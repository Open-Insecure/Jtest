package com.br.dong.swing;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 * Jlist与复选框的整合
 *
 */
public class CheckListExample extends JFrame {
    public CheckListExample() {
        super("CheckList Example");
        String[] strs = { "swing", "home", "basic", "metal", "JList" };
        final JList list = new JList(createData(strs)); //初始化JList
        // set "home" icon
        Icon icon = MetalIconFactory.getFileChooserHomeFolderIcon();   //对应home的icon图标
        ((CheckableItem) list.getModel().getElementAt(1)).setIcon(icon);//给list的第二个元素设置icon的样式
        list.setCellRenderer(new CheckListRenderer());    //设置渲染？
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //设置单选
        list.setBorder(new EmptyBorder(0, 4, 0, 0));   //
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {         //鼠标点击事件
                int index = list.locationToIndex(e.getPoint()); //鼠标点击的目标
                CheckableItem item = (CheckableItem) list.getModel() //鼠标点击的选项
                        .getElementAt(index);
                item.setSelected(!item.isSelected());   //设置复选框为选中
                Rectangle rect = list.getCellBounds(index, index);
                list.repaint(rect);
            }
        });
        JScrollPane sp = new JScrollPane(list);  //设置滑动条

        final JTextArea textArea = new JTextArea(3, 10);  //打印的文本区域
        JScrollPane textPanel = new JScrollPane(textArea); //针对textArea配置一个滑动条
        JButton printButton = new JButton("print");  //打印按钮
        printButton.addActionListener(new ActionListener() {   //打印按钮监听事件
            public void actionPerformed(ActionEvent e) {
                ListModel model = list.getModel();  //获得JList的数据
                int n = model.getSize();
                for (int i = 0; i < n; i++) {  //循环打印出选中的JList
                    CheckableItem item = (CheckableItem) model.getElementAt(i);
                    if (item.isSelected()) {
                        textArea.append(item.toString());
                        textArea.append(System.getProperty("line.separator"));
                    }
                }
            }
        });
        JButton clearButton = new JButton("clear");        //清除按钮
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(printButton);
        panel.add(clearButton);
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.EAST);
        getContentPane().add(textPanel, BorderLayout.SOUTH);
    }

    /**
     * 初始化创建JList的数据
     * @param strs
     * @return
     */
    private CheckableItem[] createData(String[] strs) {
        int n = strs.length;
        CheckableItem[] items = new CheckableItem[n];
        for (int i = 0; i < n; i++) {
            items[i] = new CheckableItem(strs[i]);
        }
        return items;
    }

    /**
     * 复选组件
     */
    class CheckableItem {
        private String str;

        private boolean isSelected;

        private Icon icon;

        public CheckableItem(String str) {
            this.str = str;
            isSelected = false;
        }

        public void setSelected(boolean b) {
            isSelected = b;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public String toString() {
            return str;
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        public Icon getIcon() {
            return icon;
        }
    }

    /**
     * 复选组件渲染器 继承 自定义CheckRenderer
     */
    class CheckListRenderer extends CheckRenderer implements ListCellRenderer {
        Icon commonIcon;

        public CheckListRenderer() {
            check.setBackground(UIManager.getColor("List.textBackground"));
            label.setForeground(UIManager.getColor("List.textForeground"));
            commonIcon = UIManager.getIcon("Tree.leafIcon");
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            check.setSelected(((CheckableItem) value).isSelected());
            label.setFont(list.getFont());
            label.setText(value.toString());
            label.setSelected(isSelected);
            label.setFocus(hasFocus);
            Icon icon = ((CheckableItem) value).getIcon();
            if (icon == null) {
                icon = commonIcon;
            }
            label.setIcon(icon);
            return this;
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {}

        CheckListExample frame = new CheckListExample();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}

class CheckRenderer extends JPanel implements TreeCellRenderer {
    protected JCheckBox check;   //复选check

    protected TreeLabel label;

    public CheckRenderer() {
        setLayout(null);
        add(check = new JCheckBox());
        add(label = new TreeLabel());
        check.setBackground(UIManager.getColor("Tree.textBackground"));
        label.setForeground(UIManager.getColor("Tree.textForeground"));
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean isSelected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());
        check.setSelected(((CheckNode) value).isSelected());
        label.setFont(tree.getFont());
        label.setText(stringValue);
        label.setSelected(isSelected);
        label.setFocus(hasFocus);
        if (leaf) {
            label.setIcon(UIManager.getIcon("Tree.leafIcon"));
        } else if (expanded) {
            label.setIcon(UIManager.getIcon("Tree.openIcon"));
        } else {
            label.setIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        return this;
    }

    public Dimension getPreferredSize() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        return new Dimension(d_check.width + d_label.width,
                (d_check.height < d_label.height ? d_label.height
                        : d_check.height));
    }

    public void doLayout() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        int y_check = 0;
        int y_label = 0;
        if (d_check.height < d_label.height) {
            y_check = (d_label.height - d_check.height) / 2;
        } else {
            y_label = (d_check.height - d_label.height) / 2;
        }
        check.setLocation(0, y_check);
        check.setBounds(0, y_check, d_check.width, d_check.height);
        label.setLocation(d_check.width, y_label);
        label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }

    public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }

    public class TreeLabel extends JLabel {
        boolean isSelected;

        boolean hasFocus;

        public TreeLabel() {
        }

        public void setBackground(Color color) {
            if (color instanceof ColorUIResource)
                color = null;
            super.setBackground(color);
        }

        public void paint(Graphics g) {
            String str;
            if ((str = getText()) != null) {
                if (0 < str.length()) {
                    if (isSelected) {
                        g.setColor(UIManager
                                .getColor("Tree.selectionBackground"));
                    } else {
                        g.setColor(UIManager.getColor("Tree.textBackground"));
                    }
                    Dimension d = getPreferredSize();
                    int imageOffset = 0;
                    Icon currentI = getIcon();
                    if (currentI != null) {
                        imageOffset = currentI.getIconWidth()
                                + Math.max(0, getIconTextGap() - 1);
                    }
                    g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
                            d.height);
                    if (hasFocus) {
                        g.setColor(UIManager
                                .getColor("Tree.selectionBorderColor"));
                        g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                                d.height - 1);
                    }
                }
            }
            super.paint(g);
        }

        public Dimension getPreferredSize() {
            Dimension retDimension = super.getPreferredSize();
            if (retDimension != null) {
                retDimension = new Dimension(retDimension.width + 3,
                        retDimension.height);
            }
            return retDimension;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public void setFocus(boolean hasFocus) {
            this.hasFocus = hasFocus;
        }
    }
}

class CheckNode extends DefaultMutableTreeNode {

    public final static int SINGLE_SELECTION = 0;

    public final static int DIG_IN_SELECTION = 4;

    protected int selectionMode;

    protected boolean isSelected;

    public CheckNode() {
        this(null);
    }

    public CheckNode(Object userObject) {
        this(userObject, true, false);
    }

    public CheckNode(Object userObject, boolean allowsChildren,
                     boolean isSelected) {
        super(userObject, allowsChildren);
        this.isSelected = isSelected;
        setSelectionMode(DIG_IN_SELECTION);
    }

    public void setSelectionMode(int mode) {
        selectionMode = mode;
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;

        if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                CheckNode node = (CheckNode) e.nextElement();
                node.setSelected(isSelected);
            }
        }
    }

    public boolean isSelected() {
        return isSelected;
    }
}
