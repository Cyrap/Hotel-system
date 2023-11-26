package hotelTest;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register_guests extends JPanel {

    public Register_guests() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel roomNumberLabel = new JLabel("Өрөөний дугаар:");
        JTextField roomNumber = new JTextField(20);

        JLabel roomTypeLabel = new JLabel("Ангилал:");
        JTextField roomType = new JTextField(20);

        JLabel roomBedTypeLabel = new JLabel("Орны төрөл:");
        JTextField roomBedType = new JTextField(20);

        JLabel amountLabel = new JLabel("Дүн:");
        JTextField roomRate = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(roomNumberLabel, gbc);
        gbc.gridx = 1;
        add(roomNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(roomTypeLabel, gbc);
        gbc.gridx = 1;
        add(roomType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(roomBedTypeLabel, gbc);
        gbc.gridx = 1;
        add(roomBedType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(amountLabel, gbc);
        gbc.gridx = 1;
        add(roomRate, gbc);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveRoomData(roomNumber.getText(), roomType.getText(), roomBedType.getText(), roomRate.getText()));

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(saveButton, gbc);
    }

    private void saveRoomData(String roomNumber, String roomType, String roomBedType, String roomRate) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertQuery = "INSERT INTO guests(RoomNumber, RoomType, BedType, RoomRate, Availability) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, roomNumber);
                preparedStatement.setString(2, roomType);
                preparedStatement.setString(3, roomBedType);
                preparedStatement.setString(4, roomRate);
                preparedStatement.setInt(5, 0);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data inserted successfully");
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


