package com.br.dong.json.net.sf.json.test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-6
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 * 转换json数组为list
 */
import java.util.List;
import net.sf.json.JSONArray;

public class ToJSON {
    public static List test(String s,Class clazz){
        JSONArray jarr=JSONArray.fromObject(s);
        return (List)jarr.toCollection(jarr,clazz);
    }

    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        String s="[{\"id\":\"329\",\"txt\":\"IT\",\"items\":[{\"id\":\"337\",\"txt\":\"机构\"},{\"id\":\"338\",\"txt\":\"机构2\",\"items\":[{id:\"887\",txt:\"内部\"},{id:\"888\",txt:\"内部2\"}]}]},{\"id\":\"345\",\"txt\":\"IT2\"}]";
        List<AClass> list=test(s,AClass.class);
        for(AClass ac:list){
            System.out.println(ac.getId());
        }
    }
}
