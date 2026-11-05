package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ApiException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    /*private List<Category> categories = new ArrayList<>();
    long id=1*/;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> response = categoryRepository.findAll();
        if(response.isEmpty()){
            throw new ApiException("No categories found");
        }
        return response;
    }

    @Override
    public void createCategory(Category category) {
        Category response = categoryRepository.findByCategoryName(category.getCategoryName());
        if (response != null) {
            throw new ApiException("Category with the name "+category.getCategoryName()+" already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        categoryRepository.delete(existingCategory);
        return "Category deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        category.setCategoryId(categoryId);
        return categoryRepository.save(category);
    }
}
