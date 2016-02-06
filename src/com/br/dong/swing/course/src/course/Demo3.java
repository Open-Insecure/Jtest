package com.br.dong.swing.course.src.course;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * JTree优化示例
 * @author sf_dream
 * @date 2015年5月17日
 */
@SuppressWarnings("serial")
public class Demo3 extends JFrame {

	private JPanel content;
	private JTree tree;
	
	/** 调用reload方法更新jtree界面 */
	private DefaultTreeModel model;
	/** 根节点 */
	private DefaultMutableTreeNode root;
	
	private Border inBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	public Demo3() {
		initGUI();
		initListener();
	}
	
	private void initGUI() {
		setSize(300, 600);
		setTitle("course");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		content = new JPanel();
		content.setLayout(new BorderLayout());
		getContentPane().add(content);
		
		// 创建根节点，所有的节点都是挂在根节点下面的
		root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		
		// 加载数据
//		for (int i = 1; i <= 3; i++) {
//			DefaultMutableTreeNode cate = new DefaultMutableTreeNode("我的好友" + i);
//			for (int j = 1; j <= 3; j++) {
//				DefaultMutableTreeNode node = new DefaultMutableTreeNode("好友" + i + "-" + j);
//				cate.add(node);
//			}
//			root.add(cate);
//		}
		for (int i = 1; i <= 3; i++) {
			DemoNode cate = new DemoNode(produceImage("arrow_left.png"), "我的好友" + i);
			for (int j = 1; j <= 3; j++) {
				DemoNode node = new DemoNode(produceImage("avatar.png") ,"好友" + i + "-" + j, "人生若只如初见");
				cate.add(node);
			}
			root.add(cate);
		}
		
		tree = new JTree(model);
		// 设置根节点不可见
		tree.setRootVisible(false);
		// 设置点击1次展开节点
		tree.setToggleClickCount(1);
		// 设置UI
		tree.setUI(new DemoTreeUI());
		// 设置节点渲染器
		tree.setCellRenderer(new DemoRenderer());
		// 去掉节点前面的线条
		// 此功能也可以放到DemoTreeUI里面实现
//		tree.putClientProperty("JTree.lineStyle", "None");
		
		content.add(tree, BorderLayout.CENTER);
	}

	private void initListener() {
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TreePath path = tree.getSelectionPath();
				if (null != path) {
					Object object = path.getLastPathComponent();
					DemoNode node = (DemoNode) object;
					// 二级节点（分组）咱就不管了
					if (node.getLevel() == 2) {
						node.nodeContent.setBorder(inBorder);
						node.nodeContent.setBackground(Color.ORANGE);
						// 去掉选中之外其他所有节点的特效
						for (int i = 0; i < root.getChildCount(); i++) {
							DemoNode cate = (DemoNode) root.getChildAt(i);
							// 三级节点
							for (int j = 0; j < cate.getChildCount(); j++) {
								DemoNode buddy = (DemoNode) cate.getChildAt(j);
								if (node != buddy) {
									buddy.nodeContent.setBorder(null);
									buddy.nodeContent.setBackground(null);
								}
								model.reload(buddy);
							}
						}
						model.reload(node);
					}
				}
			}
		});
		tree.addMouseMotionListener(new MouseMotionAdapter() {
			// 没有使用moseEnter事件，是因为此方法里面没有可以获取当前节点的API
			// 大家若是知道有更好的方法，可以告诉我一下，感激不尽
			// 还有，大家看到如下方法，循环太多，如果节点过多的话，效率就不行了，但是事件只能加到jtree上，如果能加到我重绘的UI上就好了。。。
			@Override
			public void mouseMoved(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					Object object = path.getLastPathComponent();
					DemoNode node = (DemoNode) object;
					
					// 不管三七二十一，先把自己的特效加上再说
					// 这里因为是鼠标移动事件，触发太快了，所以要判断是否在当前节点上移动鼠标
					if (node.getLevel() == 1 && node.cateContent.getBorder() != inBorder) {
						node.cateContent.setBorder(inBorder);
					}
					if (node.getLevel() == 2 && node.nodeContent.getBorder() != inBorder) {
						node.nodeContent.setBorder(inBorder);
					}
					model.reload(node);
					
					// 去掉选中之外其他所有节点的特效
					for (int i = 0; i < root.getChildCount(); i++) {
						DemoNode cate = (DemoNode) root.getChildAt(i);
						if (node != cate && cate.cateContent.getBackground() != Color.ORANGE) {
							cate.cateContent.setBorder(null);
						}
						model.reload(cate);
						// 三级节点
						for (int j = 0; j < cate.getChildCount(); j++) {
							DemoNode buddy = (DemoNode) cate.getChildAt(j);
							if (node != buddy && buddy.nodeContent.getBackground() != Color.ORANGE) {
								buddy.nodeContent.setBorder(null);
							}
							model.reload(buddy);
						}
					}
				}
			}
		});
	}
	
	/**
	 * 获取图片
	 * @param name 图片名称
	 * @return
	 */
	private ImageIcon produceImage(String name) {
		ImageIcon backImage = new ImageIcon(getClass().getClassLoader().getResource(name));
		return backImage;
	}
	
	public static void main(String[] args) {
		Demo3 demo1 = new Demo3();
		demo1.setVisible(true);
		demo1.setLocationRelativeTo(null);
	}

}
