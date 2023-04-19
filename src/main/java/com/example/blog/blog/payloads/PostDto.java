package com.example.blog.blog.payloads;

import com.example.blog.blog.entities.Category;
import com.example.blog.blog.entities.Comment;
import com.example.blog.blog.entities.User;
import com.fasterxml.jackson.databind.DatabindException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String Content;
    private String imageName;
    private Date createOn;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments=new HashSet<>();
}
