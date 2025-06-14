package com.lj.ordermanagementsystem.mapper;

import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.entity.Order;

public class OrderMapper {

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getPrice()
        );
    }

    public static Order toOrder(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getName(),
                orderDto.getQuantity(),
                orderDto.getPrice()
        );
    }
}
