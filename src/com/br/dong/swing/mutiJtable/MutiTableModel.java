package com.br.dong.swing.mutiJtable;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public class MutiTableModel extends AbstractTableModel {
    /**
     * ID
     */
    private static final long serialVersionUID = -1264704523326656387L;

    /**
     * データ数が不正です
     */
    private static final String ERROR_DATA_LENGTH = "データ数が不正です。";

    /**
     * デフォールトコラム
     */
    protected int checkColumn = -1;

    /**
     * コラム名のコレクション
     */
    protected List <String> columnNames;

    /**
     * データのコレクション
     */
    protected List <Object> contents;

    /**
     * コンストラクション。<br>
     */
    public MutiTableModel() {
        // コラム名のコレクション
        this.columnNames = new Vector <String>();
        // データのコレクション
        this.contents = new Vector <Object>();
    }

    /**
     * コンストラクション。<br>
     *
     * @param columnNames コラム名のコレクション
     */
    public MutiTableModel(String[] columnNames) {
        this();

        if (null == columnNames) {
            // コラムがヌルの場合
            return;
        }

        for (String columnName : columnNames) {
            // コラム名を繰り返し、追加する
            this.columnNames.add(columnName);
        }
    }

    /**
     * コンストラクション。<br>
     *
     * @param datas データのコレクション
     * @param columnNames コラム名のコレクション
     * @throws Exception データ数が不正の異常
     */
    public MutiTableModel(Object[][] datas, String[] columnNames) throws Exception {
        this(columnNames);
        // データのコレクションを設定する
        refreshContents(datas);
    }

    /**
     * データのコレクションを更新する。<br>
     *
     * @param datas　データのコレクション
     * @throws Exception データ数が不正の異常
     */
    public void refreshContents(Object[][] datas) throws Exception {
        // データをクリーアする
        this.contents.clear();

        if (null == datas) {
            // データがヌルの場合
            return;
        }

        for (Object[] data : datas) {
            // 該当行のデータを取得する
            addRow(data);
        }
    }

    /**
     * 一つ行のデータを追加する。<br>
     *
     * @param data　データ
     * @throws Exception データ数が不正の異常
     */
    public void addRow(Object[] data) throws Exception {
        if (null == data) {
            // データなヌルの場合
            return;
        }

        if (this.columnNames.size() != data.length) {
            // データ数が不正の場合
            throw new Exception(ERROR_DATA_LENGTH);
        }

        // 行のデータを作成す
        Vector <Object> content = new Vector <Object>(this.columnNames.size());

        for (int ii = 0; ii < this.columnNames.size(); ii++) {
            // データの分、繰り返し、追加する
            content.add(data[ii]);
        }

        // データを追加する
        contents.add(content);
    }

    /**
     * 指定行で、行を削除する。<br>
     *
     * @param row 行番
     */
    public void removeRow(int row) {
        contents.remove(row);
    }

    /**
     * 指定行から、複数行を削除する。<br>
     *
     * @param row 行番
     * @param count 行数
     */
    public void removeRows(int row, int count) {
        for (int ii = 0; ii < count; ii++) {
            if (contents.size() > row) {
                contents.remove(row);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int col) {
        if (col == this.checkColumn) {
            return true;
        }

        return super.isCellEditable(row, col);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setValueAt(Object value, int row, int col) {
        ((Vector) contents.get(row)).set(col, value);
        this.fireTableCellUpdated(row, col);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    public Class <?> getColumnClass(int column) {
        Object value = getValueAt(0, column);

        if (value != null) {
            return value.getClass();
        }

        return super.getClass();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return this.contents.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object getValueAt(int row, int col) {
        return ((Vector) contents.get(row)).get(col);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    /**
     * デフォールトコラムを取得する。<br>
     *
     * @return デフォールトコラム
     */
    public int getCheckColumn() {
        return checkColumn;
    }

    /**
     * デフォールトコラムを設定する。<br>
     *
     * @param checkColumn デフォールトコラム
     */
    public void setCheckColumn(int checkColumn) {
        this.checkColumn = checkColumn;
    }
}
