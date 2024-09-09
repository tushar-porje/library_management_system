package com.kane.library_management_system.library_management_system.dto;

import java.time.LocalDate;

import com.kane.library_management_system.library_management_system.entity.Book;
import com.kane.library_management_system.library_management_system.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuedBookDto {

    private Long id;
    private Book book;
    private User user;
    private LocalDate issueDate;
    private LocalDate returnDate;

}
