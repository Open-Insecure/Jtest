package com.br.dong.sort;

public class SortTest {
	//冒泡排序
	public static void main(String []arg){
		int m[]={1,3,5,7,9};
		for(int i=0;i<m.length-1;i++){
			for(int j=0;j<m.length-i-1;j++){
				if(m[j]<m[j+1]){
					int temp=m[j];
					m[j]=m[j+1];
					m[j+1]=temp;
				}
			}
			System.out.println("��"+(i+1)+"������");
			for(int a=0;a<m.length;a++){
				System.out.print(m[a]+"\t");
			}
			System.out.println();
		}
		
	}
}
