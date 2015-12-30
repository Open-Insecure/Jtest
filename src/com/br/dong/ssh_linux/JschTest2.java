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
 * ����ssh����
 */
public class JschTest2 {
    private String charset = "UTF-8"; // ���ñ����ʽ  
    private String user; // �û���  
    private String passwd; // ��¼����  
    private String host; // ����IP  
    private JSch jsch;
    private Session session;

    /**
     *
     * @param user �û���
     * @param passwd ����
     * @param host ����IP
     */
    public JschTest2(String user, String passwd, String host) {
        this.user = user;
        this.passwd = passwd;
        this.host = host;
    }

    /**
     * ���ӵ�ָ����IP 
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
     * ִ����ص�����
     * date -d today +"%Y-%m-%d %H:%M:%S"   2015-06-16 01:11:59 ��ʽ�����ǰ���ӵķ�����ʱ��
     */
    public void execCmd() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//��ȡ����̨����
        String command = "";
        BufferedReader reader = null;
        Channel channel = null;
        try {
            while ((command = br.readLine()) != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();//channel����linux
                InputStream in = channel.getInputStream();//��ȡchannel������
                reader = new BufferedReader(new InputStreamReader(in,
                        Charset.forName(charset)));
                String buf = null;
                while ((buf = reader.readLine()) != null) {//raadLine ѭ����ȡ��
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
        String passwd = "95b004";
        String host = "167.88.124.141";

        JschTest2 demo = new JschTest2(user, passwd, host);
        demo.connect();
        demo.execCmd();
    }
}
