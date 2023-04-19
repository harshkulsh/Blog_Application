package com.example.blog.blog.services;

import com.example.blog.blog.entities.Post;
import com.example.blog.blog.payloads.PostDto;
import com.example.blog.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
    List<PostDto> searchPostsByTitle(String keyword);
}
