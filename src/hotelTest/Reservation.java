package hotelTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;

public class Reservation extends JPanel {
    JFrame frame;

    Reservation() {
        frame = new JFrame("reseption Page");
        frame.getContentPane().setBackground(Color.GRAY);
        JLabel adminLabel = new JLabel("Reservation Page");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 30));
        adminLabel.setForeground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        JTabbedPane tabbedPane2 = new JTabbedPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Rooms rooms = new Rooms();
        tabbedPane.addTab("Rooms", rooms);

        Guests guests = new Guests();
        tabbedPane.addTab("Guests", guests);
        
        RequestRes requestRes = new RequestRes();
        tabbedPane.addTab("requests", requestRes);
        
        AddGuest addGuest = new AddGuest();
        tabbedPane2.addTab("Add Guest", addGuest);

        tabbedPane.setUI(new RoundedTabbedPaneUI());
        tabbedPane2.setUI(new RoundedTabbedPaneUI());

        UIManager.put("TabbedPane.selected", new ColorUIResource(Color.BLUE));
        UIManager.put("TabbedPane.unselectedForeground", Color.BLACK);
        UIManager.put("TabbedPane.selectedForeground", Color.BLACK);
        UIManager.put("TabbedPane.background", Color.GRAY);

        tabbedPane.setPreferredSize(new Dimension(frame.getWidth() * 2 / 3, frame.getHeight() * 2 / 3));
        tabbedPane2.setPreferredSize(new Dimension(frame.getWidth() * 1 / 3, frame.getHeight() * 2 / 3));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        frame.add(adminLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        frame.add(tabbedPane, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        frame.add(tabbedPane2, gbc);
        
        tabbedPane.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    rooms.loadData();
                } else if (selectedIndex == 1) {
                    guests.loadData();
                }
            }
        });
        
        
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Reservation());
    }
}
