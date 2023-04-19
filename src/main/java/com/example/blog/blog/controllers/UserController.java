package com.example.blog.blog.controllers;


import com.example.blog.blog.entities.User;
import com.example.blog.blog.payloads.ApiResponse;
import com.example.blog.blog.payloads.UserDto;
import com.example.blog.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1=this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>  updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
        UserDto userDto1=this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok().body(userDto1);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok().body(this.userService.getUserById(userId));
    }

}
