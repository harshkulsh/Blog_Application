package com.example.blog.blog.controllers;

import com.example.blog.blog.config.AppConstants;
import com.example.blog.blog.entities.Post;
import com.example.blog.blog.payloads.ApiResponse;
import com.example.blog.blog.payloads.PostDto;
import com.example.blog.blog.payloads.PostResponse;
import com.example.blog.blog.services.FileService;
import com.example.blog.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId){
        PostDto createdPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId){
        List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        PostResponse postResponse=this.postService.getAllPosts(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted",true),HttpStatus.OK);
    }

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("/posts/searchExact/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostsByTitleExact(
            @PathVariable("keyword") String keyword
    ){
        List<PostDto> postDtoList=this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(
            @PathVariable("keyword") String keyword
    ){
        List<PostDto> postDtoList=this.postService.searchPostsByTitle(keyword);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam ("image")MultipartFile image,
            @PathVariable("postId") Integer postId
            ) throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto postDto1=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    @GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource=this.fileService.getResources(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
