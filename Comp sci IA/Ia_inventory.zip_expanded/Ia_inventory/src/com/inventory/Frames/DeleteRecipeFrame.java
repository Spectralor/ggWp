package com.inventory.Frames;

import javax.swing.*;

import com.inventory.connection.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipeFrame extends JFrame {
    private JTextField recipeNameField;

    public DeleteRecipeFrame() {
        setTitle("Delete Recipe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to avoid closing the entire application

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        add(panel);
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

        JButton deleteButton = createOrangeButton("Delete Recipe", 10, 60, 120, 25);
        panel.add(deleteButton);

        JButton cancelButton = createOrangeButton("Cancel", 140, 60, 120, 25);
        panel.add(cancelButton);

        // Set the background color of the panel to black
        panel.setBackground(Color.BLACK);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecipe();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the delete recipe frame
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

    private void deleteRecipe() {
        String recipeName = recipeNameField.getText();

        if (recipeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the recipe name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: Connect to the database and delete the recipe
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "DELETE FROM Recipes WHERE RecipeName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, recipeName);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    // Recipe deleted successfully
                    JOptionPane.showMessageDialog(this, "Recipe deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete recipe. Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query execution errors
        }

        // Close the delete recipe frame
        dispose();
    }

    // This method can be called from your RecipeManagementFrame when you want to open the DeleteRecipeFrame
    public static void openDeleteRecipeFrame() {
        new DeleteRecipeFrame();
    }
}