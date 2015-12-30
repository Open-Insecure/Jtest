package com.br.dong.cmd_test;

/**
 * 
 * <DL>
 * <DT><B>标题 </B></DT>
 * <p>
 * <DD>字符串处理办法</DD>
 * </DL>
 * <p>
 * <DL>
 * <DT><B>详细介绍</B></DT>
 * <p>
 * <DD></DD>
 * </DL>
 * <p>
 * 
 * @author 刘兆星 2011-5-31 上午10:42:36
 * @version Id: StringUtil.java 2011-5-31 上午10:42:36 刘兆星
 */
public class StringUtil {
	/**
	 * 
	 * <DL>
	 * <p>
	 * <DD>清除多余的空格</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param msg
	 * @return
	 */
	public static String clearBlank(String msg) {
		boolean isHave = true;
		msg = msg.trim();
		while (isHave) {
			isHave = false;
			if (msg.indexOf("  ") > -1) {
				isHave = true;
				msg = msg.replace("  ", " ");
			}
		}
		return msg;
	}
}
