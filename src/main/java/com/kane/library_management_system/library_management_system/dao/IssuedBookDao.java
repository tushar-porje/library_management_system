package com.kane.library_management_system.library_management_system.dao;

import java.util.List;
import java.util.Optional;

import com.kane.library_management_system.library_management_system.entity.IssuedBook;

public interface IssuedBookDao{
    List<IssuedBook> findAll();
    Optional<IssuedBook> findById(Long id);
    Optional<IssuedBook> findByUserIdAndBookId(Long userId, Long bookId);
    boolean isBookIssuedByUser(Long id);
    long save(IssuedBook issuedBook);
    int update(long id, IssuedBook issuedBook);
    int deleteById(Long id);
}
