package com.example.blog.blog.services;

import com.example.blog.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);

}
