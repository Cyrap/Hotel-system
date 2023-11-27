// Guest_SignUp.java

package hotelTest;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest_SignUp extends JPanel {

    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField nationalityField;
    private JTextField passportNumberField;

    public Guest_SignUp() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel guestNameLabel = new JLabel("Хэрэглэгчийн нэр");
        JTextField guestName = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Овог");
        lastNameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Имэйл");
        emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Утасны дугаар");
        phoneField = new JTextField(20);

        JLabel addressLabel = new JLabel("Хаяг");
        addressField = new JTextField(20);

        JLabel nationalityLabel = new JLabel("Үндсэн национал");
        nationalityField = new JTextField(20);

        JLabel passportNumberLabel = new JLabel("Пасспорт дугаар");
        passportNumberField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Нууц үг");
        JTextField password = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(guestNameLabel, gbc);
        gbc.gridx = 1;
        add(guestName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(phoneLabel, gbc);
        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(addressLabel, gbc);
        gbc.gridx = 1;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(nationalityLabel, gbc);
        gbc.gridx = 1;
        add(nationalityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(passportNumberLabel, gbc);
        gbc.gridx = 1;
        add(passportNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(password, gbc);

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> saveGuestData(
                guestName.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText(),
                nationalityField.getText(),
                passportNumberField.getText(),
                password.getText()
        ));

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(saveButton, gbc);
    }

    private void saveGuestData(String name, String lastName, String email, String phone, String address, String nationality, String passportNumber, String password) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertGuestQuery = "INSERT INTO guests(FirstName, LastName, Email, Phone, Address, Nationality, PassportNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement guestStatement = con.prepareStatement(insertGuestQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                guestStatement.setString(1, name);
                guestStatement.setString(2, lastName);
                guestStatement.setString(3, email);
                guestStatement.setString(4, phone);
                guestStatement.setString(5, address);
                guestStatement.setString(6, nationality);
                guestStatement.setString(7, passportNumber);

                int rowsAffected = guestStatement.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = guestStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            JOptionPane.showMessageDialog(this, "Guest data inserted successfully. Generated ID: " + generatedId);
                            String insertLoginQuery = "INSERT INTO guest_login(username, password, guest_id) VALUES (?, ?, ?)";
                            try (PreparedStatement loginStatement = con.prepareStatement(insertLoginQuery)) {
                                loginStatement.setString(1, name);
                                loginStatement.setString(2, password);
                                loginStatement.setLong(3, generatedId);

                                int loginRowsAffected = loginStatement.executeUpdate();

                                if (loginRowsAffected > 0) {
                                    JOptionPane.showMessageDialog(this, "Login data inserted successfully");
                                    GuestPage guestPage = new GuestPage(generatedId);
                                } else {
                                    JOptionPane.showMessageDialog(this, "Failed to insert login data");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to retrieve generated ID");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert guest data");
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error closing the connection: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Guest Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Guest_SignUp());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
