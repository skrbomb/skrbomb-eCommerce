package com.skrbomb.eCommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.skrbomb.eCommerce.entity.Address;
import com.skrbomb.eCommerce.entity.OrderItem;
import com.skrbomb.eCommerce.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String password;

    private String email;

    private String phoneNumber;

    private String userRole;

    private List<OrderItemDto> orderItemList;

    private AddressDto address;

}
