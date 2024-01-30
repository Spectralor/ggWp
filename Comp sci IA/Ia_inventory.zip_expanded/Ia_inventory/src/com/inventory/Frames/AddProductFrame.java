package com.inventory.Frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.inventory.Dao.Product;
import com.inventory.connection.DatabaseManager;

public class AddProductFrame extends JFrame {
    private JTextField productNameField;
    private JTextField quantityField;
    private JButton addButton;
    private JButton homeButton;

    public AddProductFrame() {
        setTitle("Add Product");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame, not the entire application

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        // You may add additional initialization logic or event handling specific to the AddProductFrame here
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(10, 20, 80, 25);
        productNameLabel.setForeground(Color.WHITE);
        panel.add(productNameLabel);

        productNameField = new JTextField(20);
        productNameField.setBounds(100, 20, 165, 25);
        panel.add(productNameField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 50, 80, 25);
        quantityLabel.setForeground(Color.WHITE);
        panel.add(quantityLabel);

        quantityField = new JTextField(20);
        quantityField.setBounds(100, 50, 165, 25);
        panel.add(quantityField);

        addButton = createOrangeButton("Add Product", 10, 80, 120, 25);
        panel.add(addButton);

        homeButton = createOrangeButton("Home", 140, 80, 120, 25);
        panel.add(homeButton);

        // Set the background color of the panel to black
        panel.setBackground(Color.BLACK);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddProduct();
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

    private void handleAddProduct() {
        // Extracting data from text fields
        String productName = productNameField.getText();
        String quantityStr = quantityField.getText();

        try {
            // Parsing quantity as an integer
            int quantity = Integer.parseInt(quantityStr);

            // Validating quantity
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive number.");
            } else {
                // Creating a Product object
                Product newProduct = new Product(productName, quantity);

                // Adding the product to the database
                if (DatabaseManager.addProduct(newProduct)) {
                    JOptionPane.showMessageDialog(this, "Product added successfully");
                    // Close the AddProductFrame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product to the database");
                }
            }
        } catch (NumberFormatException ex) {
            // Handling invalid quantity format
            JOptionPane.showMessageDialog(this, "Invalid quantity format. Please enter a valid number.");
        }
    }


    private void navigateToMainApplication() {
        // Implement logic to navigate back to the main application window
    	InventoryManagementFrame mainApplicationFrame = new InventoryManagementFrame();
        mainApplicationFrame.setVisible(true);

        // Close the current frame
        dispose();
    }

    // Other methods specific to the AddProductFrame can be added here
}
