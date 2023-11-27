package hotelTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWhitGuest extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    JFrame frame;

    public LoginWhitGuest() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel header = new JLabel("Guest нэвтрэх хэсэг");
        JLabel usernameLabel = new JLabel("Нэвтрэх нэр:");
        JLabel passwordLabel = new JLabel("Нууц үг:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Нэвтрэх");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(loginButton, gbc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }


    private void handleLogin() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        String jdbcUrl = "jdbc:mysql://localhost:3306/hotel";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM guest_login WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int guestId = resultSet.getInt("guest_id"); 
                    JOptionPane.showMessageDialog(this, "Login Successful. Guest ID: " + guestId);

                    GuestPage guestPage = new GuestPage(guestId);
                   
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
  
         JFrame frame = new JFrame("Login");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(new LoginWhitGuest());
         frame.setSize(400, 300);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
    }
}
