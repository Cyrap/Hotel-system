package hotelTest;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
public class Rooms extends JPanel {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    public Rooms() {
        setLayout(new BorderLayout());
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        loadData();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateData());
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteData());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        scrollPane.setVisible(true);
        deleteButton.setVisible(true);
        setAvailabilityCellRenderer();
    }
    private void setAvailabilityCellRenderer() {
        int availabilityColumnIndex = getAvailabilityColumnIndex();
        if (availabilityColumnIndex != -1) {
            dataTable.getColumnModel().getColumn(availabilityColumnIndex).setCellRenderer(new AvailabilityCellRenderer());
        }
    }
    private int getAvailabilityColumnIndex() {
        for (int i = 0; i < dataTable.getColumnCount(); i++) {
            if (dataTable.getColumnName(i).equals("Availability")) {
                return i;
            }
        }
        return -1;
    }
public void loadData() {
    Connection con = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
        String selectQuery = "SELECT * FROM rooms ORDER BY Availability DESC, RoomNumber ASC";
        try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Vector<Vector<Object>> data = new Vector<>();
            Vector<String> columnNames = new Vector<>();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }
            tableModel = new DefaultTableModel(data, columnNames); 
            dataTable.setModel(tableModel);
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
    private void updateData() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String updateQuery = "UPDATE rooms SET RoomNumber=?, RoomType=?, BedType=?, RoomRate=? WHERE id=?";
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    preparedStatement.setInt(1, id);
                    for (int col = 1; col < tableModel.getColumnCount(); col++) {
                        Object value = tableModel.getValueAt(row, col);
                        if (value instanceof Boolean) {
                            preparedStatement.setBoolean(col, (Boolean) value);
                        } else {
                            preparedStatement.setObject(col, value);
                        }
                    }
                    preparedStatement.executeUpdate();
                }
            }
            loadData();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
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
    private void deleteData() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                int idToDelete = (int) tableModel.getValueAt(selectedRow, 0);
                String deleteQuery = "DELETE FROM rooms WHERE id=?";
                try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, idToDelete);
                    preparedStatement.executeUpdate();
                }
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
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
            }
        }
    }
    private class AvailabilityCellRenderer extends DefaultTableCellRenderer {
        private static final int PADDING = 5; 
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel cellComponent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            boolean isAvailable = (boolean) value;
            cellComponent.setText(isAvailable ? "Available" : "Not Available");
            if (isAvailable) {
                cellComponent.setBackground(Color.GREEN);
            } else {
                cellComponent.setBackground(Color.RED);
            }
            cellComponent.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
            return cellComponent;
        }
    }
    public static void main(String[] args) {
    }
}
