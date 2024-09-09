package com.kane.library_management_system.library_management_system.service.ServiceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kane.library_management_system.library_management_system.dao.BookDao;
import com.kane.library_management_system.library_management_system.dto.BookDto;
import com.kane.library_management_system.library_management_system.entity.Book;
import com.kane.library_management_system.library_management_system.exception.ResourceNotFoundException;
import com.kane.library_management_system.library_management_system.service.BookService;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Override
    public List<BookDto> getAllBook() {
        List<Book> books = bookDao.findAll();
        List<BookDto> bookDtos=new ArrayList<>();
        for(Book book:books){
            bookDtos.add(getBookDtoMapped(book));
        }
        return bookDtos;
    }

    @Override
    public BookDto getBookById(Long bookId) {
        Book book = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("book","id",bookId));
        return getBookDtoMapped(book);
    }

    @Override
    public List<BookDto> getBookByTitle(String title){
        List<Book> books = bookDao.findByTitle(title);
        List<BookDto> bookDtos=new ArrayList<>();
        for(Book book:books){
            bookDtos.add(getBookDtoMapped(book));
        }
        return bookDtos;
    }

    @Override
    public int deleteBookById(Long bookId) {
        BookDto bookDto = getBookById(bookId);
        return bookDto.isAvailable()?bookDao.deleteById(bookId):0;
    }

    @Override
    public long createBook(BookDto bookDto) {
        Book book = getBookMapped(bookDto);
        return bookDao.save(book);
    }

    @Override
    public int updateBook(Long id, BookDto bookDto) {
        Book book = getBookMapped(bookDto);
        return bookDao.update(id, book);
    }

    @Override
    public boolean checkBookAvailability(Long bookId) {
        return bookDao.isBookAvailable(bookId);
    }

    @Override
    public List<BookDto> findBookByAvailability(boolean available){
        List<Book> Books = bookDao.findByAvailability(available);
        List<BookDto> bookDtos = new ArrayList<>();
        for(Book book:Books){
            bookDtos.add(getBookDtoMapped(book));
        }
        return bookDtos;
    }

    private BookDto getBookDtoMapped(Book book){
        return BookDto.builder()
                        .id(book.getId())
                        .author(book.getAuthor())
                        .title(book.getTitle())
                        .available(book.isAvailable())
                        .genre(book.getGenre())
                        .yearPublished(book.getYearPublished())
                        .build();
    }

    private Book getBookMapped(BookDto bookDto){
        return Book.builder()
                    .author(bookDto.getAuthor())
                    .available(bookDto.isAvailable())
                    .genre(bookDto.getGenre())
                    .title(bookDto.getTitle())
                    .yearPublished(bookDto.getYearPublished())
                    .build();
    }
    
}
