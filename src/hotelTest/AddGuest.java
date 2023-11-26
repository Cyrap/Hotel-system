package hotelTest;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class AddGuest extends JPanel {

    public AddGuest() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel firstNameLabel = new JLabel("Нэр:");
        JTextField firstName = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Овог:");
        JTextField lastName = new JTextField(20);

        JLabel emailLabel = new JLabel("E-mail:");
        JTextField email = new JTextField(20);

        JLabel phoneLabel = new JLabel("Утас:");
        JTextField phone = new JTextField(20);

        JLabel addressLabel = new JLabel("Хаяг:");
        JTextField address = new JTextField(20);

        JLabel nationalityLabel = new JLabel("Үндэс угсаа:");
        JTextField nationality = new JTextField(20);

        JLabel passwordLabel = new JLabel("Рэгистэр:");
        JTextField passwordNumber = new JTextField(20);

        JButton saveButton = new JButton("Save");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);
        gbc.gridx = 1;
        add(firstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);
        gbc.gridx = 1;
        add(lastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(phoneLabel, gbc);
        gbc.gridx = 1;
        add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(addressLabel, gbc);
        gbc.gridx = 1;
        add(address, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(nationalityLabel, gbc);
        gbc.gridx = 1;
        add(nationality, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        firstNameLabel.setPreferredSize(new Dimension(100, 20));
        lastNameLabel.setPreferredSize(new Dimension(100, 20));
        emailLabel.setPreferredSize(new Dimension(100, 20));
        phoneLabel.setPreferredSize(new Dimension(100, 20));
        addressLabel.setPreferredSize(new Dimension(100, 20));
        nationalityLabel.setPreferredSize(new Dimension(100, 20));
        passwordLabel.setPreferredSize(new Dimension(100, 20));

        saveButton.addActionListener(e -> {
            int guestId = saveGuestData(firstName.getText(), lastName.getText(), email.getText(), phone.getText(), address.getText(), nationality.getText(), passwordNumber.getText());
            if (guestId > 0) {
                SwingUtilities.invokeLater(() -> new ChooseRoom(guestId));
            }
        });

        setVisible(true);
    }

    private int saveGuestData(String firstName, String lastName, String email, String phone, String address, String nationality, String passwordNumber) {
        Connection con = null;
        int guestId = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertQuery = "INSERT INTO guests(FirstName, LastName, Email, Phone, Address, Nationality, PassportNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);

                if (!phone.isEmpty()) {
                    preparedStatement.setInt(4, Integer.parseInt(phone));
                } else {
                    preparedStatement.setNull(4, Types.INTEGER);
                }

                preparedStatement.setString(5, address);
                preparedStatement.setString(6, nationality);
                preparedStatement.setString(7, passwordNumber);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            guestId = generatedKeys.getInt(1);
                            JOptionPane.showMessageDialog(this, "Data inserted successfully. Guest ID: " + guestId);
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to retrieve guest ID");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert data");
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

        return guestId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddGuest());
    }
}

