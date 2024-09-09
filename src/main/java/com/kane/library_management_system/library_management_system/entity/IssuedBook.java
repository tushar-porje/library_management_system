package com.kane.library_management_system.library_management_system.entity;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssuedBook {

    private Long id;
    private Book book;
    private User user;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public IssuedBook(){}

    public IssuedBook(Book book, User user, LocalDate issueDate, LocalDate returnDate) {
        this.book = book;
        this.user = user;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public IssuedBook(Long id, Book book, User user, LocalDate issueDate, LocalDate returnDate) {
        this(book, user, issueDate, returnDate);
        this.id=id;
    }


}
