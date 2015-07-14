package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-08
 * Time: 22:07
 * 针对youb444.com的采集线程并进行入库
 */
public class YoubbTaskThread implements Callable<String> {

    private CrawlerUtil crawlerUtil=null;//采集通用工具类
    private String name;//线程名字
    private String configUrl;//获得视频信息的url
    private List<YoubbSimplyBean> vkeys=null;//线程要采集的视频id集合
    private static Logger logger = Logger.getLogger(YoubbTaskThread.class);//日志
    private String msg="";//该线程执行结果的信息
    /**
     * 线程构造方法
     * @param name
     * @param crawlerUtil
     * @param configUrl
     * @param vkeys
     */
    public YoubbTaskThread(String name,CrawlerUtil crawlerUtil,String configUrl,List<YoubbSimplyBean> vkeys) {
        this.name=name;
        this.crawlerUtil=crawlerUtil;
        this.configUrl=configUrl;
        this.vkeys = vkeys;
    }

    /**
     * 线程run方法
     * @return 返回值
     * @throws Exception
     */
    @Override
    public String call()  {
        try{
            for(YoubbSimplyBean simplyBean:vkeys){//循环vkeys
                getConfigByVkey(simplyBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return msg;
        }

    }

    /**
     * 根据vkey查询该视频的flv路径信息
     * @param simplyBean
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void getConfigByVkey(YoubbSimplyBean simplyBean)   {
            try{
                HttpResponse response= crawlerUtil.noProxyGetUrl(configUrl+simplyBean.getVkey());
                if (response==null){
                    msg=msg+"[vkey:"+simplyBean.getVkey()+" fall]";//获得当前veky的文件信息请求错误
                }else{
                    parseXml(simplyBean,response);
                }
            }catch (Exception e){
                msg=msg+"[vkey:"+simplyBean.getVkey()+" fall]";//获得当前veky的文件信息请求错误
            }

    }

    /**
     *解析服务端xml
     * @param simplyBean
     * @param response
     */
    public void parseXml(YoubbSimplyBean simplyBean,HttpResponse response) throws DocumentException {
        org.jsoup.nodes.Document document=crawlerUtil.getDocUTF8(response);
        if(document==null) return;//
        org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
        org.dom4j.Document doc;
//        logger.info(document.body());
        doc =  org.dom4j.DocumentHelper.parseText(document.body().toString().replace("<body>","").replace("</body>","").trim());
        org.dom4j.Element root = doc.getRootElement();
        org.dom4j.Element file = root.element("file");
//        org.dom4j.Element image = root.element("image");
        System.out.println("vkey:"+simplyBean.getVkey()+"file:"+file.getTextTrim());//+"image:"+image.getTextTrim()
    }

    /**
     * 返回远程下载文件的文件信息
     * 创建header进行头访问
     * http://vip.youb77.com:81/media/you22/flv/9263.flv
     * @param targetUrl
     * @return 返回两位小数的String类型
     */
    private  String readDownloadFileInfo(String targetUrl) throws IOException, CloneNotSupportedException {
        HttpHead httpHead = new HttpHead(targetUrl);//创一个访问头
        HttpResponse response = crawlerUtil.executeHead(httpHead);//返回头访问信息
        int statusCode = response==null?-1:response.getStatusLine().getStatusCode(); // 获取HTTP状态码
        if (statusCode != 200) {//响应码不正常的时候
            logger.info("资源不存在：" + targetUrl);
            return "";
        }
        // Content-Length
        Header[] headers = response.getHeaders("Content-Length");
        double contentLength=0;
        if (headers.length > 0)
        {	//获得要下载的文件的大小
            contentLength = Long.valueOf(headers[0].getValue());
        }
        httpHead.abort();//释放
        return String.format("%.2f",contentLength/(1024 * 1024));
    }
    /**
     * 开始下载
     * @param targeturl
     */
    public void download(String targeturl,String fileName){

    }




}
