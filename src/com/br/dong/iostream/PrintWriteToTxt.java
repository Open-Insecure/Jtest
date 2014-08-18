package com.br.dong.iostream;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
/**�ı����
 * �ӿ 
 * */
public class PrintWriteToTxt {
	public static void main(String[] args) {
		try{
			String name="hehehe";
			//����������ļ� �����fuck.txt�� 
			PrintWriter out=new PrintWriter(new FileOutputStream("D:/fuck.txt"),true/**�Զ�ˢ��*/);
			//���������
			out.println(name);
			PrintWriter out2=new PrintWriter(new FileOutputStream("D:/fuck2.txt"),true/**�Զ�ˢ��*/);
			//��ȡ����̨������
			Scanner s=new Scanner(System.in);
			//��ȡ����̨�����һ��
			String aLine = s.nextLine();
			out2.println(aLine);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
