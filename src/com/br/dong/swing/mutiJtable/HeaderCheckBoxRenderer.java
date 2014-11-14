package com.br.dong.swing.mutiJtable;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public class HeaderCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
    /**
     * ID
     */
    private static final long serialVersionUID = -3224639986882887200L;

    /**
     * コンストラクション。<br>
     */
    public HeaderCheckBoxRenderer() {
        this.setBorderPainted(true);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
     * boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        return this;
    }

}
