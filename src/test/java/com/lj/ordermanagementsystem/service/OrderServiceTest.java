package com.lj.ordermanagementsystem.service;


import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.entity.Order;
import com.lj.ordermanagementsystem.exception.ResourceNotFoundException;
import com.lj.ordermanagementsystem.mapper.OrderMapper;
import com.lj.ordermanagementsystem.repository.OrderRepository;
import com.lj.ordermanagementsystem.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    private OrderDto sampleDto;
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleDto = new OrderDto(1L, "Product A", 3,10.0); // assuming your DTO has these fields
        sampleOrder = new Order(1L, "Product A", 3,10.0);
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        OrderDto result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product A", result.getName());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(2L));
        verify(orderRepository, times(1)).findById(2L);
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        OrderDto result = orderService.createOrder(sampleDto);

        assertNotNull(result);
        assertEquals("Product A", result.getName());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
