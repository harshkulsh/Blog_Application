package com.example.blog.blog.services.impl;

import com.example.blog.blog.entities.Category;
import com.example.blog.blog.exceptions.ResourceNotFoundException;
import com.example.blog.blog.payloads.CategoryDto;
import com.example.blog.blog.repositories.CategoryRepository;
import com.example.blog.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto,Category.class);
        this.categoryRepository.save(category);
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category" , "category id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updateCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(updateCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category" , "category id",categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category" , "category id",categoryId));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=this.categoryRepository.findAll();
        List<CategoryDto> categoryDtoList=categories.stream().map((category)->
            this.modelMapper.map(category,CategoryDto.class)
        ).collect(Collectors.toList());
        return categoryDtoList;
    }
}
