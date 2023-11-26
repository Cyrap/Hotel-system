package hotelTest;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest_SignUp extends JPanel {

    public Guest_SignUp() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel guestNameLabel = new JLabel("Хэрэглэгчийн нэр");
        JTextField guestName = new JTextField(20);

        JLabel passwordLabel = new JLabel("Нууц үг");
        JTextField password = new JTextField(20);


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(guestNameLabel, gbc);
        gbc.gridx = 1;
        add(guestName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(password, gbc);

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> saveRoomData(guestName.getText(), password.getText()));

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(saveButton, gbc);
    }

    private void saveRoomData(String name, String password) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertQuery = "INSERT INTO guest_login(username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                   
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            JOptionPane.showMessageDialog(this, "Data inserted successfully. Generated ID: " + generatedId);

                            GuestPage guestPage = new GuestPage(generatedId);
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to retrieve generated ID");
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
    }


    public static void main(String[] args) {
    }
}


