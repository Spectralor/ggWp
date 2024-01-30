package com.inventory.Frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.inventory.connection.DatabaseManager;

public class LoginRegistrationPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginRegistrationPage() {
        // Set up the frame and components
        setTitle("Login / Signup");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        placeComponents();

        setVisible(true);
    }

    private void placeComponents() {
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        userLabel.setForeground(Color.WHITE);
        add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setBackground(new Color(255, 165, 0));
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(100, 80, 80, 25);
        signupButton.setBackground(new Color(255, 165, 0));
        signupButton.setForeground(Color.WHITE);
        add(signupButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Add action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }
        });
    }

    private void performLogin() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        try (Connection connection = DatabaseManager.connectToDatabase()) {
            // Login
            String selectQuery = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, enteredUsername);
                selectStatement.setString(2, enteredPassword);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Authentication successful
                        JOptionPane.showMessageDialog(this, "Login successful!");
                        // TODO: Open the main application window or perform further actions
                        openMainApplicationWindow();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid username or password");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }

    private void openMainApplicationWindow() {
        // TODO: Implement logic to open the main application window
        // For example, you might create and show a new JFrame for the main window.
        // Replace this with your actual logic.
        MainApplicationFrame mainFrame = new MainApplicationFrame();
        mainFrame.setVisible(true);

        // Close the login frame if needed
        this.dispose();
    }

    private void performSignup() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        // Validate the entered data (you may add more validation as needed)
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try (Connection connection = DatabaseManager.connectToDatabase()) {
            // Check if the username is already taken
            String checkUsernameQuery = "SELECT * FROM Users WHERE Username = ?";
            try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery)) {
                checkUsernameStatement.setString(1, enteredUsername);
                try (ResultSet resultSet = checkUsernameStatement.executeQuery()) {
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different one.");
                        return;
                    }
                }
            }

            // If username is available, proceed with registration
            String insertQuery = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, enteredUsername);
                insertStatement.setString(2, enteredPassword);

                int affectedRows = insertStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    // TODO: You may navigate to another page or perform other actions here
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegistrationPage();
            }
        });
    }
}
