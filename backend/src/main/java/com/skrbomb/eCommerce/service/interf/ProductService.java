package com.skrbomb.eCommerce.service.interf;

import com.skrbomb.eCommerce.dto.CategoryDto;
import com.skrbomb.eCommerce.dto.ProductDto;
import com.skrbomb.eCommerce.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {

    Response createProduct(Long categoryId,
                           MultipartFile image,
                           String name,
                           String description,
                           BigDecimal price);
    Response updateProduct(Long productId,
                           Long categoryId,
                           MultipartFile image,
                           String name,
                           String description,
                           BigDecimal price);

    Response deleteProduct(Long productId);

    Response getProductById(Long productId);

    Response getAllProducts();

    Response getProductsByCategoryId(Long categoryId);

    Response searchProducts(String keyWord);


}
