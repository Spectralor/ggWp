package com.inventory.Frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainApplicationFrame extends JFrame {

    public MainApplicationFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Main Application Window");
        setSize(562, 718);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set the background color of the content pane to light blue
        getContentPane().setBackground(new Color(193, 194, 255));

        // Add a button to open Inventory Management
        JButton inventoryManagementButton = createMenuButton("Inventory Management", new Color(255, 165, 0)); // Orange

        // Add a button to open Recipe Management
        JButton recipeManagementButton = createMenuButton("Recipe Management", new Color(70, 114, 196)); // Blue

        // Add a button to open Billing
        JButton billingButton = createMenuButton("Billing", new Color(255, 182, 193)); // Light Red

        // Add a button to open Calendar
        JButton calendarButton = createMenuButton("Calendar", new Color(0, 128, 0)); // Green

        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10)); // GridLayout with 4 rows, 1 column, and 10 pixels vertical gap
        panel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80)); // Add padding
        panel.setBackground((Color.BLACK)); // Set background color of the panel to match the content pane
        panel.add(inventoryManagementButton);
        panel.add(recipeManagementButton);
        panel.add(billingButton);
        panel.add(calendarButton); // Add the Calendar button

        add(panel);
    }

    private JButton createMenuButton(String buttonText, Color buttonColor) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Decreased font size
        button.setForeground(Color.WHITE);
        button.setBackground(buttonColor); // Set button background color

        // Set preferred size
        button.setPreferredSize(new Dimension(150, 30));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainApplicationFrame.this, "Opening " + buttonText);
                openMenu(buttonText);
            }
        });

        return button;
    }

    private void openMenu(String menuName) {
        // Implement logic to open the respective window based on the clicked button
        JFrame menuFrame = null;

        switch (menuName) {
            case "Inventory Management":
                menuFrame = new InventoryManagementFrame();
                break;
            case "Recipe Management":
                menuFrame = new RecipeManagementFrame();
                break;
            case "Billing":
                menuFrame = new Bill();
                break;
            case "Calendar":
                // Add logic to open the Calendar frame
                menuFrame = new CalendarFrame();
                break;
        }

        if (menuFrame != null) {
            menuFrame.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and show the main application frame
                MainApplicationFrame mainFrame = new MainApplicationFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
