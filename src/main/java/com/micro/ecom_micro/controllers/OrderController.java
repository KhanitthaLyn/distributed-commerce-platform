package com.micro.ecom_micro.controllers;

import com.micro.ecom_micro.dto.OrderResponse;
import com.micro.ecom_micro.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-User-Id") String userId) {
       return orderService.createOrder(userId)
               .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }
}
