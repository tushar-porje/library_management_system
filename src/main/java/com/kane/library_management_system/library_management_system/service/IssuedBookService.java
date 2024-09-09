package com.kane.library_management_system.library_management_system.service;

import com.kane.library_management_system.library_management_system.dto.IssuedBookDto;

public interface IssuedBookService {
     Long issueBook(long userId, long bookId);
     String returnBook(long userId, long bookId);
     IssuedBookDto getIssuedBookByUserIdAndBookId(Long userId, Long bookId);
     IssuedBookDto getIssuedBookById(long issuedBookId);
}
