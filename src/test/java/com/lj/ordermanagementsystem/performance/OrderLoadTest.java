package com.lj.ordermanagementsystem.performance;


import com.lj.ordermanagementsystem.dto.OrderDto;
import com.lj.ordermanagementsystem.repository.OrderRepository;
import com.lj.ordermanagementsystem.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)  // Disable default transactional behavior
public class OrderLoadTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll(); // Clear orders before each test
    }

    @Test
    void testBulkOrderCreation() {
        int numOrders = 10000;  // Number of orders to create

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition()); // Start manual transaction

        for (int i = 0; i < numOrders; i++) {
            OrderDto order = new OrderDto();
            order.setName("Customer_" + i);
            order.setQuantity(5);
            order.setPrice(100.0);
            // Set other fields as needed

            orderService.createOrder(order);

            if (i % 1000 == 0) {
                System.out.println("Inserted: " + i + " orders...");
            }
        }

        transactionManager.commit(status); // Commit once after bulk insert

        int totalOrders = orderService.getAllOrders().size();
        System.out.println("Total Orders in DB: " + totalOrders);

        // Assert that all orders are created
        assertEquals(numOrders, totalOrders);
    }
}
