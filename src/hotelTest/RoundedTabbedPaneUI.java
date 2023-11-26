package hotelTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class RoundedTabbedPaneUI extends BasicTabbedPaneUI {
    private static final int ARC_WIDTH = 16;
    private static final int ARC_HEIGHT = 16;

    private Color unselectedTabColor = Color.GRAY;
    private Color selectedTabColor = Color.white;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabAreaInsets.left = 10; // Adjust the left padding
        tabAreaInsets.right = 10; // Adjust the right padding
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        if (isSelected) {
            g2.setColor(selectedTabColor);
        } else {
            g2.setColor(unselectedTabColor);
        }
        g2.fillRoundRect(x, y, w, h, ARC_WIDTH, ARC_HEIGHT);
        g2.dispose();
    }
}
