package com.skrbomb.eCommerce.repository;

import com.skrbomb.eCommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
