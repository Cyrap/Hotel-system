package hotelTest;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployee extends JPanel {

    public AddEmployee() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Нэр:");
        JTextField employeeName = new JTextField(20);

        JLabel positionLabel = new JLabel("Албан тушаал:");
        JTextField employeePosition = new JTextField(20);

        JLabel ageLabel = new JLabel("Нас:");
        JTextField employeeAge = new JTextField(20);

        JLabel salaryLabel = new JLabel("Цалин:");
        JTextField employeeSalary = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(employeeName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(positionLabel, gbc);
        gbc.gridx = 1;
        add(employeePosition, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ageLabel, gbc);
        gbc.gridx = 1;
        add(employeeAge, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(salaryLabel, gbc);
        gbc.gridx = 1;
        add(employeeSalary, gbc);

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> saveRoomData(employeeName.getText(), employeePosition.getText(), employeeAge.getText(), employeeSalary.getText()));

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(saveButton, gbc);
    }

    private void saveRoomData(String name, String position, String age, String salary) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String insertQuery = "INSERT INTO employee(name, position, age, salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, position);
                preparedStatement.setString(3, age);
                preparedStatement.setString(4, salary);

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
