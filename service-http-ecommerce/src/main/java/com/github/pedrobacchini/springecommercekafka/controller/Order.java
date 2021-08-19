package com.github.pedrobacchini.springecommercekafka.controller;

public class Order {

    private final String email;
    private final String orderId;
    private final String amount;

    public Order(String orderId, String amount, String email) {
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAmount() {
        return amount;
    }
}
