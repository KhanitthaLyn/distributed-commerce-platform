package com.micro.ecom_micro.repository;

import com.micro.ecom_micro.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
