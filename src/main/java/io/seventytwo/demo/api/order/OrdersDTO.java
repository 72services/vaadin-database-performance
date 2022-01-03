package io.seventytwo.demo.api.order;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.util.List;

@JacksonXmlRootElement(localName = "orders")
public record OrdersDTO(@JacksonXmlElementWrapper(useWrapping = false) List<OrderDTO> order) {

    public record OrderDTO(Integer id, LocalDate orderDate,
                           @JacksonXmlElementWrapper(localName = "items") List<OrderItemDTO> item) {

        public record OrderItemDTO(Integer id, Integer quantity, String productName) {
        }
    }
}
