package com.br.dong.ssh_linux;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-06-16
 * Time: 12:48
 * 保持ssh连接
 */
public class JschTest2 {
    private String charset = "UTF-8"; // 设置编码格式  
    private String user; // 用户名  
    private String passwd; // 登录密码  
    private String host; // 主机IP  
    private JSch jsch;
    private Session session;

    /**
     *
     * @param user 用户名
     * @param passwd 密码
     * @param host 主机IP
     */
    public JschTest2(String user, String passwd, String host) {
        this.user = user;
        this.passwd = passwd;
        this.host = host;
    }

    /**
     * 连接到指定的IP 
     *
     * @throws JSchException
     */
    public void connect() throws JSchException {
        jsch = new JSch();
        session = jsch.getSession(user, host, 22);
        session.setPassword(passwd);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }

    /**
     * 执行相关的命令
     * date -d today +"%Y-%m-%d %H:%M:%S"   2015-06-16 01:11:59 格式输出当前连接的服务器时间
     */
    public void execCmd() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//读取控制台输入
        String command = "";
        BufferedReader reader = null;
        Channel channel = null;
        try {
            while ((command = br.readLine()) != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();//channel连接linux
                InputStream in = channel.getInputStream();//读取channel的输入
                reader = new BufferedReader(new InputStreamReader(in,
                        Charset.forName(charset)));
                String buf = null;
                while ((buf = reader.readLine()) != null) {//raadLine 循环读取的
                    System.out.println(buf);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.disconnect();
            session.disconnect();
        }
    }

    public static void main(String[] args) throws Exception {
        String user = "root";
        String passwd = "MMh0use.aK";
        String host = "23.226.66.36";

        JschTest2 demo = new JschTest2(user, passwd, host);
        demo.connect();
        demo.execCmd();
    }
}
