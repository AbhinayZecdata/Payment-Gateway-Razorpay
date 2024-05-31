package com.example.paymentgateway.demo.dto;

import java.time.LocalDateTime;
import java.util.Date;


import lombok.Data;

@Data
public class OrderDto {

    private Long myOrderId;

    private String orderId;

    private int amount;

    private String receipt;

    private String status;

    private String paymentId;


    private Long userId;

    private int amount_paid;

    private int amount_due;

    private String currency;

    private int attempts;

    private Date orderDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

