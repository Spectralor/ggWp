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

public class DeleteProductFrame extends JFrame {
    private JTextField productNameField;
    private JButton deleteButton;
    private JButton homeButton;

    public DeleteProductFrame() {
        setTitle("Delete Product");
        setSize(300, 150);
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

        deleteButton = createOrangeButton("Delete Product", 10, 60, 150, 25);
        panel.add(deleteButton);

        homeButton = createOrangeButton("Home", 170, 60, 100, 25);
        panel.add(homeButton);
        	
        // Set the background color of the panel to black
        panel.setBackground(Color.BLACK);
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteProduct();
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

    private void handleDeleteProduct() {
        String productName = productNameField.getText();

        // Delete the product from the database
        deleteProductFromDatabase(productName);

        JOptionPane.showMessageDialog(this, "Product deleted successfully");
        dispose();
    }

    private void deleteProductFromDatabase(String productName) {
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String deleteQuery = "DELETE FROM Products WHERE ProductName = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, productName);

                // Execute the delete statement
                int rowsDeleted = deleteStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Product deleted successfully from the database");
                } else {
                    System.out.println("Failed to delete product from the database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting product from the database");
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
        new DeleteProductFrame().setVisible(true);
    }
}
