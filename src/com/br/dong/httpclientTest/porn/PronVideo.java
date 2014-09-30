package com.br.dong.httpclientTest.porn;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.thread.MyRunable;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-25
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 *
 * 线程池视频文件下载
 */
public class PronVideo {

    //视频列表url page页数需要拼装
    private static String url="http://91p.vido.ws/v.php?next=watch&page=";
    //视频文件请求url 后跟参数需要拼装
    //默认查找页数
    private static int defaultPage=1000;
    //多少条进行一次批量插入
    private static int batchNum=100;
    private static FileOperate fo=new FileOperate();
    //当前页面视频采集完成标志  pageGetFlag=20表示此页的20部视频全部下完
    public static int pageGetFlag=0;
    //当前要采集的第几页的页数
    public static int current=1;
    //第一次循环标志
    public static boolean first=true;
    //--下载程序使用的参数
    public  static String saveFile="F:\\vedios\\new\\";
    //private static String saveFile="c:\\vedios\\new20140830\\";
    //线程池
    static ExecutorService threadPool= Executors.newFixedThreadPool(20);
    public static void main(String[] args) {
        getPaging(false);
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

    /**
     * 在线拿去实时视频列表页面 数据库中的页面会过期
     * @param url
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws java.io.IOException
     * @throws CloneNotSupportedException
     */
    public static List<VedioBean> getPageVideosOnline(String url) throws ClientProtocolException, IOException, CloneNotSupportedException, KeyManagementException, NoSuchAlgorithmException {
        CrawlerUtil client=new CrawlerUtil();
        client.clientCreatNoUrl("http");
        List<VedioBean> list=new ArrayList<VedioBean>();
        //填充中文参数 post获得中文返回页面
        Document doc=client.getDocUTF8(client.post(url, client.produceEntity(getPostParmList())));
        Elements videobox=doc.select("div[class*=listchannel]");
        System.out.println(url+"'s videos total number:["+videobox.size()+"]");
        //拿去视频预览图片
        for(Element e:videobox){
            String title=e.select("div[class*=imagechannel]>a>img").attr("title");//标题
            String preImgSrc=e.select("div[class*=imagechannel]>a>img").attr("src");//获得预览图片链接
            String vedioUrl=e.select("div[class*=imagechannel]>a").attr("abs:href");//视频链接地址
            String infotime=e.text().substring(e.text().indexOf("时长:"),e.text().indexOf(" 添加时间"));//获得时长
            String updatetime= DateUtil.getStrOfDateMinute();
            System.out.println(title+preImgSrc+vedioUrl+infotime+updatetime);
            list.add(new VedioBean(title,preImgSrc,vedioUrl,infotime,updatetime,0));
        }
        //多线程采集处理视频
        for(VedioBean v:list){
//            System.out.println(url+"already downloaded["+pageGetFlag+"]");
             threadPool.execute(new SimplyDownLoadTask("name",v,new CrawlerUtil()));
        }
        return list;
        //System.out.println(videobox.toString());
    }

    /**
     * 定制采集从多少页到多少页的数据
     * @param startPage
     * @param endPage
     * @param path
     */
    public static void getPaging(int startPage,int endPage,String path){
        saveFile=path;
        collect(startPage,endPage);

    }
    /**
     * 在线拿去91pron的分页最大页数，从current页数开始进行视频抓取的入口方法
     * @param wantMaxPage    true 则拿去最大页数    false 则拿去默认的页数
     */
    public static void getPaging(Boolean wantMaxPage){
        CrawlerUtil client=new CrawlerUtil();
        try {
            client.clientCreatNoUrl("http");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int maxpage=defaultPage;
        //第一次采集第一页的
        HttpResponse response;
        if(wantMaxPage){
            //查找最大页数
            try {
                response = client.post(url+"1", client.produceEntity(getPostParmList()));
                Document doc= client.getDocUTF8(response);
//	 		System.out.println(doc.toString());
                //拿到视频分页
                Elements maxpageElement=doc.select("div[class*=pagingnav]>a:eq(6)");
                maxpage=Integer.parseInt(maxpageElement.text());
            } catch (CloneNotSupportedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }  catch (SocketException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            catch(NumberFormatException e){
                System.out.println("fail to get max page,auto set max page="+defaultPage);
            }
        }

        System.out.println("get max page:"+maxpage);
        collect(current,maxpage);
    }

    /**
     * 采集多少页到多少页的视频
     * @param startPage   起始页数
     * @param endPage     结束页数
     * 注意  current
     */
    public static void collect(int startPage,int endPage){
        //当前页开始循环到最大页数
        PronUI.jta.append("start page:"+startPage+"end page:"+endPage+"total videos:"+((endPage+1)-startPage)*20+"\n");
        while(startPage<endPage+1){
            //设置采集标志位未采集
            try {
                if(first){
                    System.out.println("start to collection .."+url+startPage);
                    PronUI.jta.append("start to collection .."+url+startPage+"\n");
                    getPageVideosOnline(url+startPage);
                    startPage++;
                    first=false;
                }
                if(pageGetFlag%20==0&&pageGetFlag>=20){
                    startPage++;
                    System.out.println("start to collection .."+url+startPage);
                    PronUI.jta.append("start to collection .."+url+startPage+"\n");
                    getPageVideosOnline(url+startPage);
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (KeyManagementException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        //退出系统

    }



}
