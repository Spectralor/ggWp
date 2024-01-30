package com.inventory.Dao;


public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean login(String enteredPassword) {
        return password.equals(enteredPassword);
    }
}
