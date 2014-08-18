package com.br.dong.string.test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** 
 * @author  hexd
 * ����ʱ�䣺2014-5-29 ����2:33:42 
 * ��˵�� 
 * �������6λ������
 */
public class CodeUtil {
	 //������
	  private static String codeChars = "0123456789abcdefghkmnpqrstuvwxyzABCDEFGHKLMNPQRSTUVWXYZ"; 
	  //����
	  private static String head="insert into accode (code,type) values('";
	  private static String bottom=",'1');";
	  public static void main(String[] args) {
	      Random random = new Random(); 
	      int charsLength=codeChars.length();
	      //ʹ��hashSet����֤���ǲ�ͬ�������
	      Set<String> set = new HashSet<String>();
	      Random rand = new Random();
	      for(int i=0;i<1000;i++){
	  		StringBuilder validationCode = new StringBuilder();
			// ��λ
			for (int j = 0; j < 6; j++) {
				// ����õ�ǰ��֤����ַ�
				char codeChar = codeChars.charAt(random.nextInt(charsLength));
				validationCode.append(codeChar);
			}
			set.add(validationCode.toString());
	      }
	      for (String str : set) {  
	          System.out.println(head+str+"'"+bottom);  
	    }  

	}
}
