package com.br.dong.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-08
 * Time: 22:30
 * 解析xml字符串
 */
public class Dom4jParseXmlStringDemo {
   private static  String xml="<config>\n" +
            "<logo>http://youb444.com/media/player/logo/logosmall.png</logo>\n" +
            "<file>http://vip.youbvip.com:81/media/you22/flv/9549.flv</file>\n" +
            "<image>\n" +
            "http://youb444.com/media/videos/tmb/9549/default.jpg\n" +
            "</image>\n" +
            "<streamer>lighttpd</streamer>\n" +
            "<type>lighttpd</type>\n" +
            "<autostart>false</autostart>\n" +
            "<stretching>uniform</stretching>\n" +
            "<bufferlength>3</bufferlength>\n" +
            "<backcolor>0x000000</backcolor>\n" +
            "<frontcolor>0xcccccc</frontcolor>\n" +
            "<lightcolor>0xff0090</lightcolor>\n" +
            "<skin>http://youb444.com/jwplayer/jw4player_skin.swf</skin>\n" +
            "<abouttext>YOUB88.COM - Free Porn Everyday</abouttext>\n" +
            "</config>";

    public static void main(String[] args) {
        try {
            SAXReader reader = new SAXReader();
            Document doc;
            doc = DocumentHelper.parseText(xml);

            //Document doc = reader.read(ffile); //读取一个xml的文件
            Element root = doc.getRootElement();
            Element eName = root.element("file");
            System.out.println("节点内容*--"+eName.getTextTrim());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
