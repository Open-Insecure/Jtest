package com.br.dong.thread_socket_simply_test.mutileClientTest.execute;

import com.br.dong.cmd_test.StringUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-09
 * Time: 15:46
 * 获得linux下cpu使用率
 */
public class CpuUsage {

    /***
     * 获得cpu idle的值
     * @return
     */
    public static String getIdle() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("vmstat 1 1");//
//        Process p = rt.exec("ipconfig");//
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = null;
            int m = 0;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                if(m==2){
                    System.out.println(getIdleValue(str));
                    return getIdleValue(str);
                }
                m++;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error"+e;
        }finally {
            in.close();

        }
        return "no success";
    }
    public static String testGetIdle(){
        String exec = exec("167.88.124.141", "root", "95b004", 22, "vmstat 1 1");
//        System.out.println(exec);
        String temp[]=exec.split("\n");
        for(int i=0;i<temp.length;i++){
            if(i==(temp.length-1)){//只读取最后一行
//                System.out.println(temp[i]);
//                System.out.println(getIdleValue(temp[i]));
                return getIdleValue(temp[i]);
            }
        }
        return "error";
    }

    /***
     * 获得cpu使用率
     * @return
     * @throws Exception
     */
    public double getCpuInfo() throws Exception {
        double cpuUsed = 0;
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("top -b -n 1");// 调用系统的“top"命令
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = null;
            String[] strArray = null;
            while ((str = in.readLine()) != null) {
                int m = 0;
                if (str.indexOf(" R ") != -1 && str.indexOf("top") == -1) {// 只分析正在运行的进程，top进程本身除外
                    strArray = str.split(" ");
                    for (String tmp : strArray) {
                        if (tmp.trim().length() == 0)
                            continue;
                        if (++m == 9) {// 第9列为CPU的使用百分比(RedHat 9)
                            cpuUsed += Double.parseDouble(tmp);
                        }
                    }
                    // System.out.println(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return cpuUsed;
    }
    /**
     * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
     * @param host	主机名
     * @param user	用户名
     * @param psw	密码
     * @param port	端口
     * @param command	命令
     * @return
     */
    public static String exec(String host,String user,String psw,int port,String command){
        String result="";
        Session session =null;
        ChannelExec openChannel =null;
        try {
            JSch jsch=new JSch();
            session = jsch.getSession(user, host, port);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect();
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            int exitStatus = openChannel.getExitStatus();
            System.out.println(exitStatus);
            openChannel.connect();
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                result+= new String(buf.getBytes("gbk"),"UTF-8")+"\r\n";
            }
        } catch (JSchException e) {
            result+=e.getMessage();
        }catch ( IOException e){
            result+=e.getMessage();
        }
        finally{
            if(openChannel!=null&&!openChannel.isClosed()){
                openChannel.disconnect();
            }
            if(session!=null&&session.isConnected()){
                session.disconnect();
            }
        }
        return result;
    }

    /***
     * 获得idle的值
     * @param str
     */
    public static String  getIdleValue(String str){
        String tmp[]=StringUtil.clearBlank(str).split(" ");
        try{
            return tmp[14];
        }catch (Exception e){
            return "error";
        }
    }
    public static void main(String[] args) throws Exception {


//        CpuUsage cpuUsage=new CpuUsage();
//        System.out.println(cpuUsage.getIdle());
    }
}
