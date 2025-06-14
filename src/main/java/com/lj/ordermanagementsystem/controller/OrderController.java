package com.lj.ordermanagementsystem.controller;

import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class OrderController {
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto savedOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return ResponseEntity.ok(orderDtos);
    }

    @PostMapping("{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("{id}") Long orderId,
                                                @RequestBody OrderDto updateOrder){
        OrderDto updatedOrder = orderService.updateOrder(orderId, updateOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable("id") Long orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

}
