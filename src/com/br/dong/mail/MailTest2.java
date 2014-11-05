package com.br.dong.mail;
/**
* @author  hexd
* 创建时间：2014-7-21 下午2:11:24
* 类说明 发送邮件类
*
* send failed, exception: com.sun.mail.smtp.SMTPSendFailedException: 554 MI:SPB UserReject 0,smtp13,EcCowEAp6nWUIc5TkDJiBA--.278S2 1406017940
*  please feedback at http://feedback.mail.126.com/antispam/complain.php?user=aishidong@163.com
	此类错误由于邮箱发送过度被封锁了
*/
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailTest2 {

    static Authenticator auth = new Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("aishidong@163.com", "95b004");
        }

    };

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.from", "aishidong@163.com");
        Session session = Session.getInstance(props, auth);
        //--要发送的文件
        String file="F:\\aa.txt";

        try {
        		   MimeMessage msg = new MimeMessage(session);
                   msg.setFrom();
                   msg.setRecipients(Message.RecipientType.TO, "837484691@qq.com");
                   //设置标题
                   msg.setSubject("测试自动发邮件");
                   msg.setSentDate(new Date());
                   String a="aa";
                   //设置文本
                   msg.setText(a+"\n");
                   //发送邮件
                   Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }

    }
}
