package com.micro.ecom_micro.services;

import com.micro.ecom_micro.dto.OrderItemDTO;
import com.micro.ecom_micro.dto.OrderResponse;
import com.micro.ecom_micro.models.*;
import com.micro.ecom_micro.repository.OrderRepository;
import com.micro.ecom_micro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

       Optional<User> userOptional = userRepository.findById(Long.parseLong(userId));
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFITMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        null,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                ))
                .toList();
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new  OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
