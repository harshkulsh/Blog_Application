package com.example.blog.blog.services;

import com.example.blog.blog.entities.User;
import com.example.blog.blog.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
