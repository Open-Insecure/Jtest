package com.br.dong.properties.encode;
/** 
 * @author  hexd
 * 创建时间：2014-6-4 上午11:12:32 
 * 类说明 
 */
public interface Encode {
	 public void encode(String fileInputName,String fileOutputName) throws Exception; 
	 public void decode(String fileInputName,String fileOutputName) throws Exception; 
}
