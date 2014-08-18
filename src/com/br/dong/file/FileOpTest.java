package com.br.dong.file;

public class FileOpTest {
	public static void main(String []agrs){
		FileOperate f=new FileOperate();
//		f.newFile("he.txt", null);
		f.newFile("f://fhe.txt", "");
		f.appendMethodB("f://fhe.txt", "aa");
		f.appendMethodB("f://fhe.txt", "bb");
		f.appendMethodB("f://fhe.txt", "cc");
		f.appendMethodB("f://fhe.txt", "dd");
		f.appendMethodB("f://fhe.txt", "ee");
		f.appendMethodB("f://fhe.txt", "ff");
	}
}
