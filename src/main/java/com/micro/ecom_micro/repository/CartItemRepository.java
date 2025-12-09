package com.micro.ecom_micro.repository;

import com.micro.ecom_micro.models.CartItem;
import com.micro.ecom_micro.models.Product;
import com.micro.ecom_micro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}
