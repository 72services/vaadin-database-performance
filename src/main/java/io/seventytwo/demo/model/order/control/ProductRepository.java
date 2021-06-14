package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
