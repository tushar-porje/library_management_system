package com.kane.library_management_system.library_management_system.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kane.library_management_system.library_management_system.dto.UserDto;
import com.kane.library_management_system.library_management_system.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/v1/apis/user")
public class UserController {

    @Autowired
    UserService userService;
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    
    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        long createduserId = userService.createUser(userDto);
        String createResponse = String.format("%d user has been added with id: ", createduserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        int isUserDeleted = userService.deleteById(id);
        String deleteResponse;
        HttpStatus httpStatus;
        if(isUserDeleted>0){
            deleteResponse = String.format("%d user has been deleted", isUserDeleted);
            httpStatus=HttpStatus.OK;
        }else{
            deleteResponse = String.format("Cannot delete the user before all books are returned.");
            httpStatus=HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(deleteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        String updateResponse = String.format("User with %l is updated",id);
        return ResponseEntity.status(HttpStatus.OK).body(updateResponse);
    }

    

}
