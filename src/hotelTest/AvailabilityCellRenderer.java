package hotelTest;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class AvailabilityCellRenderer extends DefaultTableCellRenderer {
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
