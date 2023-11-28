package hotelTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Employee extends JPanel {

    private JTable dataTable;

    public Employee() {
        setLayout(new BorderLayout());
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        loadData();
        scrollPane.setVisible(true);
    }

    private void loadData() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            String selectQuery = "SELECT * FROM employee";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                Vector<Vector<Object>> data = new Vector<>();
                Vector<String> columnNames = new Vector<>();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(resultSet.getMetaData().getColumnName(i));
                }
                columnNames.add("Delete");

                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    row.add("Delete");
                    data.add(row);
                }

   
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                     
                        return column == getColumnCount() - 1;
                    }
                };
                dataTable.setModel(tableModel);

           
                addButtonColumn(dataTable, tableModel.getColumnCount() - 1);
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

    private void addButtonColumn(JTable table, int column) {
        TableColumn buttonColumn = table.getColumnModel().getColumn(column);
        buttonColumn.setCellRenderer(new ButtonRenderer());
        buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
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
        private String label;
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

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    int selectedRow = dataTable.getSelectedRow();
                    int idToDelete = (int) dataTable.getModel().getValueAt(selectedRow, 0); // Assuming ID is in the first column
                    deleteRow(idToDelete);
                    loadData();
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        private void deleteRow(int idToDelete) {
            Connection con = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

                String deleteQuery = "DELETE FROM employee WHERE id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, idToDelete);
                    preparedStatement.executeUpdate();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error deleting row: " + ex.getMessage());
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Employee());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
