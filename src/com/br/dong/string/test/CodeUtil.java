package com.br.dong.string.test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** 
 * @author  hexd
 * 生成邀请码
 */
public class CodeUtil {
	private static String codeChars = "0123456789abcdefghkmnpqrstuvwxyzABCDEFGHKLMNPQRSTUVWXYZ";
	private static String head = "insert into accode (code,type) values('";
	private static String bottom = ",'1');";

	public static void main(String[] args) {
		System.out.println(randomNumber());
		String c=randomCity();
		System.out.println(c);
		System.out.println(randomCityde(c));

	}

	public static String randomString() {
		Random random = new Random();
		int charsLength = codeChars.length();
		Set<String> set = new HashSet<String>();//set 保证不会重复插入
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			StringBuilder validationCode = new StringBuilder();
			for (int j = 0; j < 6; j++) {
				char codeChar = codeChars.charAt(random.nextInt(charsLength));
				validationCode.append(codeChar);
			}
			set.add(validationCode.toString());
		}
		for (String str : set) {
			//生成插入邀请码的sql
			System.out.println(head + str + "'" + bottom);
		}
		return "";
	}

	/**
	 * 返回随机的qq
	 *
	 * @return
	 */
	public static String randomNumber() {
		String number = "123456789";
		Random random = new Random();
		int charsLength = number.length();
		StringBuilder qq = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			char qqChar = number.charAt(random.nextInt(charsLength));
			qq.append(qqChar);
		}
		return qq.toString();
	}

	/**
	 * 返回城市
	 *
	 * @return
	 */
	public static String randomCity() {
		String[] citys = {"北京", "上海", "广东"};
		Random random = new Random();
		int lenght = citys.length;
		return citys[random.nextInt(lenght)];
	}

	public static String randomCityde(String province) {
		Random random = new Random();
		String[] beijing = {"东城", "西城", "崇文", "宣武", "朝阳", "丰台", "石景山", "海淀", "门头沟", "房山", "通州", "顺义", "昌平", "大兴 ", "怀柔", "平谷", "密云县", "延庆县"};
		String[] shanghai = {"广州市", "韶关市", "深圳市", "珠海市", "汕头市", "佛山市", "江门市", "湛江市", "茂名市", "肇庆市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市"};
		String[] guangdong = {"东城", "西城", "崇文", "宣武", "朝阳", "丰台", "石景山", "海淀", "门头沟", "房山", "通州", "顺义", "昌平", "大兴 ", "怀柔", "平谷", "密云县", "延庆县"};
		if (province.equals("北京")) {
			return beijing[random.nextInt(beijing.length)];
		} else if (province.equals("上海")) {
			return shanghai[random.nextInt(shanghai.length)];
		} else {
			return guangdong[random.nextInt(guangdong.length)];
		}
	}
}