package com.kane.library_management_system.library_management_system.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private int yearPublished;
    private boolean available;

    public Book() {}

    public Book(String title, String author, String genre, int yearPublished, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearPublished = yearPublished;
        this.available = available;
    }

    public Book(Long id,String title, String author, String genre, int yearPublished, boolean available) {
        this(title, author, genre, yearPublished, available);
        this.id=id;
    }
}
