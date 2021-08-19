package com.github.pedrobacchini.springecommercekafka.domain;

public class Order {

    private String email;
    private String orderId;
    private String amount;

    public Order() { }

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
