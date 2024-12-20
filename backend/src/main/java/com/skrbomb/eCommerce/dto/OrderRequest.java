package com.skrbomb.eCommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skrbomb.eCommerce.entity.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {


    private BigDecimal totalPrice;
    private List<OrderItemRequest> orderItems;
    private Payment payment;
}