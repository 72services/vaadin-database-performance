package io.seventytwo.demo.order.control;

import io.seventytwo.demo.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
