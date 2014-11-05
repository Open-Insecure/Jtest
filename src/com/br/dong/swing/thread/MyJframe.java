package com.br.dong.swing.thread;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
* @author  hexd
* 创建时间：2014-6-3 下午5:55:05
* 类说明
* 多线程日志打印
*/
public class MyJframe extends JFrame {

	private JPanel contentPane;
	private JTextField email;
	private JPasswordField password;
    //用来显示日志记录的文本域
    public static JTextArea area = new JTextArea(10, 50);
    //设置滚动面板用来处理日志
	JScrollPane jScrollPane = new JScrollPane(area);
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//
		  WriteLog log =WriteLog.getInstance();;
		new Thread(log).start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyJframe frame = new MyJframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MyJframe() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 600);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel label = new JLabel("百度一键签到工具");
		label.setFont(new Font("黑体", Font.PLAIN, 28));
		label.setBounds(78, 24, 240, 56);
		contentPane.add(label);

		JLabel label_1 = new JLabel("邮箱");
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		label_1.setBounds(78, 139, 54, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("密码");
		label_2.setFont(new Font("宋体", Font.PLAIN, 18));
		label_2.setBounds(78, 164, 54, 15);
		contentPane.add(label_2);

		JLabel lblauthorJonyChang = new JLabel("@author apdo");
		lblauthorJonyChang.setBounds(230, 65, 128, 15);
		contentPane.add(lblauthorJonyChang);

		email = new JTextField();
		email.setText("dong7253997");
		email.setBounds(123, 138, 224, 21);
		contentPane.add(email);
		email.setColumns(10);

		password = new JPasswordField();
		password.setText("95b004");
		password.setBounds(123, 164, 224, 21);
		contentPane.add(password);

		JButton button = new JButton("确定");
		button.setFont(new Font("宋体", Font.PLAIN, 18));
		button.setBounds(254, 195, 93, 23);
		contentPane.add(button);

//		JLabel label_3 = new JLabel("日志");
//		label_3.setFont(new Font("宋体", Font.PLAIN, 12));
//		label_3.setBounds(30, 265, 328, 15);
//		contentPane.add(label_3);
		jScrollPane.setBounds(30, 265, 500, 300);
		contentPane.add(jScrollPane);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					System.out.println("点击了");
			}
		});
	}
}
