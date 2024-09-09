package com.kane.library_management_system.library_management_system.service;

import java.util.List;

import com.kane.library_management_system.library_management_system.dto.UserDto;

public interface UserService {
    public List<UserDto> getAllUsers();
    public UserDto getUserById(Long userId);
    public int deleteById(Long userId);
    public long createUser(UserDto user);
    public int updateUser(Long id, UserDto user);
}
