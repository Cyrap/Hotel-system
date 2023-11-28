package hotelTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Guest_request extends JPanel {
	
    public Guest_request(int generatedId) {
        setLayout(new BorderLayout());

        JTable dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        loadData(dataTable);

        dataTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                int roomNumber = (int) dataTable.getValueAt(selectedRow, 0);
                makeReservation(generatedId, roomNumber);
            }
        });

        setVisible(true);
    }

    private void loadData(JTable dataTable) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String selectQuery = "SELECT * FROM rooms where Availability = 1";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                Vector<Vector<Object>> data = new Vector<>();
                Vector<String> columnNames = new Vector<>();
                columnNames.add("Room Number");
                columnNames.add("Availability");

                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("RoomNumber"));
                    row.add(resultSet.getBoolean("Availability"));
                    data.add(row);
                }

                DefaultTableModel availableRoomsModel = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                dataTable.setModel(availableRoomsModel);

                int availabilityColumnIndex = getAvailabilityColumnIndex(dataTable);
                if (availabilityColumnIndex != -1) {
                    dataTable.getColumnModel().getColumn(availabilityColumnIndex).setCellRenderer(new AvailabilityCellRenderer());
                }

            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error closing the connection: " + ex.getMessage());
            }
        }
    }

    private int getAvailabilityColumnIndex(JTable dataTable) {
 
        for (int i = 0; i < dataTable.getColumnCount(); i++) {
            if (dataTable.getColumnName(i).equals("Availability")) {
                return i;
            }
        }
        return -1;
    }

    private void makeReservation(long generatedId, int roomNumber) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            int roomId = getRoomIdByNumber(con, roomNumber);

            if (roomId != -1) {
                if (isRoomAvailable(con, roomId)) {
                    String insertReservationQuery = "INSERT INTO requests (guest_id, room_id,status) VALUES (?, ?,'pending')";
                    try (PreparedStatement reservationStatement = con.prepareStatement(insertReservationQuery)) {
                        reservationStatement.setInt(1, (int) generatedId);
                        reservationStatement.setInt(2, roomId);

                        int rowsAffectedReservation = reservationStatement.executeUpdate();

                        if (rowsAffectedReservation > 0) {
                            JOptionPane.showMessageDialog(this, "Request made successfully");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to make reservation");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Room not available");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid roomNumber");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error closing the connection: " + ex.getMessage());
            }
        }
    }
    private boolean isRoomAvailable(Connection con, int roomId) throws SQLException {
        String selectQuery = "SELECT Availability FROM rooms WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, roomId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean("Availability");
            }
        }
    }

    private int getRoomIdByNumber(Connection con, int roomNumber) throws SQLException {
        String selectQuery = "SELECT id FROM rooms WHERE RoomNumber = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("id") : -1;
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Guest_request(1)); 
    }
}
