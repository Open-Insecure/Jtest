package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-5-24
 * Time: 15:32
 * �����������1024gc�ɼ����������ӽ��з���
 *
 * mm�ĵ�ַ�� 21mybbs.me
 * ��������  184.164.72.160
 * �°��� http://107.150.17.66
 */
public class UploadTask_2015_05_24 extends Thread{
    //���뷽ʽ
    private static String GBK="GBK";
    private static String UTF8="UTF-8";
    private static String path="E:\\logs\\";
//    private static String path="C:\\logs\\";
    //��־Ŀ¼
    private static String logPath=path+ DateUtil.getCurrentDay()+"log.txt";
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    //�ļ�����������
    private static FileOperate fileOperate=new FileOperate();
    //
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    //���������Ƿ���������ı�־
    private String withFile;
    //�û���
    private String username;
    //����
    private String password;
    //��¼��ҳurl
    private  String index="";
    //��¼posturl��ȡformhash
    private  String loginPostUrl="";
    //��ǰ���������ĵ�ַ��ȡformhash
    private  String postPage="";
    //�ϴ�posturl
    private  String uploadUrl="";
    //��������
    private String type="";
    //fid
    private String fid="";
    //��ǰ�������ڸ�Ŀ¼
    private  String folderpath="";

    public static void main(String[] args) throws UnsupportedEncodingException {
        //�°��� http://107.150.17.66/
//        UploadTask_2015_05_24 task=new UploadTask_2015_05_24("name","E:\\uploads\\1\\","yes","liang93370894","asd123123","http://107.150.17.66/logging.php?action=login","http://107.150.17.66/logging.php?action=login&","http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1","http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes","");
//        task.start();

        //mm��Ԣ
//        UploadTask_2015_05_24 task=new UploadTask_2015_05_24("name","E:\\uploads\\1\\","yes","he7253997","95b004","http://21mybbs.me/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login","http://21mybbs.me/member.php?mod=logging&action=login&loginsubmit=yes&handlekey=login&loginhash=LtnzP&inajax=1","http://21mybbs.me/post.php?action=newthread&fid=25&extra=page%3D1","http://21mybbs.me/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes","mmhouse");
//        task.start();



    }

    /**
     * �̹߳��췽��
     * @param name ���̶߳�Ӧ���ݿ���torrents��update+temp��־�磺2014-10-27,bt��������ת�� ��Ӧ���ش洢���ļ�������
     * @param withFile �Ƿ���������ϴ��ı�־
     * @param index ��ҳ��¼ҳ��������õ�¼��formhash
     * @param loginPostUrl ��¼post�ӿڵ�ַ
     * @param postPage  ������ַ������÷�������formhash
     * @param uploadUrl �����ӿڵ�ַ
     * @param type ��������
     */
    public UploadTask_2015_05_24(String name,String folderpath,String withFile,String username,String password,String index,String loginPostUrl,String postPage,String uploadUrl,String type) {
        super(name);
        this.folderpath=folderpath;
        this.withFile=withFile;
        this.username=username;
        this.password=password;
        this.index=index;
        this.loginPostUrl=loginPostUrl;
        this.postPage=postPage;
        this.uploadUrl=uploadUrl;
        this.type=type;
        System.out.println(toString());
    }
    //��дrun����
    public void run(){
//        init();
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test() throws IOException, NoSuchAlgorithmException, KeyManagementException, CloneNotSupportedException {
        if(type.equals("mmhouse")){
            //MM��Ԣ��¼
            crawlerUtil.clientCreate("http","21mybbs.me","http://21mybbs.me/forum.php");
            mmLoging(username,password);
        }else{
            login(username, password);
            GcBean bean=JDBCSkydriveUtil.getCollectById("9");
            if("yes".equals(withFile)){
                upload(uploadUrl,bean);
            } else{
//            normalPost(uploadUrl, map);
            }
        }

    }

    /**
     * ��¼MM��Ԣ�ķ���
     */
    public void mmLoging(String username,String password) throws IOException, CloneNotSupportedException {
        // ����httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // ������������
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //--�°������
//        list.add(new BasicNameValuePair("formhash", getFormhash(index,"formhash")));
        //--mm��Ԣ�Ĳ���
        list.add(new BasicNameValuePair("fastloginfield", "username"));
        list.add(new BasicNameValuePair("quickforward", "yes"));
        list.add(new BasicNameValuePair("handlekey", "1s"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        uefEntity = new UrlEncodedFormEntity(list, GBK);
        HttpResponse response=crawlerUtil.post(loginPostUrl, uefEntity);
        Document document=crawlerUtil.getDocGBK(response);
        System.out.println(document.toString());
    }

    /**
     * ��ʼ������
     */
    public void init(){
        List<GcBean> rows=JDBCSkydriveUtil.getAllCollectLogs();
        login(username,password);
        for(int i=0;i<rows.size();i++){
            Map map= (Map) rows.get(i);
            System.out.println(map.toString());
            //����ѡ���Ƿ��ϴ�����:��
            if("yes".equals(this.withFile)){
                //�������������ʱ����ʹ�ô˷����ϴ�
                if("phpwind".equals(type)){
                    uploadForPHPWind(uploadUrl,map);
                }   else{
//                    upload(uploadUrl,map);
                }
            }else{
                //����ѡ���Ƿ��ϴ�����:��
                //�������ı���Ϣ
                if("nomal".equals(type)){
                    normalPost(uploadUrl, map);
                }else if("phpwind".equals(type)){
                    uploadForPHPWindNoFile(uploadUrl,map);
                }
                else{
                    uploadNoFile(uploadUrl,map);
                }
            }
        }
    }
    /**
     * �����ǰ����ͨ��form������post�ύ
     * @param url
     * @param map
     */
    public void normalPost(String url,Map map){
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", getFormhash(postPage,"formhash")));
        list.add(new BasicNameValuePair("isblog", ""));
        list.add(new BasicNameValuePair("frombbs", "1"));
        list.add(new BasicNameValuePair("subject", map.get("title").toString()+"["+map.get("size")+"]"));
        list.add(new BasicNameValuePair("message",map.get("message").toString()));
        list.add(new BasicNameValuePair("tags", "1"));
        list.add(new BasicNameValuePair("readperm", "0"));
        list.add(new BasicNameValuePair("iconid", "0"));
        list.add(new BasicNameValuePair("wysiwyg", "0"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, GBK);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,GBK);
                    if(doc!=null){
                        System.out.println(doc.select("div[class=box message]").toString());
                    }

                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ı��������� ��������
     * @param url
     * @param map
     */
    public void uploadNoFile(String url,Map map){
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//����Ϊ���������ģʽ
        builder.setCharset(Charset.forName(GBK));// ��������ı����ʽ
        builder.addTextBody("formhash", getFormhash(postPage,"formhash"), ContentType.TEXT_PLAIN);//����formhash���ӷ�������ҳ���ȡformhash�������
        builder.addTextBody("isblog", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("frombbs", "1", ContentType.TEXT_PLAIN);
        //����StringBody ����GBK��ֹ����
        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        //���� -�˴��Ĵ��ı�����ʹ�ñ��������txt�ĵ������ݻ���ʹ�����ݿ���message�ֶε����ݶ���
//        String msg=map.get("title").toString()+"\r\nӰƬ��С������:"+map.get("size")+"Ԥ��ͼƬ:\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addTextBody("selecttypeid", "33", ContentType.TEXT_PLAIN); //ѡ�����������
        builder.addTextBody("typeid", "33", ContentType.TEXT_PLAIN);//��������
        builder.addPart("subject", subject);//���ӱ���
        builder.addPart("message", message); //��������
        builder.addTextBody("localid[]", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachperm[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachprice[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachdesc[]", "111", ContentType.TEXT_PLAIN);
        builder.addTextBody("readperm", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("tags", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("price", "0", ContentType.TEXT_PLAIN);//����
        builder.addTextBody("iconid", "0", ContentType.TEXT_PLAIN);//ͼ��id
        builder.addTextBody("wysiwyg", "0", ContentType.TEXT_PLAIN);//
//
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("����..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){

            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
//                fileOperate.appendMethodB(logPath, DateUtil.getStrOfDateTime() + "[" + doc.select("div[class=box message]").toString() + "]");//��ӡ��ǰtask�߳���־
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public void uploadForPHPWindNoFile(String url,Map map){
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//����Ϊ���������ģʽ
        builder.setCharset(Charset.forName(GBK));// ��������ı����ʽ
        builder.addTextBody("verify", getFormhash(postPage, "verify"), ContentType.TEXT_PLAIN);//����formhash���ӷ�������ҳ���ȡformhash�������
        builder.addTextBody("magicname", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("magicid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("p_type", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc1", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_iconid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_autourl", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_usesign", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_convert", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_rvrc", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_money", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_credittype", "money", ContentType.TEXT_PLAIN);
        builder.addTextBody("step", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("pid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("action", "new", ContentType.TEXT_PLAIN);
        builder.addTextBody("fid", fid, ContentType.TEXT_PLAIN);
        builder.addTextBody("tid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("article", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("special", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc1", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc2", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc2", "", ContentType.TEXT_PLAIN);

        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addPart("atc_title", subject);//���ӱ���
        builder.addPart("atc_content", message); //��������
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("����..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){

            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                String content=EntityUtils.toString(eee, GBK);
//                System.out.println("Response content: " +content );
//                if(content.contains("�������")){
//                    UploadUI.jta.append("�����ɹ�"+map.get("title")+"\n");
//                }
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * ���phpwind���ϴ�
     * @param url
     * @param map
     */
    public void uploadForPHPWind(String url,Map map){
        HttpPost post = new HttpPost(url);
//        File file = new File(folderpath+this.getName()+"\\"+map.get("title"));
        File file = new File(folderpath+map.get("title"));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//����Ϊ���������ģʽ
        builder.setCharset(Charset.forName(GBK));// ��������ı����ʽ
        builder.addTextBody("verify", getFormhash(postPage, "verify"), ContentType.TEXT_PLAIN);//����formhash���ӷ�������ҳ���ȡformhash�������
        builder.addTextBody("magicname", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("magicid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_iconid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_autourl", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_usesign", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_convert", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_rvrc", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_money", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_credittype", "money", ContentType.TEXT_PLAIN);
        builder.addTextBody("step", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("pid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("action", "new", ContentType.TEXT_PLAIN);
        builder.addTextBody("fid", fid, ContentType.TEXT_PLAIN);
        builder.addTextBody("tid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("article", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("special", "0", ContentType.TEXT_PLAIN);
        //
        builder.addBinaryBody("attachment_1", file, ContentType.APPLICATION_OCTET_STREAM, folderpath+map.get("title"));
        builder.addTextBody("p_type", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc1", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc2", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc2", "", ContentType.TEXT_PLAIN);

        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addPart("atc_title", subject);//���ӱ���
        builder.addPart("atc_content", message); //��������
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("����..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//           System.out.println(doc.toString());
            if(doc!=null){
            }
//           if (eee != null) {
//               System.out.println("--------------------------------------");
//               String content=EntityUtils.toString(eee, GBK);
//               System.out.println("Response content: " +content );
//               if(content.contains("�������")){
//                   UploadUI.jta.append("�����ɹ�"+map.get("title")+"\n");
//               }
//               System.out.println("--------------------------------------");
//           } else{
//               System.out.println("upload error!");
//           }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * post�ӿ��ϴ���������ͨ�����ķ���
     * @param url �ϴ�post�ӿ�
     */
    public  void upload(String url,GcBean bean){
        HttpPost post = new HttpPost(url);
//        File file = new File(folderpath+this.getName()+"\\"+map.get("title"));
        File file = new File(folderpath+bean.getName()+"."+bean.getType());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//����Ϊ���������ģʽ
        builder.setCharset(Charset.forName(GBK));// ��������ı����ʽ
        builder.addTextBody("formhash", getFormhash(postPage,"formhash"), ContentType.TEXT_PLAIN);//����formhash���ӷ�������ҳ���ȡformhash�������
        builder.addTextBody("isblog", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("frombbs", "1", ContentType.TEXT_PLAIN);

        //����StringBody ����GBK��ֹ����
        String subjectStr=bean.getName()+"."+bean.getType();
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        //����
//        String msg=map.get("title").toString()+"\r\nӰƬ��С������:"+map.get("size")+"Ԥ��ͼƬ:\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        String msg=bean.getContent();
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        //typeid��Ҫ���ݾ���Ķ�ȡ
        builder.addTextBody("frombbs", "33", ContentType.TEXT_PLAIN); //ѡ�����������
        builder.addTextBody("typeid", "33", ContentType.TEXT_PLAIN);//��������
//        builder.addTextBody("selecttypeid", "2", ContentType.TEXT_PLAIN); //ѡ�����������
//        builder.addTextBody("typeid", "2", ContentType.TEXT_PLAIN);//��������
        builder.addPart("subject", subject);//���ӱ���
        builder.addPart("message", message); //��������
        builder.addBinaryBody("attach[]", file, ContentType.APPLICATION_OCTET_STREAM, folderpath + bean.getName()+"."+bean.getType());
        builder.addTextBody("localid[]", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachperm[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachprice[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachdesc[]", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("readperm", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("tags", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("price", "0", ContentType.TEXT_PLAIN);//����
        builder.addTextBody("iconid", "0", ContentType.TEXT_PLAIN);//ͼ��id
        builder.addTextBody("wysiwyg", "0", ContentType.TEXT_PLAIN);//
//
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("����..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){
            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
//                if(eee.toString().length()<1000){
//                    UploadUI.jta.append("�����ɹ�"+map.get("title")+"\n");
//                }
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * ���PHP�ĵ�¼����
     * @param username
     * @param password
     */
    public void loginPHPWind(String username,String password){
        // ����httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // ������������
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("forward", "http://lianyu.org/post.php?fid-"+fid+".htm"));
        list.add(new BasicNameValuePair("jumpurl", "http://lianyu.org/post.php?fid-"+fid+".htm"));
        list.add(new BasicNameValuePair("step", "2"));
        list.add(new BasicNameValuePair("hideid", "0")); //������
        list.add(new BasicNameValuePair("cktime", "31536000"));
        list.add(new BasicNameValuePair("lgt", "0"));
        list.add(new BasicNameValuePair("question", "0"));
        list.add(new BasicNameValuePair("customquest", ""));
        list.add(new BasicNameValuePair("answer", ""));
        list.add(new BasicNameValuePair("pwuser", username));
        list.add(new BasicNameValuePair("pwpwd", password));
        UrlEncodedFormEntity uefEntity;
        try {
            if(loginPostUrl.contains("maichun")){
                uefEntity = new UrlEncodedFormEntity(list, UTF8);
            }else{
                uefEntity = new UrlEncodedFormEntity(list, GBK);
            }
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,GBK);
                    if(doc!=null&&doc.toString().contains("˳��")){
                        System.out.println(".."+doc.toString());
                        System.out.println(username + "login success");
                    }  else{
                        System.out.println("login fault:" + doc.toString());
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (NullPointerException e){
        }
    }

    /**
     * ��¼����
     */
    public  void login(String username,String password) {
        // ����httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // ������������
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //--�°������
        list.add(new BasicNameValuePair("formhash", getFormhash(index,"formhash")));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("referer", "http://107.150.17.66"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("questionid", "0"));
        list.add(new BasicNameValuePair("answer", ""));
        list.add(new BasicNameValuePair("loginmode", ""));
        list.add(new BasicNameValuePair("styleid", ""));
        list.add(new BasicNameValuePair("loginsubmit", "true"));
        //--mm��Ԣ�Ĳ���
//        list.add(new BasicNameValuePair("fastloginfield", "username"));
//        list.add(new BasicNameValuePair("quickforward", "yes"));
//        list.add(new BasicNameValuePair("handlekey", "1s"));

        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, GBK);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,GBK);
                    if(doc!=null&&doc.toString().contains(username)){
                        System.out.println(username + "login success");
                    }  else{
                        System.out.println("login faul:" + doc.toString());
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
        }
    }
    /**
     * ��õ�ǰ��������ҳ���formhash ���������
     * @param url
     * @param keyname Ҫ��õ����ز���
     * @throws IOException
     */
    public  String  getFormhash(String url,String keyname)  {
        HttpGet get=new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
        } catch (IOException e) {
            return "";
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            Document doc=crawlerUtil.getDocument(entity,GBK);
            Elements elements=doc.select("form[method=post]").select("input[name="+keyname+"]");
            String formhash=elements.toString();
            System.out.println("fff"+formhash);
            if(formhash.length()>10){
                formhash=formhash.substring(formhash.indexOf("value=\"")+7,formhash.indexOf("\" />"));
                System.out.println("keyname.."+keyname+":"+formhash);
            }
            return formhash;
        }
        return "";
    }
}
