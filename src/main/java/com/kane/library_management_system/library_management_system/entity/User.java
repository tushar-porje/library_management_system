package com.kane.library_management_system.library_management_system.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public User(){
    }

    public User(String firstName,String lastName, String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    public User(Long id, String firstName,String lastName, String email){
        this(firstName, lastName,email);
        this.id=id;
    }
}
