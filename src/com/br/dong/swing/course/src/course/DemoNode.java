package com.br.dong.swing.course.src.course;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 自定义Node <br/>
 * 继承节点类DefaultMutableTreeNode <br/>
 * 在这里自定义我们自己的UI，以便cellRenderer中调用   <br/>
 * 大家写的时候最好将userNode和cateNode分开来写，这样会清晰一些
 * @author sf_dream
 * @date 2015年5月24日
 */
@SuppressWarnings("serial")
public class DemoNode extends DefaultMutableTreeNode {

	/** 图片 */
	private Icon icon;
	/** 文字 */
	private String name;
	/** 签名 */
	private String sign;
	
	public JPanel cateContent;
	public JPanel nodeContent;
	
	public JLabel iconLabel;
	public JLabel nameLabel;
	
	public JLabel signLabel;
	
	/**
	 * 初始化分组节点
	 * @param name 名称
	 */
	public DemoNode(Icon icon, String name) {
		this.icon = icon;
		this.name = name;
		// 初始化UI
		initCateGUI();
	}

	/**
	 * 初始化好友节点
	 * @param icon 头像
	 * @param nick 昵称
	 * @param sign 签名
	 */
	public DemoNode(Icon icon, String nick, String sign) {
		this.icon = icon;
		this.name = nick;
		this.sign = sign;
		// 初始化UI
		initNodeGUI();
	}
	
	/**
	 * 自定义分组UI
	 */
	private void initCateGUI() {
		cateContent = new JPanel();
		cateContent.setLayout(null);
//		cateContent.setOpaque(false);
		// 这里大家注意，当我们写好UI之后可能会发现他的颜色不太对，
		// 这时候千万不要用上面那句，不然当我们想再次改变其颜色的时候，就生效不了
		// 红绿蓝分别为255的这个颜色趋近于透明，我们可以用它来代替setOpaque
//		cateContent.setBackground(new Color(255,255,255));
		// 突然发现置成null也可以
		cateContent.setBackground(null);
		cateContent.setPreferredSize(new Dimension(300, 25));
//		cateContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		iconLabel = new JLabel(icon);
		iconLabel.setBounds(6, 5, 20, 16);
		cateContent.add(iconLabel);
		
		nameLabel = new JLabel(name);
		nameLabel.setBounds(23, 0, 132, 28);
		cateContent.add(nameLabel);
	}
	
	/**
	 * 自定义好友UI
	 */
	private void initNodeGUI() {
		nodeContent = new JPanel();
		nodeContent.setLayout(null);
//		nodeContent.setOpaque(false);
		nodeContent.setBackground(null);
		nodeContent.setPreferredSize(new Dimension(300, 50));
//		nodeContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		iconLabel = new JLabel(icon);
		iconLabel.setBounds(8, 4, 39, 42);
		nodeContent.add(iconLabel);
		
		nameLabel = new JLabel(name);
		nameLabel.setBounds(59, 5, 132, 19);
		nodeContent.add(nameLabel);
		
		signLabel = new JLabel(sign);
		signLabel.setBounds(59, 28, 132, 17);
		nodeContent.add(signLabel);
	}
	
	/**
	 * 将自定义UI返回给渲染器	<br/>
	 * 供渲染器调用，返回的必须是一个Component
	 * @return
	 */
	public Component getCateView() {
		return cateContent;
	}
	
	/**
	 * 将自定义UI返回给渲染器	<br/>
	 * 供渲染器调用，返回的必须是一个Component
	 * @return
	 */
	public Component getNodeView() {
		return nodeContent;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
