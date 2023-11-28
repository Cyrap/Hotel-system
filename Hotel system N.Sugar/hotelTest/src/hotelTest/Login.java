package hotelTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login extends JFrame {
    JFrame frame;

    public Login() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(new File("C:\\Users\\cyrap\\eclipse-workspace\\hotelTest\\img\\background.jpg"));
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        
        LoginWhitReservationPanel res = new LoginWhitReservationPanel();
        LoginWhitAdmin admin = new LoginWhitAdmin();
        LoginWhitGuest guest = new LoginWhitGuest();
        Guest_SignUp guestLogin = new Guest_SignUp();
        JTabbedPane tbp = new JTabbedPane();
        tbp.addTab("Reservation", res);
        tbp.addTab("Admin", admin);
        tbp.addTab("guest", guest);
        tbp.addTab("Guest signUp", guestLogin);
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(tbp, gbc);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panel.add(centerPanel, gridBagConstraints);

        add(panel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
