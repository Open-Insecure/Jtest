package com.br.dong.httpclientTest.Crawler;

/** 
 * @author  hexd
 * 创建时间：2014-7-22 下午12:46:01 
 * 类说明 
 */

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class GetWeb {
	//爬取深度
    private int webDepth = 3;
    //最大线程
    private int intThreadNum = 10;
    //开始主页面
    private String strHomePage="";
    private String myDomain;
    private String fPath = "web";
    private ArrayList<String>arrUrls = new ArrayList<String>();//
    private ArrayList<String>arrUrl = new ArrayList<String>();//
 
    private Hashtable<String,Integer> allUrls = new Hashtable<String,Integer>();
    private Hashtable<String,Integer> deepUrls = new Hashtable<String,Integer>();
    private int intWebIndex = 0;
    private String charset = "GB2312";
    private String report = "";
    private long startTime;
    private int webSuccessed = 0;
    private int webFailed = 0;
    private static int imageNumber = 0;

    private List<String> ImageUrl;

    public GetWeb(String s)
    {
        this.strHomePage = s;
    }

    public GetWeb(String s,int i)
    {
        this.strHomePage = s;
        this.webDepth = i;
    }

    private synchronized void addWebSuccessed()
    {
        webSuccessed++;
    }

    private synchronized void addWebFailed()
    {
        webFailed++;
    }

    /**生成txt报告
     * @param s
     */
    public synchronized void addReport(String s)
    {
        try
        {
            report += s;
            PrintWriter pwReport = new PrintWriter(new FileOutputStream("report.txt"));
            pwReport.println(report);
            pwReport.close();
        }
        catch(Exception e)
        {
            System.out.println("生成报告失败！");
        }
    }

    public synchronized String getAUrl()
    {
        String tmpAUrl = arrUrls.get(0);
        arrUrls.remove(0);
        return tmpAUrl;
    }

    public synchronized String getUrl()
    {
        String tmpUrl = arrUrl.get(0);
        arrUrl.remove(0);
        return tmpUrl;
    }

    public synchronized Integer getIntWebIndex()
    {
        intWebIndex++;
        return intWebIndex;
    }

    public static void main(String[] args)
    {
        /*if(args.length == 0 || args[0].equals(""))
        {
            System.out.println("No input!");
            System.exit(1);
        }
        else if(args.length == 1)
        {*/
    		
            GetWeb gw = new GetWeb("http://www.taobao.com/");//args[0]);
            gw.getWebByHomePage();
        /*}
        else
        {
            GetWeb gw = new GetWeb(args[0],Integer.parseInt(args[1]));
            gw.getWebByHomePage();
        }*/
    }
    public void getWebByHomePage()
    {
        startTime = System.currentTimeMillis();
        this.myDomain = getDomain();
        if(myDomain == null)
        {
            System.out.println("Wrong Input!");
            return ;
        }
        System.out.println("HomePage = "+ strHomePage);
        addReport("HomePage =" + strHomePage+"!\n");
        System.out.println("Domain ="+myDomain);
        addReport("Domain = "+myDomain+"!\n");
        arrUrls.add(strHomePage);
        arrUrl.add(strHomePage);
        allUrls.put(strHomePage, 0);
        deepUrls.put(strHomePage, 1);
        File fDir = new File(fPath);
        if(!fDir.exists())
        {
            fDir.mkdir();
        }
        System.out.println("Start!");
        this.addReport("Start!\n");
        String tmp = getAUrl();
        this.getWebByUrl(tmp,charset,allUrls.get(tmp)+"");
        int i =0;
        for(i = 0; i < intThreadNum; i++)
        {
            new Thread(new Processer(this)).start();
        }
        while(true)
        {
            if(arrUrls.isEmpty() && Thread.activeCount() == 1)
            {
                long finishTime = System.currentTimeMillis();
                long costTime = finishTime - startTime;
                System.out.println("Start time" + startTime+"Finish time"+finishTime+"  "+"Cost time =" + costTime + "ms"+"\n");
                System.out.println("Total url number ="+(webSuccessed+webFailed)+"\n"+"Successed :"+webSuccessed+"Failed:"+webFailed+"\n");
                addReport("Total url number ="+(webSuccessed+webFailed)+"\n"+"Successed :"+webSuccessed+"Failed:"+webFailed+"\n");
                String strIndex ="";
                String tmpUrl="";
                while(!arrUrl.isEmpty())
                {
                    tmpUrl = getUrl();
                    strIndex += "Web depth:"+ deepUrls.get(tmpUrl)+"  Filepath:"+ fPath+"/web"+allUrls.get(tmpUrl)+".htm"+"  url:"+tmpUrl+"\n\n";
                }
                System.out.println(strIndex);
                try
                {
                    PrintWriter pwIndex = new PrintWriter(new FileOutputStream("fileindex.txt"));
                    pwIndex.println(strIndex);
                    pwIndex.close();
                }
                catch(Exception e)
                {
                    System.out.println("索引文件失败！");
                }
                strIndex ="";
                while(!arrUrl.isEmpty())
                {
                    strIndex += getUrl();
                }
                System.out.println(strIndex);
                try
                {
                    PrintWriter pwIndex = new PrintWriter(new FileOutputStream("fileindex1.txt"));
                    pwIndex.println(strIndex);
                    pwIndex.close();
                }
                catch(Exception e)
                {
                    System.out.println("索引文件失败！");
                }

                break;
            }
        }
    }
    public void getWebByUrl(String strUrl,String charset,String fileIndex)
    {
        try
        {
            System.out.println("Getting web by url:"+ strUrl);
            addReport("Getting web by url:"+ strUrl+"\n");
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            InputStream is = null;
            is = url.openStream();
            /*String filePath = fPath+"/web"+fileIndex+".htm";
            PrintWriter pw = null;
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            pw = new PrintWriter(writer);*/
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String rLine = null;
            String tmp_rLine = null;
            ImageUrl = new ArrayList<String>();
            while((rLine = bReader.readLine()) != null)
            {
                tmp_rLine = rLine;
                ImageUrl = getImgStr(rLine);
                int str_len = tmp_rLine.length();
                if(str_len > 0)
                {
                    /*sb.append("\n"+tmp_rLine);
                    pw.println(tmp_rLine);
                    pw.flush();*/
                    if(deepUrls.get(strUrl) < webDepth)
                        getUrlByString(tmp_rLine,strUrl);
                }
                for(String e:ImageUrl)
                {
                    imageNumber ++;
                    StringBuilder tmp = new StringBuilder(e);
                    if(e.endsWith(".jpg") || e.endsWith(".bmp")||e.endsWith(".png")){
                    	//下载图片链接的图片
                    this.DownloadImage(e,"F:/imgtest/"+tmp.substring((tmp.lastIndexOf("/")+1), tmp.length()).toString());
				    }
                    System.out.println(e.toString()+"\n");
                }
                tmp_rLine = null;
            }
            is.close();
            //pw.close();
            System.out.println("Get web Successfully!"+ strUrl);
            addReport("Get web Successfully!"+ strUrl+"\n");
            addWebSuccessed();
        }
        catch(Exception e)
        {
            System.out.println("Get web failed!"+ strUrl);
            System.out.println(e.toString());
            addReport("Get web failed!"+ strUrl+"\n");
            addWebFailed();
        }
    }
    public String getDomain()
    {
        String reg = "(?<=http\\://[a-zA-Z0-9]{0,100}[.]{0,1})[^.\\s]*?\\.(com|cn|net|org|biz|inf|o|cc|tv|h)";
       
        Pattern p = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(strHomePage);
        boolean blnp = m.find();
        if(blnp == true)
            return m.group(0);
        return null;
    }
    public void getUrlByString(String inputArgs,String strUrl)
    {
        String tmpStr = inputArgs;
        String regUrl ="(?<=(href=)[\"]?[\']?)[http://][^\\s\"\'\\?]*("+"taobao.com"+")[^\\s\"\'>]*";
        Pattern p = Pattern.compile(regUrl,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tmpStr);
        boolean blnp = m.find();
        //System.out.println("inputArgs:  "+inputArgs+"\n");
        while(blnp == true)
        {
            System.out.println("aaaa : "+m.group(0));
            if(!allUrls.containsKey(m.group(0)))// && lookFordestinationUrl(m.group(0)))
            {
                System.out.println("Find a new url,depth:"+(deepUrls.get(strUrl)+1)+" "+m.group(0));
                addReport("Find a new url,depth:"+(deepUrls.get(strUrl)+1)+" "+m.group(0)+"\n");
                arrUrls.add(m.group(0));
                arrUrl.add(m.group(0));
                allUrls.put(m.group(0), getIntWebIndex());
                deepUrls.put(m.group(0), (deepUrls.get(strUrl)+1));
            }
            tmpStr = tmpStr.substring(m.end(),tmpStr.length());
            m = p.matcher(tmpStr);
            blnp = m.find();
        }
    }
    
    
    
    class Processer implements Runnable
    {
        GetWeb gw;
        public Processer(GetWeb g)
        {
            this.gw = g;
        }
        public void run()
        {
            while(!arrUrls.isEmpty())
            {
                String tmp = getAUrl();
                getWebByUrl(tmp,charset,allUrls.get(tmp)+"");
            }
        }
    }
public static List<String> getImgStr(String htmlStr){
         String img="";
         Pattern p_image;
         Matcher m_image;
         List<String> pics = new ArrayList<String>();

         String regEx_img ="<img(?:.*)src=(\"{1}|\'{1})([^\\[^>]+[gif|jpg|jpeg|bmp|bmp]*)(\"{1}|\'{1})(?:.*)>";// "<img.*src=(.*?)[^>]*?>"; //图片链接地址
         p_image = Pattern.compile
                 (regEx_img,Pattern.CASE_INSENSITIVE);
         m_image = p_image.matcher(htmlStr);
         while(m_image.find()){
             //if(lookFordestinationUrl(m_image.group())){
                 img = img + "," + m_image.group();
                 Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
                 while(m.find()){
                    pics.add(m.group(1));
                 }
            // }
         }
            return pics;
     }
    public static boolean lookFordestinationUrl(String str)
    {
       return str.contains("衣") || str.contains("鞋") || str.contains("裤") || str.contains("装");
    }
    
    /**下载图片
     * @param urlString
     * @param filename
     * @throws Exception
     */
    public void DownloadImage(String urlString,String filename)throws Exception
    {
        try{
            URL url = new URL(urlString);
            System.out.println("imageUrl:    "+urlString);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(filename);
            while((len = is.read(bs)) != -1)
            {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
	    }
	    catch(Exception e)
	    {
                System.out.println(e.toString());
            }
    }
}



