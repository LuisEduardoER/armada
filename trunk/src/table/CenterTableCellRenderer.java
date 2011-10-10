/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author arthur
 */
public class CenterTableCellRenderer extends DefaultTableCellRenderer {

    public CenterTableCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
