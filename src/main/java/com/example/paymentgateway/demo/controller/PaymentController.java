package com.example.paymentgateway.demo.controller;

import com.example.paymentgateway.demo.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import com.razorpay.*;

@RestController
@RequestMapping("/user")
public class PaymentController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String,Object> data) throws Exception {

        int amount = Integer.parseInt(data.get("price").toString());

        RazorpayClient client = new RazorpayClient("rzp_test_lszROxFGCqXv0s","oHEAkghITZ12lyz2TPRE7er2");

        JSONObject options = new JSONObject();
        options.put("amount", amount*100);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        System.out.println(options);
        Order order = client.orders.create(options);

        com.example.paymentgateway.demo.entity.Order orderdb = new com.example.paymentgateway.demo.entity.Order();
        orderdb.setOrderId(order.get("id"));

        int amnt = order.get("amount");
        int totalAmount = amnt/100;
        orderdb.setAmount(totalAmount);

        orderdb.setReceipt(order.get("receipt"));
        orderdb.setStatus(order.get("status"));

        int amntpaid = order.get("amount_paid");
        int  totalAmntPaid = amntpaid/100;
        orderdb.setAmount_paid(totalAmntPaid);

        int amntdue = order.get("amount_due");
        int  totalAmntDue = amntdue/100;
        orderdb.setAmount_due(totalAmntDue);

        orderdb.setCurrency(order.get("currency"));


        orderdb.setAttempts(order.get("attempts"));

        orderRepository.save(orderdb);

        System.out.println(order.toString());
        return order.toString();
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String,Object> data) throws RazorpayException {

        String orderId = data.get("orderId") != null ? data.get("orderId").toString() : null;

        com.example.paymentgateway.demo.entity.Order order = orderRepository.findByOrderId(orderId);

        if (orderId == null) {
            return ResponseEntity.badRequest().body("Order ID is required");
        }

        try {
            RazorpayClient client = new RazorpayClient("rzp_test_lszROxFGCqXv0s", "oHEAkghITZ12lyz2TPRE7er2");

            Order razorpayOrder = client.orders.fetch(orderId);

          String paymentStatus =  razorpayOrder.get("status");

            if (data.get("status").equals(paymentStatus)) {
                com.example.paymentgateway.demo.entity.Order order2 = orderRepository.findByOrderId(orderId);
                order2.setPaymentId(data.get("paymentId").toString());
                order2.setStatus(paymentStatus);
                order2.setAttempts(razorpayOrder.get("attempts"));
                orderRepository.save(order2);
                return ResponseEntity.ok("Order Updated Successfully !");
            } else {
                com.example.paymentgateway.demo.entity.Order order2 = orderRepository.findByOrderId(orderId);
                order2.setPaymentId(data.get("paymentId").toString());
                order2.setStatus(paymentStatus);
                order2.setAttempts(razorpayOrder.get("attempts"));
                orderRepository.save(order2);
                return ResponseEntity.ok("Payment Not Successfull !");
            }
        } catch (RazorpayException e) {
            // Handle RazorpayException
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    }

