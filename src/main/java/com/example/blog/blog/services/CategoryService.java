package com.example.blog.blog.services;

import com.example.blog.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto );
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getAllCategories();
}
