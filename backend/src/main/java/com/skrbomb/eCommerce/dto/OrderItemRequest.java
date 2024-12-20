package com.skrbomb.eCommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private int quantity;
}
