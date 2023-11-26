
package hotelTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class GuestPage extends JFrame {
    JFrame frame;
    GuestPage(long generatedId) {
        frame = new JFrame("GuestPage Page");
        frame.getContentPane().setBackground(Color.GRAY);
        JLabel adminLabel = new JLabel("Welcome, Guest!");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 30));
        adminLabel.setForeground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JTabbedPane tabbedPane2 = new JTabbedPane();
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Guest_request guest_request = new Guest_request((int) generatedId);
        tabbedPane2.addTab("make request",guest_request );
        
        Guest_request_page guest_req_page = new Guest_request_page((int) generatedId);
        tabbedPane.addTab("guest request page", guest_req_page);
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
        gbc.gridwidth = 2;
        frame.add(adminLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        frame.add(tabbedPane, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        frame.add(tabbedPane2, gbc);

        frame.setVisible(true);
    }

    public static void main(String args[]) {
    }
}
