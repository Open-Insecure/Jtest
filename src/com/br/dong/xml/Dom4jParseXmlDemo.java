package com.br.dong.xml;
/** 
 * 使用dom4j解析xml文档 
 * @author Administrator 
 * 
 */  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStream;  
import java.util.Iterator;  
import java.util.List;  
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;
public class Dom4jParseXmlDemo {
	/**
	 * 解析xml
	 * */
	public void parseXml01(){  
        try{  
            //将src下面的xml转换为输入流  
//            InputStream inputStream = new FileInputStream(new File("D:/project/dynamicWeb/src/resource/module01.xml"));   
            InputStream inputStream = this.getClass().getResourceAsStream("module01.xml");//也可以根据类的编译文件相对路径去找xml 
            System.out.println("路径"+this.getClass().getResource("module01.xml").getFile());//此方法获得文件绝对路径
            //创建SAXReader读取器，专门用于读取xml  
            SAXReader saxReader = new SAXReader();  
            //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取   
            Document document = saxReader.read(inputStream);    
//            Document document = saxReader.read(new File("D:/project/dynamicWeb/src/resource/module01.xml"));//必须指定文件的绝对路径  
            //另外还可以使用DocumentHelper提供的xml转换器也是可以的。  
            //Document document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><modules id=\"123\"><module> 这个是module标签的文本信息</module></modules>");  
              
            //获取根节点对象  
            Element rootElement = document.getRootElement();    
            System.out.println("根节点名称：" + rootElement.getName());//获取节点的名称  
            System.out.println("根节点有多少属性：" + rootElement.attributeCount());//获取节点属性数目  
            System.out.println("根节点id属性的值：" + rootElement.attributeValue("id"));//获取节点的属性id的值  
            System.out.println("根节点内文本：" + rootElement.getText());//如果元素有子节点则返回空字符串，否则返回节点内的文本  
            //rootElement.getText() 之所以会换行是因为 标签与标签之间使用了tab键和换行符布局，这个也算是文本所以显示出来换行的效果。  
            System.out.println("根节点内文本(1)：" + rootElement.getTextTrim());//去掉的是标签与标签之间的tab键和换行符等等，不是内容前后的空格  
            System.out.println("根节点子节点文本内容：" + rootElement.getStringValue()); //返回当前节点递归所有子节点的文本信息。  
              
            //获取子节点  
            Element element = rootElement.element("module");  
            if(element != null){  
                System.out.println("子节点的文本：" + element.getText());//因为子节点和根节点都是Element对象所以它们的操作方式都是相同的  
            }  
            //但是有些情况xml比较复杂，规范不统一，某个节点不存在直接java.lang.NullPointerException，所以获取到element对象之后要先判断一下是否为空  
              
            rootElement.setName("root");//支持修改节点名称  
            System.out.println("根节点修改之后的名称：" + rootElement.getName());  
            rootElement.setText("text"); //同样修改标签内的文本也一样  
            System.out.println("根节点修改之后的文本：" + rootElement.getText());  
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }  
    /**
     *  使用Iterator 迭代器循环document文档对象
     * */ 
	public void parseXml02(){  
	    try{  
	        //将src下面的xml转换为输入流  
	        InputStream inputStream = this.getClass().getResourceAsStream("module02.xml");  
	        //创建SAXReader读取器，专门用于读取xml  
	           SAXReader saxReader = new SAXReader();  
	           //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取  
	           Document document = saxReader.read(inputStream);  
	             
	           Element rootElement = document.getRootElement();  
	        Iterator<Element> modulesIterator = rootElement.elements("module").iterator();  
	        //rootElement.element("name");获取某一个子元素  
	        //rootElement.elements("name");获取根节点下子元素moudule节点的集合，返回List集合类型  
	        //rootElement.elements("module").iterator();把返回的list集合里面每一个元素迭代子节点，全部返回到一个Iterator集合中  
	        while(modulesIterator.hasNext()){  
	            Element moduleElement = modulesIterator.next();  
	            Element nameElement = moduleElement.element("name");  
	            System.out.println(nameElement.getName() + ":" + nameElement.getText());  
	            Element valueElement = moduleElement.element("value");  
	            System.out.println(valueElement.getName() + ":" + valueElement.getText());  
	            Element descriptElement = moduleElement.element("descript");  
	            System.out.println(descriptElement.getName() + ":" + descriptElement.getText());  
	        }     
	    } catch (Exception e) {    
	           e.printStackTrace();    
	       }    
	}     
	/**
	 * xml规则比较复杂，比如接下来要测试的module03.xml
	 * */
	public void parseXml03(){  
	    try{  
	        //将src下面的xml转换为输入流  
	        InputStream inputStream = this.getClass().getResourceAsStream("module03.xml");  
	        //创建SAXReader读取器，专门用于读取xml  
	           SAXReader saxReader = new SAXReader();  
	           //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取  
	           Document document = saxReader.read(inputStream);  
	             
	           Element rootElement = document.getRootElement();  
	           if(rootElement.elements("module") != null ){  
	            //因为第一个module标签只有内容没有子节点，直接.iterator()就java.lang.NullPointerException了, 所以需要分开实现  
	            List<Element> elementList = rootElement.elements("module");  
	            for (Element element : elementList) {  
	                if(!element.getTextTrim().equals("")){  
	                    System.out.println("【1】" + element.getTextTrim());  
	                }else{  
	                    Element nameElement = element.element("name");  
	                    System.out.println("   【2】" + nameElement.getName() + ":" + nameElement.getText());  
	                    Element valueElement = element.element("value");  
	                    System.out.println("   【2】" + valueElement.getName() + ":" + valueElement.getText());  
	                    Element descriptElement = element.element("descript");  
	                    System.out.println("   【2】" + descriptElement.getName() + ":" + descriptElement.getText());  
	                      
	                    List<Element> subElementList = element.elements("module");  
	                    for (Element subElement : subElementList) {  
	                        if(!subElement.getTextTrim().equals("")){  
	                            System.out.println("      【3】" + subElement.getTextTrim());  
	                        }else{  
	                            Element subnameElement = subElement.element("name");  
	                            System.out.println("      【3】" + subnameElement.getName() + ":" + subnameElement.getText());  
	                            Element subvalueElement = subElement.element("value");  
	                            System.out.println("      【3】" + subvalueElement.getName() + ":" + subvalueElement.getText());  
	                            Element subdescriptElement = subElement.element("descript");  
	                            System.out.println("      【3】" + subdescriptElement.getName() + ":" + subdescriptElement.getText());  
	                        }  
	                    }  
	                }  
	            }  
	           }  
	    } catch (Exception e) {    
	           e.printStackTrace();    
	       }    
	}  
	//---主方法
    public static void main(String[] args) {  
        Dom4jParseXmlDemo demo = new Dom4jParseXmlDemo();  
        demo.parseXml01();  
        demo.parseXml02();
        demo.parseXml03();
    }  
}  
