package com.kane.library_management_system.library_management_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private int yearPublished;
    private boolean available;
 
}
