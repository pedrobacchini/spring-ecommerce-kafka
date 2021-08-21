package com.github.pedrobacchini.springecommercekafka.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

public class User {

    @DynamoDBHashKey(attributeName = "pk")
    private String uuid;

    @DynamoDBAttribute(attributeName = "email")
    private String email;

    public User() { }

    public User(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }
}
