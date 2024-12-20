package com.skrbomb.eCommerce.entity;


import com.skrbomb.eCommerce.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "users"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required.")
    private String name;

    @NotBlank(message = "Password is required.")
    private String password;

    @Column(
            unique = true,
            nullable = false
    )
    @NotBlank(message = "Email is required.")
    private String email;

    @Column(
            name = "phone_number",
            nullable = false
    )
    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    private UserRole userRole;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItemList;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Address address;

    @Column(name = "created_at")
    private final LocalDateTime createdAt=LocalDateTime.now();

}
