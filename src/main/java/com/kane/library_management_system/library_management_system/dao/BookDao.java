package com.kane.library_management_system.library_management_system.dao;

import java.util.List;
import java.util.Optional;


import com.kane.library_management_system.library_management_system.entity.Book;

public interface BookDao{
    List<Book> findAll();
    Optional<Book> findById(Long id);
    List<Book> findByTitle(String title);
    long save(Book book);
    int update(Long id, Book book);
    int deleteById(Long id);
    public boolean isBookAvailable(Long bookId);
    List<Book> findByAvailability(boolean available);
}
