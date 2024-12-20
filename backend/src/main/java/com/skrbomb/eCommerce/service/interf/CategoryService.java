package com.skrbomb.eCommerce.service.interf;


import com.skrbomb.eCommerce.dto.CategoryDto;
import com.skrbomb.eCommerce.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryDto);

    Response updateCategory(Long categoryId, CategoryDto categoryDto);

    Response getAllCategories();

    Response getCategoryById(Long categoryId);

    Response deleteCategory(Long categoryId);
}
