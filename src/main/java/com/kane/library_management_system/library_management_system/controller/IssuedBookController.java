package com.kane.library_management_system.library_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kane.library_management_system.library_management_system.dto.BookDto;
import com.kane.library_management_system.library_management_system.dto.UserDto;
import com.kane.library_management_system.library_management_system.service.BookService;
import com.kane.library_management_system.library_management_system.service.IssuedBookService;
import com.kane.library_management_system.library_management_system.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1/apis")
public class IssuedBookController {
    
    @Autowired
    private IssuedBookService issuedBookService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Operation(summary = "1. Issue a book [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/issue-book/user/{userId}/book/{bookId}")
    public ResponseEntity<String> issueBook(@PathVariable Long userId, @PathVariable Long bookId) {
        long issuedBookId = issuedBookService.issueBook(userId, bookId);
        // IssuedBookDto issuedBookDto=issuedBookService.getIssuedBookById(issuedBookId);
        BookDto bookDto= bookService.getBookById(bookId);
        UserDto userDto = userService.getUserById(userId);
        String responseMessage = String.format("'%s' book is issued by %s %s with issuedBookid: %d", bookDto.getTitle(),userDto.getFirstName(),userDto.getLastName(),issuedBookId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @Operation(summary = "2. Return a book [Backend Developer-Kane Technical Assignment]")
    @GetMapping("/return-book/user/{userId}/book/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        String issuedBookResponse = issuedBookService.returnBook(userId, bookId);

        return ResponseEntity.status(HttpStatus.OK).body(issuedBookResponse);
    }   

}
