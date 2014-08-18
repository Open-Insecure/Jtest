package com.br.dong.json.listTojson;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/** 
 * @author  hexd
 * 创建时间：2014-6-5 下午1:25:12 
 * 类说明 
 */
public class JsonLibTest {  
    
    public List<TreeNode> getEntity(){  
        List<TreeNode> result = new ArrayList<TreeNode>();  
        TreeNode tn = new TreeNode();  
        tn.setId(1);  
        tn.setPid(0);  
        tn.setName("目录1");  
        result.add(tn);  
        tn = new TreeNode();  
        tn.setId(11);  
        tn.setPid(1);  
        tn.setName("目录11");  
        result.add(tn);  
        tn = new TreeNode();  
        tn.setId(111);  
        tn.setPid(11);  
        tn.setName("目录111");  
        result.add(tn);  
        tn = new TreeNode();  
        tn.setId(12);  
        tn.setPid(1);  
        tn.setName("目录12");  
        result.add(tn);  
        tn = new TreeNode();  
        tn.setId(2);  
        tn.setPid(0);  
        tn.setName("目录2");  
        result.add(tn);  
        tn = new TreeNode();  
        tn.setId(1);  
        tn.setPid(0);  
        tn.setName("目录1");  
        result.add(tn);  
        return result;  
    }  
      
    public String testEntityList2Json(){  
        String result = "";  
        List<TreeNode> entity = this.getEntity();  
        JSONArray jsonArray = JSONArray.fromObject(entity);  
        result = jsonArray.toString();  
        System.out.println("************************ Json:\n"+result);  
        return result;  
    }  
      
    public void testJson2EntityList(){  
        List<TreeNode> result = null;       
        String json = this.testEntityList2Json();  
        JSONArray jsonobj = JSONArray.fromObject(json);  
        result = (List<TreeNode>) JSONArray.toList(jsonobj,TreeNode.class);   
        for(TreeNode tn:result){  
            System.out.println(tn.getName());  
        }  
    }  
      
    public static void main(String args[]){  
        JsonLibTest test = new JsonLibTest();  
        test.testJson2EntityList();  
    }  
  
}  
