package com.br.dong.cmd_test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * <DL>
 * <DT><B>标题 </B></DT>
 * <p>
 * <DD>查询Cpu信息</DD>
 * </DL>
 * <p>
 * <DL>
 * <DT><B>详细介绍</B></DT>
 * <p>
 * <DD></DD>
 * </DL>
 * <p>
 * 
 * @author 刘兆星 2011-5-30 上午11:20:54
 * @version Id: CpuExecCmd.java 2011-5-30 上午11:20:54 刘兆星
 */
public class CpuExecCmd {
	/**
	 * 日志实例
	 */
	private static final Log logger = LogFactory.getLog(CpuExecCmd.class);
	/*
	 * 是否已经有了
	 */
	private boolean isHave = false;
	/**
	 * 执行查询的结果集
	 */
	private String result = "";

	/**
	 * 
	 * <DL>
	 * <p>
	 * <DD>执行脚本命令</DD>
	 * </DL>
	 * <p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exceCmd() throws Exception {
		logger.info("执行命令");
		//commented by ldf 20121221 Process process = Runtime.getRuntime().exec("iostat");
		Process process = Runtime.getRuntime().exec("vmstat 1 1");
//		Process process = Runtime.getRuntime().exec("dir");

		BufferedReader br = new BufferedReader(new InputStreamReader(process
				.getInputStream()));
		String line = "";
		while ((line = br.readLine()) != null) {
			dealLine(line);
		}
		// 输出错误字节流
		BufferedReader errorBr = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
		String errorStr = "";
		while ((errorStr = errorBr.readLine()) != null) {
			logger.warn("执行CPU监控脚本异常：" + errorStr);
		}
		int exitVal = process.waitFor();
		// if (exitVal == 0) {
		// process.destroy();
		// }
		process.destroy();
		br.close();
		errorBr.close();
		logger.info("CPU执行完毕返回");
		return dealResult(result);
	}

	/**
	 * 
	 * <DL>
	 * <p>
	 * <DD>处理每一行</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param line
	 */
	private void dealLine(String line) {
		int index = line.indexOf("avg-cpu:");
		if (index > 0) {
			isHave = true;
			System.out.println(line);
			return;
		}
		if (isHave) {
			result = line;
			isHave = false;
		}
	}

	/**
	 * 
	 * <DL>
	 * <p>
	 * <DD>整理执行结果脚本</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param str
	 * @return
	 */
	private String dealResult(String str) {
		str = StringUtil.clearBlank(str);
		String[] array = str.split(" ");
		if (array.length != 6) {
			logger.info("查询结果是错误的,长度不是6。" + str);
			return "";
		}
		// 验证数据阀值 由于阀值文件是时时同步的 此处不严重 在监控服务端验证
		StringBuffer sb = new StringBuffer();
		sb.append(array[2] + " " + array[3] + " " + array[4] + " " + array[5]);
		logger.info("cpu信息："+sb.toString());
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		CpuExecCmd test=new CpuExecCmd();
		test.exceCmd();
	}
}
