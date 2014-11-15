package com.br.dong.swing.mutiJtable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class MutiTable extends JTable implements MouseListener {
    /**
     * ID
     */
    private static final long serialVersionUID = 584842405181279389L;

    /**
     * デフォールト_チェックヘッダー列
     */
    private final int DEFAULT_CHECKHEADERCOLUMN = -1;

    /**
     * デフォールト_PREFERREDWIDTH
     */
    private final int DEFAULT_PREFERREDWIDTH = 23;

    /**
     * デフォールト_MAXWIDTH
     */
    private final int DEFAULT_MAXWIDTH = 23;

    /**
     * デフォールト_MINWIDTH
     */
    private final int DEFAULT_MINWIDTH = 23;

    /**
     * デフォールトコラム
     */
    private int checkHeaderColumn = DEFAULT_CHECKHEADERCOLUMN;

    /**
     * ヘッダのチェックボックス
     */
    private final HeaderCheckBoxRenderer checkHeader = new HeaderCheckBoxRenderer();

    /**
     * 元のヘッダのレンダー
     */
    private TableCellRenderer oldCheckHeader = null;

    /**
     * 元のテーブルの操作可能状態
     */
    private boolean oldEnable = false;

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.columnAtPoint(e.getPoint()) != this.checkHeaderColumn) {
            // チェックコラム以外の場合
            return;
        }

        if (e.getSource() == this.getTableHeader()) {
            // ヘッダーの場合
            // コラムのチェック
            boolean isSelected = !checkHeader.isSelected();
            checkHeader.setSelected(isSelected);
            this.getTableHeader().repaint();
            // 指定列の全てのセルはチェック状態を設定する
            checkColumnCells(isSelected);
        } else {
            // セルの場合
            // セルのチェック状態を設定する
            int row = this.rowAtPoint(e.getPoint());
            boolean isSelected = !(Boolean) (this.getModel().getValueAt(row, this.checkHeaderColumn));
            this.getModel().setValueAt(isSelected, row, this.checkHeaderColumn);
            // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
            checkColumnHeader();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * コラムを取得する。<br>
     *
     * @return コラム
     */
    public int getCheckHeaderColumn() {
        return checkHeaderColumn;
    }

    /**
     * コラムを設定する。<br>
     *
     * @param checkHeaderColumn コラム
     */
    public void setCheckHeaderColumn(int checkHeaderColumn) {
        // テーブルのコラムを定義する
        TableColumn tableColumn;

        // 元の情報を削除する
        if (isCheckHeader()) {
            // 元のチェックヘッダーが有効である場合
            tableColumn = this.getColumnModel().getColumn(this.checkHeaderColumn);

            // ヘッダーのチェックボックスを設定する
            if (null != oldCheckHeader) {
                tableColumn.setHeaderRenderer(oldCheckHeader);
                // テーブルの操作可能をリカバリする
                this.setEnabled(oldEnable);
            }

            // ヘッダーのマースをモニターする
            this.getTableHeader().removeMouseListener(this);
            // セルのマースをモニターする
            this.removeMouseListener(this);
        }

        // 新たな情報を設定する
        // チェックヘッダーを設定する
        this.checkHeaderColumn = checkHeaderColumn;

        if (!isCheckHeader()) {
            // チェックヘッダーが無効の場合
            this.checkHeaderColumn = DEFAULT_CHECKHEADERCOLUMN;
            return;
        }

        tableColumn = this.getColumnModel().getColumn(this.checkHeaderColumn);
        tableColumn.setPreferredWidth(DEFAULT_PREFERREDWIDTH);
        tableColumn.setMaxWidth(DEFAULT_MAXWIDTH);
        tableColumn.setMinWidth(DEFAULT_MINWIDTH);
        // ヘッダーのチェックボックスを設定する
        oldCheckHeader = tableColumn.getHeaderRenderer();
        tableColumn.setHeaderRenderer(checkHeader);
        // ヘッダーのマースをモニターする
        this.getTableHeader().addMouseListener(this);
        // セルのマースをモニターする
        this.addMouseListener(this);
        // テーブルの操作可能を削除する
        oldEnable = this.isEnabled();

        if (oldEnable) {
            this.setEnabled(false);
        }

        // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
        checkColumnHeader();
    }

    /**
     * 指定列の全てのセルはチェック状態を設定する。<br>
     *
     * @param isCheck チェック状態
     */
    public void checkColumnCells(boolean isCheck) {
        if (!isCheckHeader()) {
            // チェックヘッダーが無効の場合
            return;
        }

        for (int ii = 0; ii < this.getRowCount(); ii++) {
            this.getModel().setValueAt(isCheck, ii, this.checkHeaderColumn);
        }
    }

    /**
     * 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する。<br>
     */
    public void checkColumnHeader() {
        if (hasCheckedRow()) {
            // チェックした行がある場合
            if (this.checkHeader.isSelected()) {
                // ヘッダーがチェックした場合
                return;
            }

            // ヘッダーをチェックにする
            this.checkHeader.setSelected(true);
            this.getTableHeader().repaint();
        } else {
            // チェックした行がない場合
            if (!this.checkHeader.isSelected()) {
                // ヘッダーがチェックしなかった場合
                return;
            }

            // ヘッダーをチェックしなくにする
            this.checkHeader.setSelected(false);
            this.getTableHeader().repaint();
        }
    }

    /**
     * チェックヘッダーがあるかどうかを取得する。<br>
     *
     * @return true、チェックヘッダーがある；false、チェックヘッダーがない
     */
    public boolean isCheckHeader() {
        return !(this.checkHeaderColumn < 0 || this.checkHeaderColumn >= this.getColumnCount());
    }

    /**
     * チェックした行があるかどうかを取得する。<br>
     *
     * @return true、チェックした行がある；false、チェックした行がない
     */
    public boolean hasCheckedRow() {
        if (!isCheckHeader()) {
            // チェックヘッダーが無効の場合
            return false;
        }

        for (int ii = 0; ii < this.getRowCount(); ii++) {
            // 元のチェック状態を取得する
            boolean isCheck = (Boolean) this.getModel().getValueAt(ii, this.checkHeaderColumn);

            if (isCheck) {
                // 該当行がチェックしている場合
                return true;
            }
        }

        return false;
    }

    /**
     * 全てのチェックした行のインデックスを取得する。<br>
     *
     * @return 全てのチェックした行のインデックス
     */
    public List <Integer> getAllCheckedRows() {
        // チェックのセルのインデックスを作成する
        List <Integer> rows = new ArrayList <Integer>();

        if (!isCheckHeader()) {
            // チェックヘッダーが無効の場合
            return rows;
        }

        for (int ii = 0; ii < this.getRowCount(); ii++) {
            // 元のチェック状態を取得する
            boolean isCheck = (Boolean) this.getModel().getValueAt(ii, this.checkHeaderColumn);

            if (isCheck) {
                // 該当行がチェックしている場合
                rows.add(ii);
            }
        }

        return rows;
    }

    /**
     * 指定コラムで、全てのチェックした行の値を取得する。<br>
     *
     * @param col コラム
     * @return　指定コラムの全てのチェックした行の値
     */
    public List <Object> getAllCheckedColumn(int col) {
        // チェックのセルのインデックスを作成する
        List <Object> rows = new ArrayList <Object>();

        if (!isCheckHeader()) {
            // チェックヘッダーが無効の場合
            return rows;
        }

        for (int ii = 0; ii < this.getRowCount(); ii++) {
            // 元のチェック状態を取得する
            boolean isCheck = (Boolean) this.getModel().getValueAt(ii, this.checkHeaderColumn);

            if (isCheck) {
                // 該当行がチェックしている場合
                rows.add(this.getModel().getValueAt(ii, col));
            }
        }

        return rows;
    }

    /**
     * データのコレクションを更新する。<br>
     *
     * @param datas　データのコレクション
     * @throws Exception データ数が不正の異常
     */
    public void refreshContents(Object[][] datas) throws Exception {
        // モデルを取得する
        MutiTableModel mutiTableModel = (MutiTableModel) this.getModel();
        mutiTableModel.refreshContents(datas);
        // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
        checkColumnHeader();
        this.updateUI();
    }

    /**
     * 一つ行のデータを追加する。<br>
     *
     * @param data　データ
     * @throws Exception データ数が不正の異常
     */
    public void addRow(Object[] data) throws Exception {
        // モデルを取得する
        MutiTableModel mutiTableModel = (MutiTableModel) this.getModel();
        mutiTableModel.addRow(data);
        // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
        checkColumnHeader();
        this.updateUI();
    }

    /**
     * 指定行で、行を削除する。<br>
     *
     * @param row 行番
     */
    public void removeRow(int row) {
        // モデルを取得する
        MutiTableModel mutiTableModel = (MutiTableModel) this.getModel();
        mutiTableModel.removeRow(row);
        // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
        checkColumnHeader();
        this.updateUI();
    }

    /**
     * 指定行から、複数行を削除する。<br>
     *
     * @param row 行番
     * @param count 行数
     */
    public void removeRows(int row, int count) {
        // モデルを取得する
        MutiTableModel mutiTableModel = (MutiTableModel) this.getModel();
        mutiTableModel.removeRows(row, count);
        // 全てのセルのチェック状態より、ヘッダーのチェック状態を設定する
        checkColumnHeader();
        this.updateUI();
    }
}

