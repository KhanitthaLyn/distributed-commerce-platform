package com.micro.ecom_micro.dto;

import com.micro.ecom_micro.models.OrderItem;
import com.micro.ecom_micro.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> Items;
    private LocalDateTime createdAt;
}
