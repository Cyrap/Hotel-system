package hotelTest;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class MakeFeedBack extends JPanel {

    public MakeFeedBack() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel firstNameLabel = new JLabel("Нэр:");
        JTextField firstName = new JTextField(20);
        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);
        gbc.gridx = 1;
        add(firstName, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(saveButton, gbc);
        firstNameLabel.setPreferredSize(new Dimension(100, 20));
        saveButton.addActionListener(e -> {
            saveGuestData(firstName.getText());
        });

        setVisible(true);
    }

    private int saveGuestData(String firstName) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertQuery = "INSERT INTO feedback(comment) VALUES (?)";
            
            
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, firstName);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data insert successfully made");
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
		return 0;

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MakeFeedBack());
    }
}

