package com.lj.ordermanagementsystem.service.impl;

import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.entity.Order;
import com.lj.ordermanagementsystem.exception.ResourceNotFoundException;
import com.lj.ordermanagementsystem.mapper.OrderMapper;
import com.lj.ordermanagementsystem.repository.OrderRepository;
import com.lj.ordermanagementsystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = OrderMapper.toOrder(orderDto);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(savedOrder);

    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found for this id : " + orderId)
                );
        return OrderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto updateOrder) {
        Order order  = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found for this id : " + id)
        );
        order.setName(updateOrder.getName());
        order.setPrice(updateOrder.getPrice());
        order.setQuantity(updateOrder.getQuantity());

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(updatedOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found for this id : " + id)
        );
        orderRepository.deleteById(id);

    }
}
