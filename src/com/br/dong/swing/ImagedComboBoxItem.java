package com.br.dong.swing;
import javax.swing.Icon;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-15
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class ImagedComboBoxItem {
    private Icon icon = null;
    private String text = null;
    private int indent = 0;
    ImagedComboBoxItem(String text, Icon icon, int indent) {
        this.text = text;
        this.icon = icon;
        this.indent = indent;
    }
    public String getText() {
        return text;
    }
    public Icon getIcon() {
        return icon;
    }
    public int getIndent() {
        return indent;
    }
}
