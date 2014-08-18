package com.br.dong.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonLibTest1 {

	public static void main(String[] args) {
		 String json2 = "{\"address\":\"chian\",\"birthday\":{\"birthday\":\"2010-11-22\"},"+
		        "\"email\":\"email@123.com\",\"id\":22,\"name\":\"tom\"}";
		 String json="{\"status\":\"0\",\"subscribe\":\"XGSS\"},{\"status\":\"0\",\"subscribe\":\"XGZQ\"},{\"status\":\"0\",\"subscribe\":\"PGJK\"},{\"status\":\"0\",\"subscribe\":\"ZFGG\"},{\"status\":\"0\",\"subscribe\":\"FHYA\"},{\"status\":\"0\",\"subscribe\":\"HLHGDZ\"},{\"status\":\"0\",\"subscribe\":\"YJYG\"},{\"status\":\"1\",\"subscribe\":\"TTLDQ\"},{\"status\":\"0\",\"subscribe\":\"FBCJHB\"},{\"status\":\"0\",\"subscribe\":\"YZZZ\"}";
		 String json3=""; 
		 JSONArray jsonArray = null;
	      JSONObject jsonObject = null;
		    try {
		        System.out.println ("==============JSON Arry String >>> Java Array ==================");
		        json = "[" + json + "]";
		        //json数组
		        jsonArray = JSONArray.fromObject(json);
		        //对象数组
		        Object[] os = jsonArray.toArray();
		        System.out.println("length:"+os.length);
		        JSONObject jo=jsonArray.getJSONObject(0);
		        System.out.println("join"+JSONArray.fromObject(json).join(""));
		        System.out.println("os"+os[0].toString());
		        System.out.println("jo"+jo.toString());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
}
