package com.inventory.Dao;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    private int recipeID;
    private String recipeName;
    private List<String> ingredients;
    private String instructions;
    private int quantity;
    private double price;

    // Constructor for RecipeManagementFrame
    public Recipe(String recipeName, List<String> ingredients, String instructions, int quantity, double price) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructor for BillingFrame
    public Recipe(String recipeName, List<String> ingredients, String instructions) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        // Set default values for quantity and price (you can modify these as needed)
        this.quantity = 0;
        this.price = 0.0;
    }

    // Getter for recipeID
    public int getRecipeID() {
        return recipeID;
    }

    // Getter for recipeName
    public String getRecipeName() {
        return recipeName;
    }

    // Getter for ingredients
    public List<String> getIngredients() {
        // Ensure ingredients is not null; return the original list if it is not null,
        // otherwise return an empty list
        List<String> ingredientsList = ingredients != null ? Arrays.asList(ingredients.toArray(new String[0])) : Arrays.asList();
        System.out.println("Ingredients: " + ingredientsList);
        return ingredientsList;
    }

    // Getter for instructions
    public String getInstructions() {
        return instructions;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Method to add a new recipe
    public static boolean addRecipe(String recipeName, List<String> ingredients, String instructions, int quantity, double price) {
        // Placeholder logic for adding a recipe
        // You may want to implement database interactions or other storage mechanisms
        System.out.println("Recipe added: " + recipeName);
        return true; // Return true if the addition was successful
    }

    // Method to delete an existing recipe
    public static boolean deleteRecipe(int recipeID, String recipeName) {
        // Placeholder logic for deleting a recipe
        // You may want to implement database interactions or other storage mechanisms
        System.out.println("Recipe deleted: " + recipeName);
        return true; // Return true if the deletion was successful
    }
}

