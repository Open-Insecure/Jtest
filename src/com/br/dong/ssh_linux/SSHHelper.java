package com.br.dong.ssh_linux;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-6-3
 * Time: 8:46
 * java 测试连接ssh
 *
 */
public class SSHHelper {
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



    public static void main(String args[]){
        String myshell="# !/bin/sh  \n" +
                "  \n" +
                "TOP_NUM=800  \n" +
                "index=0  \n" +
                "  \n" +
                "  \n" +
                "function url_down(){  \n" +
                "  \n" +
                "while [ $index -le $TOP_NUM ]  \n" +
                "    do  \n" +
                "       echo $index  \n" +
                "        index=`expr $index + 24`  \n" +
                "done  \n" +
                "  \n" +
                "}  \n" +
                "  \n" +
                "url_down";
        String exec = exec("23.226.66.36", "root", "MMh0use.aK", 22, myshell);
        System.out.println(exec);
    }
}
