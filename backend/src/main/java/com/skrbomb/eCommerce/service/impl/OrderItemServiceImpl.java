package com.skrbomb.eCommerce.service.impl;

import com.skrbomb.eCommerce.dto.OrderItemDto;
import com.skrbomb.eCommerce.dto.OrderRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.entity.Order;
import com.skrbomb.eCommerce.entity.OrderItem;
import com.skrbomb.eCommerce.entity.Product;
import com.skrbomb.eCommerce.entity.User;
import com.skrbomb.eCommerce.enums.OrderStatus;
import com.skrbomb.eCommerce.exception.NotFoundException;
import com.skrbomb.eCommerce.mapper.EntityDtoMapper;
import com.skrbomb.eCommerce.repository.OrderItemRepo;
import com.skrbomb.eCommerce.repository.OrderRepo;
import com.skrbomb.eCommerce.repository.ProductRepo;
import com.skrbomb.eCommerce.service.interf.OrderItemService;
import com.skrbomb.eCommerce.service.interf.UserService;
import com.skrbomb.eCommerce.specification.OrderItemSpecification;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {

        User user=userService.getLoginUser();
        //map orderRequest items to order entities

        List<OrderItem> orderItems=orderRequest.getOrderItems()
                .stream()
                .map(orderItemRequest -> {
                    Product product  =productRepo.findById(orderItemRequest.getProductId())
                            .orElseThrow(()-> new NotFoundException("Product not found"));

                    OrderItem orderItem=new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
                    orderItem.setStatus(OrderStatus.PENDING);
                    orderItem.setUser(user);

                    return orderItem;
                }).toList();

        //calculate the total price
        BigDecimal totalPrice=orderRequest.getTotalPrice()!=null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO)>0
                ?orderRequest.getTotalPrice()
                :orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        //create order entity
        Order order =new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        //set the order reference in each orderItem
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
        });

        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order Placed Successfully")
                .build();

    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {

        OrderItem orderItem=orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order item not found"));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);

        return Response.builder()
                .status(200)
                .message("Order Status Updated Successfully")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {

        Specification<OrderItem> spec=Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));
        Page<OrderItem> orderItemPage=orderItemRepo.findAll(spec,pageable);

        if(orderItemPage.isEmpty()){
            throw new NotFoundException("Order item not found");
        }

        List<OrderItemDto> orderItemDtos=orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .toList();
        return Response.builder()
                .status(200)
                .message("Successfully")
                .orderItemList(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElements(orderItemPage.getTotalElements())
                .build();
    }
}
