package com.lj.ordermanagementsystem.service.impl;

import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.entity.Order;
import com.lj.ordermanagementsystem.exception.ResourceNotFoundException;
import com.lj.ordermanagementsystem.mapper.OrderMapper;
import com.lj.ordermanagementsystem.repository.OrderRepository;
import com.lj.ordermanagementsystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String TOPIC = "orders"; // Kafka topic
    private OrderRepository orderRepository;
    private KafkaTemplate<String, Order> kafkaTemplate;

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
        kafkaTemplate.send(TOPIC, order);
//        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found for this id : " + id)
        );
        orderRepository.deleteById(id);

    }


    @KafkaListener(topics = "orders", groupId = "order-group")
    public void processOrderUpdates(Order message) {
        System.out.println("📥 Consumed message: " + message);
        // TODO: parse JSON string to Order and save to DB if needed
    }
}
