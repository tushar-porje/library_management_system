package com.kane.library_management_system.library_management_system.service;

import java.util.List;

import com.kane.library_management_system.library_management_system.dto.BookDto;

public interface BookService {
    public List<BookDto> getAllBook();
    public BookDto getBookById(Long bookId);
    public List<BookDto> getBookByTitle(String title);
    public int deleteBookById(Long bookId);
    public long createBook(BookDto book);
    public int updateBook(Long id, BookDto book);
    public boolean checkBookAvailability(Long bookId);
    public List<BookDto> findBookByAvailability(boolean available);
}
