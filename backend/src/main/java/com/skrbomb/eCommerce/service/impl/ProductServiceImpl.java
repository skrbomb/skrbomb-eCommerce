package com.skrbomb.eCommerce.service.impl;


import com.skrbomb.eCommerce.dto.ProductDto;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.entity.Category;
import com.skrbomb.eCommerce.entity.Product;
import com.skrbomb.eCommerce.exception.NotFoundException;
import com.skrbomb.eCommerce.mapper.EntityDtoMapper;
import com.skrbomb.eCommerce.repository.CategoryRepo;
import com.skrbomb.eCommerce.repository.ProductRepo;
import com.skrbomb.eCommerce.service.AwsS3Service;
import com.skrbomb.eCommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;


    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {

        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new NotFoundException("Category not found"));

        String productImageUrl=awsS3Service.saveImageToS3(image);

        Product product=new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setImageUrl(productImageUrl);
        productRepo.save(product);

        return Response.builder()
                .status(200)
                .message("Product created Successfully")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {

        Product product=productRepo.findById(productId)
                .orElseThrow(()->new NotFoundException("Product not found"));

        Category category=null;
        String productImageUrl=null;

        if(categoryId!=null){
            category=categoryRepo.findById(categoryId)
                    .orElseThrow(()->new NotFoundException("Category not found"));
        }

        if(image!= null && !image.isEmpty()){
            productImageUrl = awsS3Service.saveImageToS3(image);
        }

        if(category!=null)  product.setCategory(category);
        if(productImageUrl!=null) product.setImageUrl(productImageUrl);
        if(name!=null) product.setName(name);
        if(description!=null) product.setDescription(description);
        if(price != null) product.setPrice(price);

        productRepo.save(product);
        productRepo.flush();

        return Response.builder()
                .status(200)
                .message("Product updated Successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {

        productRepo.findById(productId)
                .ifPresentOrElse(productRepo::delete,()->{
                    throw new NotFoundException("Product not found");
                });

        return Response.builder()
                .status(200)
                .message("Product deleted")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product=productRepo.findById(productId)
                .orElseThrow(()->new NotFoundException("Product not found"));
        ProductDto productDto=entityDtoMapper.mapProductToDtoBasic(product);
        return Response.builder()
                .status(200)
                .message("Successful")
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productDtos=productRepo.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .toList();
        return Response.builder()
                .status(200)
                .message("Successful")
                .productList(productDtos)
                .build();
    }

    @Override
    public Response getProductsByCategoryId(Long categoryId) {
        List<ProductDto> productDtos=productRepo.findByCategoryId(categoryId)
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Successful")
                .productList(productDtos)
                .build();
    }

    @Override
    public Response searchProducts(String keyWord) {

        List<Product> products=productRepo.findByNameContainingOrDescriptionContaining(keyWord,keyWord);

        if(products.isEmpty()){
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtos=products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Successful")
                .productList(productDtos)
                .build();
    }
}
