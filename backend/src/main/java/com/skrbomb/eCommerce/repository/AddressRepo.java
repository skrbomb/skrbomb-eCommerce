package com.skrbomb.eCommerce.repository;

import com.skrbomb.eCommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
