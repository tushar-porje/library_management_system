package com.kane.library_management_system.library_management_system.dao;

import java.util.List;
import java.util.Optional;

import com.kane.library_management_system.library_management_system.entity.User;

public interface UserDao{
    List<User> findAll();
    Optional<User> findById(Long id);
    long save(User user);
    int update(Long id, User user);
    int deleteById(Long id);
}
