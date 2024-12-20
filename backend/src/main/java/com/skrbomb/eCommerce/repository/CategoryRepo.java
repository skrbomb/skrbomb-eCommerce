package com.skrbomb.eCommerce.repository;

import com.skrbomb.eCommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
