package com.skrbomb.eCommerce.mapper;


import com.skrbomb.eCommerce.dto.*;
import com.skrbomb.eCommerce.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {

    //map user entity to userDto
    public UserDto mapUserToDtoBasic(User user){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole().name());
        return userDto;
    }

    //map address to addressDto
    public AddressDto mapAddressToDtoBasic(Address address){
        AddressDto addressDto=new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setState(address.getState());
        addressDto.setStreet(address.getStreet());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    //map category to categoryDto
    public CategoryDto  mapCategoryToDtoBasic(Category category){
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    //map OrderItem to OrderItemDto
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){
        OrderItemDto orderItemDto=new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    //map product to productDto
    public ProductDto mapProductToDtoBasic(Product product){
        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    //map user to userDto & address
    public UserDto mapUserToDtoPlusAddress(User user){
        UserDto userDto=mapUserToDtoBasic(user);
        if(user.getAddress()!=null){
            AddressDto addressDto=mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    // map orderItem to Dto plus product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){
        OrderItemDto orderItemDto=mapOrderItemToDtoBasic(orderItem);
        if(orderItem.getProduct()!=null){
            ProductDto productDto=mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //map orderItem to Dto plus product & user
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto=mapOrderItemToDtoPlusProduct(orderItem);
        if(orderItem.getUser()!=null){
            UserDto userDto=mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //user to Dto with address and orderItem history
    public UserDto mapUserToDtoPlusAddressAndOrderItem(User user){
        UserDto userDto=mapUserToDtoPlusAddress(user);
        if(user.getOrderItemList()!=null&&!user.getOrderItemList().isEmpty()){
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .toList());

        }
        return userDto;
    }



















}
