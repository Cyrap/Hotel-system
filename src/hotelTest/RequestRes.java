package hotelTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RequestRes extends JPanel {
    private JTable dataTable;

    public RequestRes() {
        setLayout(new BorderLayout());
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        loadData();
        scrollPane.setVisible(true);
    }

    public void loadData() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            String selectQuery = "SELECT * FROM requests WHERE status = 'pending'";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                Vector<Vector<Object>> data = new Vector<>();
                Vector<String> columnNames = new Vector<>();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(resultSet.getMetaData().getColumnName(i));
                }
                columnNames.add("Decline");
                columnNames.add("Approve");
                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    row.add("Decline");
                    row.add("Approve");
                    data.add(row);
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == getColumnCount() - 1 || column == getColumnCount() - 2;
                    }
                };
                dataTable.setModel(tableModel);
                addButtonColumns(dataTable, tableModel.getColumnCount() - 1, tableModel.getColumnCount() - 2);
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

    private void addButtonColumns(JTable table, int... columns) {
        for (int column : columns) {
            TableColumn buttonColumn = table.getColumnModel().getColumn(column);
            buttonColumn.setCellRenderer(new ButtonRenderer());
            buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }

     
            String label = (value == null) ? "Approve" : value.toString();

            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int selectedRow = dataTable.getSelectedRow();
                int idToHandle = (int) dataTable.getModel().getValueAt(selectedRow, 0);

                if (button.getText().equals("Approve")) {
                    approveRequest(idToHandle);
                } else if (button.getText().equals("Decline")) {
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to decline this request?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        declineRequest(idToHandle);
                    }
                }

      
                loadData();
            }
            isPushed = false;
            return button.getText();
        }

        private void approveRequest(int idToApprove) {
            Connection con = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");


                int roomId = getRoomIdByRequestId(con, idToApprove);
                int guestId = getGuestIdByRequestId(con, idToApprove);


                updateReservation(con, guestId, roomId);

       
                updateRoomAvailability(con, roomId, false);

                String updateQuery = "UPDATE requests SET status = 'approved' WHERE id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, idToApprove);
                    preparedStatement.executeUpdate();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error approving request: " + ex.getMessage());
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

        private void declineRequest(int idToDecline) {
            Connection con = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

       
                String updateQuery = "UPDATE requests SET status = 'declined' WHERE id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, idToDecline);
                    preparedStatement.executeUpdate();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error declining request: " + ex.getMessage());
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

        private int getRoomIdByRequestId(Connection con, int requestId) throws SQLException {
            String selectQuery = "SELECT room_id FROM requests WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, requestId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next() ? resultSet.getInt("room_id") : -1;
                }
            }
        }

        private int getGuestIdByRequestId(Connection con, int requestId) throws SQLException {
            String selectQuery = "SELECT guest_id FROM requests WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, requestId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next() ? resultSet.getInt("guest_id") : -1;
                }
            }
        }

        private void updateReservation(Connection con, int guestId, int roomId) throws SQLException {
            try {
                String updateQuery = "INSERT INTO reservation (guestID, roomID) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, guestId);
                    preparedStatement.setInt(2, roomId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error when trying to post reservation request: " + ex.getMessage());
                throw ex;
            }
        }

        private void updateRoomAvailability(Connection con, int roomId, boolean isAvailable) throws SQLException {
            String updateRoomAvailabilityQuery = "UPDATE rooms SET Availability = ? WHERE id = ?";
            try (PreparedStatement updateStatement = con.prepareStatement(updateRoomAvailabilityQuery)) {
                updateStatement.setBoolean(1, isAvailable);
                updateStatement.setInt(2, roomId);
                updateStatement.executeUpdate();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Guests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new RequestRes());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
