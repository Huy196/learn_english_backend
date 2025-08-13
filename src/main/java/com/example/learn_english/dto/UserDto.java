package com.example.learn_english.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Set<String> roles;
    private String age;
    private String name;
    private List<String> images;
}
