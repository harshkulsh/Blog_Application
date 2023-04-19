package com.example.blog.blog.services.impl;


import com.example.blog.blog.entities.User;
import com.example.blog.blog.exceptions.ResourceNotFoundException;
import com.example.blog.blog.payloads.UserDto;
import com.example.blog.blog.repositories.UserRepository;
import com.example.blog.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.userDtoToUser(userDto);
        User savedUser=this.userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User updatedUser=this.userRepository.save(user);
        UserDto userDto1=userToUserDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userId));

        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepository.findAll();
        List<UserDto> userDtoList=users.stream().map(user->userToUserDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        this.userRepository.delete(user);
    }

    private User userDtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto userToUserDto(User user){
        UserDto userDto=this.modelMapper.map(user, UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
