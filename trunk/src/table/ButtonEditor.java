package table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author arthur
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor{

    protected JButton button;
    private boolean isPushed;

    public ButtonEditor() {
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        isPushed = true;
        JButton b = (JButton) value;
        this.button.setIcon(b.getIcon());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if(isPushed){
            //JOptionPane.showMessageDialog(button, label + ": Ouch!");
            System.out.println("Ouch!");
        }
        isPushed = false;
        return "";
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}