package com.br.dong.swing.jtable;

import javax.swing.table.DefaultTableModel;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 * 重写jtable的model 使之不可重新编辑
 */
public class JtableModel extends DefaultTableModel {
    public JtableModel(Object[][] data, Object[] columnNames){
        super(data,columnNames);
    }

    //某个单元格是否可以编辑
    public boolean isCellEditable(int row, int column)
    {
         //下表为0
        if(column==4){
            return true;
        }
        return false;
    }
}
