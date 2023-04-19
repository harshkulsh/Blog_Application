package com.example.blog.blog.controllers;

import com.example.blog.blog.entities.Category;
import com.example.blog.blog.payloads.ApiResponse;
import com.example.blog.blog.payloads.CategoryDto;
import com.example.blog.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catId") Integer catId){
        CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId")Integer id ){
        CategoryDto categoryDto=this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtoList=this.categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }
}
