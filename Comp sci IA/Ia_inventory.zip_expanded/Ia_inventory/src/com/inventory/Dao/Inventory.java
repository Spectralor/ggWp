package com.inventory.Dao;

public class Inventory {
    private String ingredientName;
    private int quantity;

    public Inventory(String ingredientName, int quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public void updateQuantity(int deltaQuantity) {
        quantity += deltaQuantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
