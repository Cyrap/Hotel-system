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

public class Guest_request_page extends JPanel {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private long generatedID; // Add a field to store the generated ID

    public Guest_request_page(long generatedID) {
        this.generatedID = generatedID; // Initialize the generated ID
        setLayout(new BorderLayout());
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        loadData();

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteData());
        JPanel buttonPanel = new JPanel();
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
            if (dataTable.getColumnName(i).equals("status")) {
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
            String selectQuery = "SELECT * FROM requests WHERE guest_id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, generatedID); // Set the generated ID as a parameter
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

    private void deleteData() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                int idToDelete = (int) tableModel.getValueAt(selectedRow, 0);
                String deleteQuery = "DELETE FROM requests WHERE id=?";
                try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, idToDelete);
                    preparedStatement.executeUpdate();
                }
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Устгах хүсэлтийн мөрийг оруулна уу");
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
            String isAvailable = (String) value;
            if ("approved".equals(isAvailable)) {
                cellComponent.setBackground(Color.GREEN);
            } else if ("declined".equals(isAvailable)) {
                cellComponent.setBackground(Color.RED);
            }else {
            	cellComponent.setBackground(Color.gray);            }
            
            cellComponent.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
            return cellComponent;
        }
    }

    public static void main(String[] args) {
        // You can add your main method logic here if needed
    }
}
