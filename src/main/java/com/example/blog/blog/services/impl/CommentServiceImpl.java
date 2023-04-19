package com.example.blog.blog.services.impl;

import com.example.blog.blog.entities.Comment;
import com.example.blog.blog.entities.Post;
import com.example.blog.blog.exceptions.ResourceNotFoundException;
import com.example.blog.blog.payloads.CommentDto;
import com.example.blog.blog.payloads.PostDto;
import com.example.blog.blog.repositories.CommentRepository;
import com.example.blog.blog.repositories.PostRepository;
import com.example.blog.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        Comment comment=modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment= this.commentRepository.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","commentId",commentId));
        this.commentRepository.delete(comment);
    }
}
