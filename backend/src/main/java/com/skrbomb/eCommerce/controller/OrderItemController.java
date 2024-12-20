package com.skrbomb.eCommerce.controller;


import com.skrbomb.eCommerce.dto.OrderRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.enums.OrderStatus;
import com.skrbomb.eCommerce.service.interf.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orderItem")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> filterOrderItems(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endData,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
            ){
        Pageable pageable= PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));
        OrderStatus orderStatus=status!=null?OrderStatus.valueOf(status.toUpperCase()):null;

        return ResponseEntity.ok(orderItemService.filterOrderItems(orderStatus,startDate,endData,itemId,pageable));
    }

    @PutMapping("/update-item-status/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable("itemId") Long itemId,
                                                          @RequestParam String status){
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(itemId,status));
    }

}
