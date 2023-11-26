package hotelTest;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Feedback extends JPanel {
 private JTable dataTable;
 private DefaultTableModel tableModel;

 public Feedback() {
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
         String selectQuery = "SELECT * FROM feedback";
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
 public static void main(String[] args) {
 }
}


