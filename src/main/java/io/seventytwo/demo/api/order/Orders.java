package io.seventytwo.demo.api.order;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.util.List;

@JacksonXmlRootElement(localName = "orders")
public record Orders(@JacksonXmlElementWrapper(useWrapping = false) List<Order> order) {

    public record Order(Integer id, LocalDate orderDate, @JacksonXmlElementWrapper(localName = "items") List<Item> item) {

        public record Item(Integer id, Integer quantity, String productName) {
        }
    }
}
