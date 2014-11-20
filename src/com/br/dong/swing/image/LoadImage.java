package com.br.dong.swing.image;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-19
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 * 测试swing image组件加载远程图片
 */
public class LoadImage {
    public static void main(String[] args){
        JFrame frame = new JFrame("Test Image Panel");
        JLabel lbl = new JLabel();
        BufferedImage image = null;
        try {
            URL imageURL = new URL("http://ptlogin2.qq.com/getimage");
            InputStream is = imageURL.openConnection().getInputStream();
            System.out.println();
            image = ImageIO.read(is);
            System.out.println("image is:"+image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Container c= frame.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(lbl);
        lbl.setIcon(new ImageIcon(image));
        frame.setBounds(200,100,300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
