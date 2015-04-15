package com.br.dong.img;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-19
 * Time: 上午1:27
 * To change this template use File | Settings | File Templates.
 */
public class ImageIdentify {
    public String getFilePath(String name){
        return this.getClass().getResource(name).getFile() ;
    }
    public static  void print1(String name) throws IOException {
        ImageIdentify imageIdentifyi=new ImageIdentify();
        BufferedImage bi = (BufferedImage)ImageIO.read(new File(imageIdentifyi.getFilePath(name)));

        //获取图像的宽度和高度
        int width = bi.getWidth();
        int height = bi.getHeight();

        //扫描图片
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){//行扫描
                int dip = bi.getRGB(j, i);
//=============volcano add 20120414========start=========//
                int p=dip;
                int red = 0xff & (p >> 16);
                int green = 0xff & (p >> 8);
                int blue = 0xff & p;
                // if(i == 0)
                // System.out.println("i:" + i + " red:" + red + " green:"
                // + green + " blue:" + blue);

                if (red < 180 && green < 180 && blue < 180) {
                    // System.out.println(i+".....get num");
                    System.out.print("?");
                }
                else    System.out.print("0");//用0比用空格好，用以看清有多少位像素
//=============volcano add 20120414========end =========//

            }
            System.out.println();//换行
        }
    }
    public static void print2(String name) throws IOException {
        ImageIdentify imageIdentifyi=new ImageIdentify();
        BufferedImage bi = (BufferedImage)ImageIO.read(new File(imageIdentifyi.getFilePath(name)));

        //获取图像的宽度和高度
        int width = bi.getWidth();
        int height = bi.getHeight();

        //扫描图片
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){//行扫描
                int dip = bi.getRGB(j, i);
                if(dip == -1) System.out.print(" ");
                else          System.out.print("♦");
            }
            System.out.println();//换行
        }

    }

    public static void main(String[] args) throws IOException {

        print1("e.png");
        print2("e.png");
        print1("c.png");
        print2("c.png");
    }
}
