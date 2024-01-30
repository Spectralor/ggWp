package com.inventory.Frames;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InventoryManagementFrame extends JFrame {
    private JButton addProductButton;
    private JButton viewProductButton;
    private JButton updateProductButton;
    private JButton deleteProductButton;
    private JButton homeButton;

    public InventoryManagementFrame() {
        setTitle("Inventory Management");
        setSize(400, 300);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        addProductButton = createRandomColorButton("Add Product");
        viewProductButton = createRandomColorButton("View Product");
        updateProductButton = createRandomColorButton("Update Product");
        deleteProductButton = createRandomColorButton("Delete Product");
        homeButton = createRandomColorButton("Home");
        
       
        panel.setBackground((Color.BLACK)); // Set background color of the panel to match the content pane

        panel.add(addProductButton);
        panel.add(viewProductButton);
        panel.add(updateProductButton);
        panel.add(deleteProductButton);
        panel.add(homeButton);

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddProductFrame();
            }
        });

        viewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewProductFrame();
            }
        });

        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateProductFrame();
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteProductFrame();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToMainApplication();
            }
        });
    }

    private JButton createRandomColorButton(String text) {
        JButton button = new JButton(text);

        // Generate random RGB values for the button's background color
        Random random = new Random();
        int black = random.nextInt(256);
        int orange = random.nextInt(256);
        int red = random.nextInt(256);

        Color backgroundColor = new Color(black, orange, red);
        button.setBackground(backgroundColor);

        // Determine whether to use white or black text based on the background color brightness
        double brightness = (0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() + 0.114 * backgroundColor.getBlue()) / 255;
        button.setForeground(brightness > 0.5 ? Color.BLACK : Color.WHITE);

        return button;
    }


    private void openAddProductFrame() {
        new AddProductFrame().setVisible(true);
    }

    private void openViewProductFrame() {
        new ViewProductFrame().setVisible(true);
    }

    private void openUpdateProductFrame() {
        new UpdateProductFrame().setVisible(true);
    }

    private void openDeleteProductFrame() {
        new DeleteProductFrame().setVisible(true);
    }

    private void navigateToMainApplication() {
        // Implement logic to navigate back to the main application window
        MainApplicationFrame mainApplicationFrame = new MainApplicationFrame();
        mainApplicationFrame.setVisible(true);

        // Close the current frame
        this.dispose();
    }

    public static void main(String[] args) {
        new InventoryManagementFrame();
    }
}
