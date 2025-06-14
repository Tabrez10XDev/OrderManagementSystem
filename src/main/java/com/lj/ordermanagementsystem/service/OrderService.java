package com.lj.ordermanagementsystem.service;

import com.lj.ordermanagementsystem.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrder(Long id, OrderDto updateOrder);

    void deleteOrderById(Long id);
}
