package com.skrbomb.eCommerce.service.impl;

import com.skrbomb.eCommerce.dto.CategoryDto;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.entity.Category;
import com.skrbomb.eCommerce.exception.NotFoundException;
import com.skrbomb.eCommerce.mapper.EntityDtoMapper;
import com.skrbomb.eCommerce.repository.CategoryRepo;
import com.skrbomb.eCommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response createCategory(CategoryDto categoryDto) {

        Category category=new Category();
        category.setName(categoryDto.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryDto) {

        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new NotFoundException("Category not found"));
        category.setName(categoryDto.getName());
        categoryRepo.save(category);

        return Response.builder()
                .status(200)
                .message("Category updated successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<CategoryDto> categories = categoryRepo.findAll()
                .stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .collect(Collectors.toList());


        return Response.builder()
                .status(200)
                .message("successful")
                .categoryList(categories)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        CategoryDto categoryDto=entityDtoMapper.mapCategoryToDtoBasic(category);
        return Response.builder()
                .status(200)
                .message("successful")
                .category(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        categoryRepo.findById(categoryId)
                .ifPresentOrElse(categoryRepo::delete,()->{
                    throw new NotFoundException("Category Not Exist");
                });
        return Response.builder()
                .status(200)
                .message("successful")
                .build();
    }
}
