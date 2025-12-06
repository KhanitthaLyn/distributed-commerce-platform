package com.micro.ecom_micro.repository;

import com.micro.ecom_micro.models.CartItem;
import com.micro.ecom_micro.models.Product;
import com.micro.ecom_micro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
}
