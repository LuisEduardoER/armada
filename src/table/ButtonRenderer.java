/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author arthur
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/entrar.png")));
        this.setText("");
        this.setMargin(new java.awt.Insets(2, 2, 2, 2));
        return this;
    }
}
