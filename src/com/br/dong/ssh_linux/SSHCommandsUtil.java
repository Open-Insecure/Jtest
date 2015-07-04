package com.br.dong.ssh_linux;

import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-6-3
 * Time: 8:46
 * java ssh连接linux
 * 提供一次性执行命令与保持ssh连接执行命令的方法
 * 注意：保持ssh连接执行命令的 必须构造SSHCommandsUtil的实例才行。
 */
public class SSHCommandsUtil {
    private String charset = "UTF-8"; // 设置编码格式
    private String username;//用户名
    private String password;//密码
    private int port;//端口
    private String host; // 主机IP
    private JSch jsch=new JSch();
    private Session session;
    private Channel channel = null;


    private static final Log log= LogFactory.getLog(SSHCommandsUtil.class);


    /**
     * 有参构造方法
     */
    public SSHCommandsUtil(String host, int port, String username, String password) {
        this.username = username;
        this.password = password;
        this.port = port;
        this.host = host;
    }

    /**
     * 连接到
     * @return
     * @throws JSchException
     */
    public String connect() throws JSchException {
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        String flag=session.isConnected()==true?"true":"false";
        return flag;//返回链接状态
    }

    /**
     * 保持ssh连接执行命令
     * @return
     */
    public String keepExecCommand(String command){
        if(!session.isConnected()) return "当前未链接到任何主机！";
        BufferedReader reader = null;
        String result="";
        try{
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            if(!channel.isConnected()) channel.connect();//channel连接linux
            InputStream in = channel.getInputStream();//读取channel的输入
            reader = new BufferedReader(new InputStreamReader(in,
                    Charset.forName(charset)));
            String buf = null;
            while ((buf = reader.readLine()) != null) {//raadLine 循环读取的
                result=result+ buf;
            }

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            channel.disconnect();
//            session.disconnect();
        }
        return result;
    }

    /**
     * 关闭连接
     * @return
     */
    public Boolean closeConnect(){
      channel.disconnect();
      session.disconnect();
      return !session.isConnected();//未连接 则返回true
    }

    /**
     * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）只执行一次
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
        log.info("主机:"+host+"命令:"+command);
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



    public static void main(String args[]) throws JSchException {
//        String myshell="# !/bin/sh  \n" +
//                "TOP_NUM=800\n" +
//                "index=0 \n" +
//                "function url_down(){ while [ $index -le $TOP_NUM ]\n" +
//                "do\n" +
//                "echo $index \n" +
//                "index=`expr $index + 24`\n" +
//                "done }\n" +
//                "\n" +
//                "url_down";
//        String exec = exec("23.226.66.36", "root", "MMh0use.aK", 22, myshell);
//        System.out.println(exec);


        SSHCommandsUtil test=new SSHCommandsUtil("23.226.66.36",22,"root","MMh0use.aK");
        System.out.println(test.connect());
//        System.out.println(test.keepExecCommand("date -d today +\"%Y-%m-%d %H:%M:%S\" "));
//        System.out.println(test.closeConnect());
//        System.out.println(test.keepExecCommand("date -d today +\"%Y-%m-%d %H:%M:%S\" "));
//        System.out.println(test.connect());
//        System.out.println(test.keepExecCommand("source /home/oracle/.bash_profile"));
//        System.out.println(test.keepExecCommand("echo \"recover progress :20\""));
//        System.out.println(test.keepExecCommand("date -d today +\"%Y-%m-%d %H:%M:%S\" "));
//        System.out.println(test.keepExecCommand("date -d today +\"%Y-%m-%d %H:%M:%S\" "));

//        test.keepExecCommand("mkdir -p /myshell/mytest/oracle");
//        test.keepExecCommand("touch /myshell/mytest/oracle/2.sh ");
          test.keepExecCommand("cat >>/myshell/mytest/oracle/2.sh<<!\n" +
                  "sasasasa"+"\n!");

    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public JSch getJsch() {
        return jsch;
    }

    public void setJsch(JSch jsch) {
        this.jsch = jsch;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
