package com.br.dong.iostream;

import java.util.Scanner;

/**
 * �����������
 * */
public class ScannerTest {

	public static void main(String[] args) {
		//�ӿ���̨���룬����һ��Scanner���������ڱ�׼�����
		Scanner in=new Scanner(System.in);
		System.out.println("��ȡ���������һ��");
		String name=in.nextLine();
		System.out.println("�������һ��"+name);
		System.out.println("��ȡ�����һ����");
		String one=in.next();
		System.out.println("�������һ����"+one);
	}
}
