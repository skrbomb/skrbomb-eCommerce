package com.skrbomb.eCommerce.controller;


import com.skrbomb.eCommerce.dto.CategoryDto;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Response> findAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<Response> saveCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") Long id,
                                                   @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
