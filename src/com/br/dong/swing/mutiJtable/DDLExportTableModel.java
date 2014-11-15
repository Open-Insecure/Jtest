package com.br.dong.swing.mutiJtable;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
public class DDLExportTableModel extends MutiTableModel{
    /**
     * ID
     */
    private static final long serialVersionUID = 2901808435846952546L;

    /**
     * ヘッダー文字
     */
    private static final String[] HEAD_TITLE = new String[] {"選択", "項番", "テーブル名", "タイプ"};

    /**
     * コンストラクション。<br>
     */
    public DDLExportTableModel() {
        super(HEAD_TITLE);
        // デーフォールトチェックコラム
        this.setCheckColumn(0);
    }

    /**
     * コンストラクション。<br>
     *
     * @param datas データのコレクション
     * @throws Exception データ数が不正の異常
     */
    public DDLExportTableModel(Object[][] datas) throws Exception {
        this();
        // データのコレクションを設定する
        refreshContents(datas);
    }
}
