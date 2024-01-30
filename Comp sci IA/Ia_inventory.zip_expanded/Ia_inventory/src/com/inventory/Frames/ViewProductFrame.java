package com.inventory.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.inventory.connection.DatabaseManager;

public class ViewProductFrame extends JFrame {
    private JTextArea productDisplayArea;

    public ViewProductFrame() {
        setTitle("View Products");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);
        setContentPane(createContentPane());  // Explicitly set the content pane

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        // Set the text color and font size of the JTextArea
        productDisplayArea.setForeground(new Color(255, 165, 0));
        productDisplayArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Set your desired font size
        panel.setBackground(Color.BLACK);
        populateProductDisplayArea();
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        return contentPane;
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());

        productDisplayArea = new JTextArea();
        productDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productDisplayArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Add "Home" button at the bottom
        JButton homeButton = createOrangeButton("Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToMainApplication();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); // Align the button to the right
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(homeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createOrangeButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 165, 0));
        button.setForeground(Color.WHITE);

        return button;
    }

    private void populateProductDisplayArea() {
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "SELECT ProductName, Quantity FROM Products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder productInfo = new StringBuilder();
                    while (resultSet.next()) {
                        String productName = resultSet.getString("ProductName");
                        int quantity = resultSet.getInt("Quantity");

                        productInfo.append(productName).append(" - Quantity: ").append(quantity).append("\n");
                    }
                    productDisplayArea.setText(productInfo.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
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
        new ViewProductFrame().setVisible(true);
    }
}
