package com.br.dong.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JArrayTest {

	public static void main(String[] args) {
		String jsonStr="{\"data\":[{\"author\": \"孙志昕\",\"baseBrief\": \"\", \"docAttachments\": [{" +
				"\"contentSource\": \"document_attachment\"," +
				"\"desc\": \"11.jpg\"," +
				" \"id\": 3000836,"+
				" \"name\": \"11.jpg\","+
				"\"parentId\": -1,"+
				"\"title\": \"11.jpg\"}],"+
				"\"docId\": 3000649,"+
				"\"picture\": \"\","+
				"\"subTitle\": \"易纲:预计金砖国家应急储备安排不久将启动\","+
				"\"title\": \"基金学院-基金知识介绍之三\","+
				"\"uploadDateStr\": \"2013-08-28\" },"+
				"{\"author\": \"孙志昕\","+
				"\"baseBrief\": \"\","+
				"\"docAttachments\": [],"+
				" \"docId\": 3000647,"+
				"\"picture\": \"\","+
				"\"subTitle\": \"人社部:社保基金投资营运将采用三步走策略\","+
				"\"title\": \"基金学院-基金知识介绍之一\","+
				" \"uploadDateStr\": \"2013-08-28\"}]}"
				;
		System.out.println(jsonStr);
		//json字符串转化为json对象
		JSONObject obj = JSONObject.fromObject(jsonStr);
		//如果是json的数组数据 则转化为jsonarray
		JSONArray data= obj.getJSONArray("data");
		int length  = data.size();
		 for(int i = 0; i < length; i++){//遍历JSONArray
			JSONObject oj = data.getJSONObject(i);
			//获得json的字符串值
			System.out.println(oj.getString("title"));
            //图片信息获取
            JSONArray array=oj.getJSONArray("docAttachments");
            if(array!=null&&array.size()>0){
            JSONObject jsob=array.getJSONObject(0);
            System.out.println(jsob.getString("id"));
            }else{
            	System.out.println("aa");
            }
		 }

	}
}
