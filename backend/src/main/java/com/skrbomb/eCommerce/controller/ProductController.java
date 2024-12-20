package com.skrbomb.eCommerce.controller;


import com.skrbomb.eCommerce.dto.ProductDto;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.exception.InvalidCredentialsException;
import com.skrbomb.eCommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<Response> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProducts(@RequestParam(required = false) String keyWord){
        return ResponseEntity.ok(productService.searchProducts(keyWord));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response> searchProductsByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(@RequestParam Long categoryId,
                                                  @RequestParam MultipartFile image,
                                                  @RequestParam String name,
                                                  @RequestParam String description,
                                                  @RequestParam BigDecimal price){
        if(categoryId==null||image.isEmpty()||name.isEmpty()||description.isEmpty()||price==null){
            throw new InvalidCredentialsException("All fields are required");
        }
        return ResponseEntity.ok(productService.createProduct(categoryId,image,name,description,price));
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(@PathVariable("productId") Long productId,
                                                  @RequestParam(required = false) Long categoryId,
                                                  @RequestParam(required = false) MultipartFile image,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String description,
                                                  @RequestParam(required = false) BigDecimal price){
        return ResponseEntity.ok(productService.updateProduct(productId,categoryId,image,name,description,price));
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}
