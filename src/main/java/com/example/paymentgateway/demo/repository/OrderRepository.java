package com.example.paymentgateway.demo.repository;

import com.example.paymentgateway.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends JpaRepository<Order, Long>,
        PagingAndSortingRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    public Order findByOrderId(String orderId);

}