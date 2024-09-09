package com.kane.library_management_system.library_management_system.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kane.library_management_system.library_management_system.dto.BookDto;
import com.kane.library_management_system.library_management_system.exception.ResourceNotFoundException;
import com.kane.library_management_system.library_management_system.service.BookService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/apis/book")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Operation(summary = "3. Show List of books [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBook();
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @Operation(summary = "4. Search for a book(byId) and show results [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBooksById(@PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @Operation(summary = "4. Search for a book(byTitle) and show results [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable String title) {
        List<BookDto> bookDtos = bookService.getBookByTitle(title);
        if(bookDtos.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
        }else{
            throw new ResourceNotFoundException("books");
        }
        
    }

    @Operation(summary = "4. Search for a book(byAvailability) and show results [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/availableBooks")
    public ResponseEntity<List<BookDto>> getBookByAvailability() {
        List<BookDto> bookDtos = bookService.findBookByAvailability(true);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @Operation(summary = "4. Search for a book(already issued by users) and show results [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/unavailableBooks")
    public ResponseEntity<List<BookDto>> getUnavailableBooks() {
        List<BookDto> bookDtos = bookService.findBookByAvailability(false);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @PostMapping("/")
    public ResponseEntity<String> createBook(@RequestBody BookDto bookDto) {
        bookDto.setAvailable(true);
        long createdBookId = bookService.createBook(bookDto);
        
        String createResponse = String.format("book has been added with id: %d", createdBookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        int isBookUpdated = bookService.updateBook(id, bookDto);
        String updateResponse;
        HttpStatus updateStatus;
        if(isBookUpdated==1){
            updateResponse = String.format("book with id: %d has been updated", id);
            updateStatus = HttpStatus.OK;
        }else{
            updateResponse = String.format("book with id: %d was not able to update", id);
            updateStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(updateStatus).body(updateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){
        int isBookDeleted = bookService.deleteBookById(id);
        String deleteResponse;
        HttpStatus httpStatus;
        if(isBookDeleted==1){
            deleteResponse = String.format(" book has been deleted with id: %d", id);
            httpStatus=HttpStatus.OK;
        }else{
            deleteResponse = String.format("Cannot delete the bookof id: %d before it is returned",id);
            httpStatus=HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(deleteResponse);
    }
    
}
