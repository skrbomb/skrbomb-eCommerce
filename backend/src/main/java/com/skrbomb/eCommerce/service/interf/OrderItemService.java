package com.skrbomb.eCommerce.service.interf;

import com.skrbomb.eCommerce.dto.OrderRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderItemService {

    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);

    Response filterOrderItems(OrderStatus status,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              Long itemId,
                              Pageable pageable);
}
