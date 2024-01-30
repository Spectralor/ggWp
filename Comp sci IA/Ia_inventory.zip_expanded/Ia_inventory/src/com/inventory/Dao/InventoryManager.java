package com.inventory.Dao;
import java.util.HashMap;
import java.util.Map;


public class InventoryManager {
    private Map<String, Integer> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        // Initialize inventory data (you can replace this with data loaded from a database)
        initializeInventory();
    }

    private void initializeInventory() {
        // Assume initial quantities for some ingredients
        inventory.put("Flour", 100);
        inventory.put("Sugar", 50);
        inventory.put("Eggs", 30);
        // Add more ingredients as needed
    }

    public int getIngredientQuantity(String ingredient) {
        return inventory.getOrDefault(ingredient, 0);
    }

    public boolean deductIngredientQuantity(String ingredient) {
        int deductionQuantity = 1; // You can adjust this to the desired deduction amount

        int currentQuantity = getIngredientQuantity(ingredient);

        if (currentQuantity >= deductionQuantity) {
            inventory.put(ingredient, currentQuantity - deductionQuantity);
            return true; // Deduction successful
        } else {
            return false; // Insufficient quantity
        }
    }

    public void updateIngredientQuantity(String ingredient, int newQuantity) {
        inventory.put(ingredient, newQuantity);
    }

    public void replenishIngredientQuantity(String ingredient, int replenishQuantity) {
        int currentQuantity = getIngredientQuantity(ingredient);
        inventory.put(ingredient, currentQuantity + replenishQuantity);
    }

    public String[] getAllIngredients() {
        return inventory.keySet().toArray(new String[0]);
    }
}
