package com.inventory.Frames;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.inventory.connection.DatabaseManager;

public class CalendarFrame extends JFrame {

    private JTextField usernameField;
    private JDateChooser dateChooser;
    private JList<String> dataList;

    public CalendarFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Calendar Window");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        add(panel);

        JLabel usernameLabel = createLabel("Recipe name");
        usernameLabel.setBounds(50, 30, 100, 25);
        usernameLabel.setForeground(new Color(255, 165, 0));
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 30, 150, 25);
        panel.add(usernameField);

        JLabel calendarLabel = createLabel("Calendar");
        calendarLabel.setBounds(50, 70, 100, 25);
        calendarLabel.setForeground(new Color(255, 165, 0));
        panel.add(calendarLabel);

        dateChooser = createDateChooser();
        dateChooser.setBounds(160, 70, 150, 30);
        panel.add(dateChooser);

        JButton saveButton = createButton("Save", new Color(255, 165, 0));
        saveButton.setBounds(160, 120, 100, 30);
        panel.add(saveButton);

        dataList = new JList<>(getDataFromDatabase());
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(dataList);
        scrollPane.setBounds(50, 160, 300, 100);
        panel.add(scrollPane);

        JButton deleteButton = createButton("Delete", new Color(255, 165, 0));
        deleteButton.setBounds(160, 280, 100, 30);
        panel.add(deleteButton);

        JButton homeButton = createButton("Home", Color.ORANGE);
        homeButton.setBounds(50, 320, 300, 30); // Set the width to cover the entire content area
        panel.add(homeButton);
     
        homeButton.addActionListener(e -> openMainApplicationWindow());                    
        saveButton.addActionListener(e -> saveDataToDatabase());
        deleteButton.addActionListener(e -> deleteDataFromDatabase());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        return button;
    }

    private JDateChooser createDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        return dateChooser;
    }

    private String[] getDataFromDatabase() {
        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "SELECT recipe_name, calendar_data FROM calendar_data";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    while (resultSet.next()) {
                        String recipeName = resultSet.getString("recipe_name");
                        String calendarData = resultSet.getString("calendar_data");
                        listModel.addElement(recipeName + " - " + calendarData);
                    }
                    return Collections.list(listModel.elements()).toArray(new String[0]);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(CalendarFrame.this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
            return new String[]{};
        }
    }

    private void saveDataToDatabase() {
        String username = usernameField.getText();
        String selectedDate = getSelectedDateAsString();

        try (Connection connection = DatabaseManager.connectToDatabase()) {
            String query = "INSERT INTO calendar_data (recipe_name, calendar_data) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, selectedDate);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(CalendarFrame.this, "Data saved successfully.");
                    refreshDataList();
                } else {
                    JOptionPane.showMessageDialog(CalendarFrame.this, "Failed to save data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(CalendarFrame.this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDataFromDatabase() {
        String selectedData = dataList.getSelectedValue();

        if (selectedData != null) {
            String[] parts = selectedData.split(" - ");
            String username = parts[0];
            String selectedDate = parts[1];

            try (Connection connection = DatabaseManager.connectToDatabase()) {
                String query = "DELETE FROM calendar_data WHERE recipe_name = ? AND calendar_data = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, selectedDate);

                    int rowsDeleted = preparedStatement.executeUpdate();

                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(CalendarFrame.this, "Data deleted successfully.");
                        refreshDataList();
                    } else {
                        JOptionPane.showMessageDialog(CalendarFrame.this, "No matching data found for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(CalendarFrame.this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(CalendarFrame.this, "No record selected for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDataList() {
        dataList.setListData(getDataFromDatabase());
    }

    private String getSelectedDateAsString() {
        java.util.Date selectedDate = dateChooser.getDate();
        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(selectedDate);
        } else {
            return "";
        }
    }
    private void openMainApplicationWindow() {
        // TODO: Implement logic to open the main application window
        // For example, you might create and show a new JFrame for the main window.
        // Replace this with your actual logic.
        MainApplicationFrame mainFrame = new MainApplicationFrame();
        mainFrame.setVisible(true);

        // Close the current frame if needed
        this.dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarFrame().setVisible(true));
    }
}
