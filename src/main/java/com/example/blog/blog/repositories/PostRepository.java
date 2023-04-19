package com.example.blog.blog.repositories;

import com.example.blog.blog.entities.Category;
import com.example.blog.blog.entities.Post;
import com.example.blog.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);
    List<Post> findAllByCategory(Category category);
    List<Post> searchPostByTitle(String keyword);
    List<Post> findByTitleContaining(String keyword);
}
