package com.example.blog.blog.services.impl;

import com.example.blog.blog.entities.Category;
import com.example.blog.blog.entities.Post;
import com.example.blog.blog.entities.User;
import com.example.blog.blog.exceptions.ResourceNotFoundException;
import com.example.blog.blog.payloads.PostDto;
import com.example.blog.blog.payloads.PostResponse;
import com.example.blog.blog.repositories.CategoryRepository;
import com.example.blog.blog.repositories.PostRepository;
import com.example.blog.blog.repositories.UserRepository;
import com.example.blog.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","categoryID",categoryId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setCreateOn(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost=this.postRepository.save(post);
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        postRepository.save(post);
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> postsPage= this.postRepository.findAll(p);
        List<Post> posts=postsPage.getContent();
        List<PostDto> postDtoList=posts.stream().map((post)->
                this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(postsPage.getNumber());
        postResponse.setPageSize(postsPage.getSize());
        postResponse.setTotalElements(postsPage.getTotalElements());
        postResponse.setTotalPages(postsPage.getTotalPages());
        postResponse.setLastPage(postsPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","post id",postId));

        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));
        List<Post> posts=this.postRepository.findAllByCategory(category);
        List<PostDto> postDtoList=posts.stream().map((post)->
                this.modelMapper.map(post,PostDto.class)
                )
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","user id",userId));
        List<Post> posts=this.postRepository.findAllByUser(user);
        List<PostDto> postDtoList=posts.stream().map((post)->
                        this.modelMapper.map(post,PostDto.class)
                )
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepository.searchPostByTitle(keyword);
        List<PostDto> postDtoList=posts.stream().map((post)->
                this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    public List<PostDto> searchPostsByTitle(String keyword){
        List<Post> posts=this.postRepository.findByTitleContaining(keyword);
        List<PostDto> postDtoList=posts.stream().map((post)->
                this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }
}
