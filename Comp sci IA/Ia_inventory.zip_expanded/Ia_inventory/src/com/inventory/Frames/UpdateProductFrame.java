package com.inventory.Frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.inventory.connection.DatabaseManager;

public class UpdateProductFrame extends JFrame {
    private JTextField productNameField;
    private JTextField newQuantityField;
    private JButton updateButton;
    private JButton homeButton;

    public UpdateProductFrame() {
        setTitle("Update Product");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(10, 20, 100, 25);
        productNameLabel.setForeground(Color.WHITE);
        panel.add(productNameLabel);

        productNameField = new JTextField(20);
        productNameField.setBounds(120, 20, 150, 25);
        panel.add(productNameField);

        JLabel newQuantityLabel = new JLabel("New Quantity:");
        newQuantityLabel.setBounds(10, 50, 100, 25);
        newQuantityLabel.setForeground(Color.WHITE);
        panel.add(newQuantityLabel);

        newQuantityField = new JTextField(20);
        newQuantityField.setBounds(120, 50, 150, 25);
        panel.add(newQuantityField);

        updateButton = createOrangeButton("Update Product", 10, 80, 150, 25);
        panel.add(updateButton);

        homeButton = createOrangeButton("Home", 170, 80, 100, 25);
        panel.add(homeButton);
        panel.setBackground(Color.BLACK);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateProduct();
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

    private void handleUpdateProduct() {
        String productName = productNameField.getText();
        String newQuantityStr = newQuantityField.getText();

        try {
            int newQuantity = Integer.parseInt(newQuantityStr);

            // Update the product in the database
            updateProductInDatabase(productName, newQuantity);

            JOptionPane.showMessageDialog(this, "Product updated successfully");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid new quantity. Please enter a valid number.");
        }
    }

    private void updateProductInDatabase(String productName, int newQuantity) {
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String updateQuery = "UPDATE Products SET Quantity = ? WHERE ProductName = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, newQuantity);
                updateStatement.setString(2, productName);

                // Execute the update statement
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Product updated successfully in the database");
                } else {
                    System.out.println("Failed to update product in the database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating product in the database");
        }
    }

    private void navigateToMainApplication() {
        // Implement logic to navigate back to the main application window
    	InventoryManagementFrame mainApplicationFrame = new InventoryManagementFrame();
        mainApplicationFrame.setVisible(true);

        // Close the current frame
        dispose();
    }

    public static void main(String[] args) {
        new UpdateProductFrame().setVisible(true);
    }
}
