package io.seventytwo.demo.api.order;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(Integer id, LocalDate orderDate, List<OrderItemDTO> items) {

    public record OrderItemDTO(Integer id, Integer quantity, String productName) {
    }
}
