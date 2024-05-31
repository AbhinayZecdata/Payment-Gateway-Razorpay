package com.example.paymentgateway.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myOrderId;

    private String orderId;


    private int amount;

    private String receipt;

    private String status;

    private Long userId;

    private int amount_paid;

    private int amount_due;

    private String currency;

    private int attempts;

    private String paymentId;

    @Column(name = "order_date")
    @CreationTimestamp
    private Date orderDate;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime updatedAt;

}

