package com.lj.ordermanagementsystem.service;

import com.lj.ordermanagementsystem.entity.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public OrderCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheOrder(Order order) {
        redisTemplate.opsForValue().set("order:" + order.getId(), order);
    }

    public Order getCachedOrder(Long id) {
        return (Order) redisTemplate.opsForValue().get("order:" + id);
    }
}