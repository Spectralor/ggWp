package com.inventory.Frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.inventory.Dao.Recipe;
import com.inventory.Dao.RecipeList;
import com.inventory.connection.DatabaseManager;

public class RecipeManagementFrame extends JFrame {
    private JTextField recipeNameField;
    private JTextArea ingredientsArea;
    private JTextArea instructionsArea;
    private JTextField quantityField;
    private JTextField priceField;
    private RecipeList recipeList;

    public RecipeManagementFrame() {
        setTitle("Recipe Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        add(panel);
        recipeList = new RecipeList(); // Instantiate the RecipeList
        placeComponents(panel);

        setVisible(true);
    }


    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel recipeNameLabel = new JLabel("Recipe Name:");
        recipeNameLabel.setBounds(10, 20, 100, 25);
        recipeNameLabel.setForeground(Color.WHITE);
        panel.add(recipeNameLabel);

        recipeNameField = new JTextField(20);
        recipeNameField.setBounds(120, 20, 250, 25);
        panel.add(recipeNameField);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setBounds(10, 50, 100, 25);
        ingredientsLabel.setForeground(Color.WHITE);
        panel.add(ingredientsLabel);

        ingredientsArea = new JTextArea();
        ingredientsArea.setBounds(120, 50, 250, 100);
        panel.add(ingredientsArea);

        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setBounds(10, 160, 100, 25);
        instructionsLabel.setForeground(Color.WHITE);
        panel.add(instructionsLabel);

        instructionsArea = new JTextArea();
        instructionsArea.setBounds(120, 160, 250, 100);
        panel.add(instructionsArea);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 270, 100, 25);
        quantityLabel.setForeground(Color.WHITE);
        panel.add(quantityLabel);

        quantityField = new JTextField(20);
        quantityField.setBounds(120, 270, 250, 25);
        panel.add(quantityField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 300, 100, 25);
        priceLabel.setForeground(Color.WHITE);
        panel.add(priceLabel);

        priceField = new JTextField(20);
        priceField.setBounds(120, 300, 250, 25);
        panel.add(priceField);

        JButton addButton = createOrangeButton("Add Recipe", 10, 330, 120, 25);
        panel.add(addButton);

        JButton deleteButton = createOrangeButton("Delete Recipe", 140, 330, 120, 25);
        panel.add(deleteButton);

        JButton billingButton = createOrangeButton("Billing", 270, 330, 120, 25);
        panel.add(billingButton);

        JButton homeButton = createOrangeButton("Home", 400, 330, 120, 25);
        panel.add(homeButton);
        
        // Set the background color of the panel to black
        panel.setBackground(Color.BLACK);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRecipe();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	openDeleteRecipeFrame();
            }
        });

        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBillingFrame();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToMainApplication();
            }
        });
    }

    private JButton createOrangeButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(255, 165, 0));
        button.setForeground(Color.WHITE);

        return button;
    }

    private void addRecipe() {
        String recipeName = recipeNameField.getText();
        String ingredients = ingredientsArea.getText();
        String instructions = instructionsArea.getText();
        String quantityStr = quantityField.getText();
        String priceStr = priceField.getText();

        int quantity = 0;
        double price = 0.0;
        try {
            quantity = Integer.parseInt(quantityStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity or price.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> parsedIngredients = parseIngredients(ingredients);

        if (recipeName.isEmpty() || parsedIngredients.isEmpty() || instructions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Recipe recipe = new Recipe(recipeName, parsedIngredients, instructions, quantity, price);

        // TODO: Connect to the database and insert the recipe
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "INSERT INTO Recipes (RecipeName, Ingredients, Instructions, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, recipe.getRecipeName());
                preparedStatement.setString(2, String.join(", ", recipe.getIngredients()));
                preparedStatement.setString(3, recipe.getInstructions());
                preparedStatement.setInt(4, recipe.getQuantity());
                preparedStatement.setDouble(5, recipe.getPrice());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    // Recipe added successfully
                    JOptionPane.showMessageDialog(this, "Recipe added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add recipe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query execution errors
        }
    }

    private void deleteRecipe() {
        // Assuming you have a way to identify the recipe to delete, e.g., selectedRecipeID
        int selectedRecipeID = getSelectedRecipeID(); // Implement this method

        // TODO: Connect to the database and delete the recipe
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "DELETE FROM Recipes WHERE RecipeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, selectedRecipeID);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    // Recipe deleted successfully
                    JOptionPane.showMessageDialog(this, "Recipe deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete recipe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query execution errors
        }
    }

    private List<String> parseIngredients(String ingredients) {
        return Arrays.asList(ingredients.split("\\s*,\\s*"));
    }

    private int getSelectedRecipeID() {
        // Assuming you have a JList named 'recipeList'
        int selectedIndex = recipeList.getSelectedIndex();

        if (selectedIndex != -1) { // -1 indicates no item is selected
            // Assuming the recipe ID is associated with the selected item
            // Adjust this based on how your data is structured
            Recipe selectedRecipe = (Recipe) recipeList.getSelectedValue();
            return selectedRecipe.getRecipeID(); // Assuming Recipe class has a method getRecipeID()
        } else {
            // Handle the case when no recipe is selected
            JOptionPane.showMessageDialog(this, "Please select a recipe.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1; // Return a sentinel value or throw an exception based on your application's needs
        }
    }



    private void navigateToMainApplication() {
        MainApplicationFrame mainApplicationFrame = new MainApplicationFrame();
        mainApplicationFrame.setVisible(true);
        this.dispose();
    }

    private void openBillingFrame() {
        // Implement logic to open the billing frame
        Bill billingFrame = new Bill();
        billingFrame.setVisible(true);
    }
    private void openDeleteRecipeFrame() {
        // Open the DeleteRecipeFrame
        DeleteRecipeFrame.openDeleteRecipeFrame();
    }

    public static void main(String[] args) {
        new RecipeManagementFrame();
    }
}