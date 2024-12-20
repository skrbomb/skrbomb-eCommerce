package com.skrbomb.eCommerce.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "order",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<OrderItem> orderItemList;

    @Column(name = "created_at")
    private LocalDateTime createdAt=LocalDateTime.now();

    //PAYMENT

}
