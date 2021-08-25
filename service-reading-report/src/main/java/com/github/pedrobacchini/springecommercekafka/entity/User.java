package com.github.pedrobacchini.springecommercekafka.entity;

public class User {

    private String uuid;

    private String email;

    public User() { }

    public User(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReportPath() {
        return "target/" + uuid + "-report.txt";
    }
}
