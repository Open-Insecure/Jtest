//package com.br.dong.json;
//
//
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
///**
// * JSON与实体对象之间的互相转化
// * @author hoodoo
// * @data 2012年12月22日
// *
// */
//public class GsonTest2{
//	public static void main(String[] args) {
//		String s="{'gradeId':1,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'}]}";
//		Gson g=new Gson();
//		Beans b=new Beans();
//		b.setGradeId(1);
//		b.setSubjects(new Subject[]{new Subject(1,"语文"),new Subject(2,"数学")});
//		//将实体对象 转化成json
//		System.out.println(g.toJson(b));
//		//从json 解析成单一对象实体
//		Beans bs=g.fromJson(s, Beans.class);
//		for(Subject sb:bs.getSubjects()){
//			System.out.println(sb.getName());
//		}
//
//		String s1="[{'gradeId':1,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'},{'id':3,'name':'英语'},{'id':4,'name':'物理'},{'id':5,'name':'化学'}]},{'gradeId':2,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'},{'id':3,'name':'英语'},{'id':4,'name':'物理'},{'id':5,'name':'化学'}]},{'gradeId':3,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'},{'id':3,'name':'英语'},{'id':4,'name':'物理'},{'id':5,'name':'化学'}]},{'gradeId':4,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'},{'id':3,'name':'英语'},{'id':4,'name':'物理'},{'id':5,'name':'化学'}]},{'gradeId':5,'subjects':[{'id':1,'name':'语文'},{'id':2,'name':'数学'},{'id':3,'name':'英语'},{'id':4,'name':'物理'},{'id':5,'name':'化学'}]}]";
//		//从json 解析成对象列表
//		List<Beans> bss=g.fromJson(s1, new TypeToken<List<Beans>>(){}.getType());
//		for(Beans b1:bss){
//			System.out.println(b1.getGradeId()+"年级");
//			for(Subject sb:b1.getSubjects()){
//				System.out.println(sb.getId()+":"+sb.getName());
//			}
//		}
//	}
//}
