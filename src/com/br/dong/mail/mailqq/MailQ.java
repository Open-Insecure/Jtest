package com.br.dong.mail.mailqq;

import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** 
 * @author  hexd
 * 创建时间：2014-7-22 下午5:00:50 
 * 类说明 
 * 发送QQ邮件 带html格式与图片
 */
public class MailQ {
	public static void main(String[] args) throws Exception {
		String smtpHost = "smtp.qq.com"; 
		String to = "254765064@qq.com"; 
		String from = "837484691@qq.com"; 
		String name = "837484691@qq.com"; 
		String password = "dong95b004"; 
		String subject = "测试邮件的题目"; 
		StringBuffer content = new StringBuffer(); // 邮件的html源代码 
		LinkedList attachList = new LinkedList(); // 附件的list,它的element都是byte[],即图片的二进制流 
		
		Properties props = new Properties(); 
		props.put("mail.smtp.host", smtpHost); 
		props.put("mail.smtp.auth", "true"); 
		Session session = Session.getDefaultInstance(props, null); 

		MimeMessage message; 
		InternetAddress[] address = {new InternetAddress(to)}; 

		message = new MimeMessage(session); 
		message.setFrom(new InternetAddress(from)); 
		message.setRecipients(Message.RecipientType.TO, address); 
		message.setSubject(subject); 
		message.setSentDate(new Date()); 
		// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个) 
		MimeMultipart mm = new MimeMultipart(); 
		// 新建一个存放信件内容的BodyPart对象 
		BodyPart mdp = new MimeBodyPart(); 
		content.append("<b>这是测试邮件的正文</b>"+"<td align=\"left\" valign=\"middle\"> <div align=\"center\"> <a href=\"#\" target=\"blank\" > <img src=\"cid:IMG1\"width=\"60\" height=\"45\" border=\"0\"> </a> </div> </td> ");
		attachList.add("f:\\bATty.ico");
		// 给BodyPart对象设置内容和格式/编码方式 
		mdp.setContent(content.toString(), "text/html;charset=GBK"); 
		// 这句很重要，千万不要忘了 
		mm.setSubType("related"); 
		mm.addBodyPart(mdp); 

		// add the attachments 
		for( int i=0; i<attachList.size(); i++) 
		{ 
		// 新建一个存放附件的BodyPart 
		mdp = createAttachment("f://bATty.ico"); 
//		DataHandler dh = new DataHandler(new ByteArrayDataSource((byte[])attachList.get(i),"application/octet-stream")); 
		//mdp.setDataHandler(dh); 
		// 加上这句将作为附件发送,否则将作为信件的文本内容 
	//	mdp.setFileName(new Integer(i).toString() + ".jpg"); 
		mdp.setHeader("Content-ID", "IMG1"); 
		// 将含有附件的BodyPart加入到MimeMultipart对象中 
		mm.addBodyPart(mdp); 
		} 
		// 把mm作为消息对象的内容 
		message.setContent(mm); 

		message.saveChanges(); 
		javax.mail.Transport transport = session.getTransport("smtp"); 
		transport.connect(smtpHost, name, password); 
		transport.sendMessage(message, message.getAllRecipients()); 
		transport.close(); 
	}
	  /**  
     * 根据传入的文件路径创建附件并返回  
     */ 
    public static MimeBodyPart createAttachment(String fileName) throws Exception {  
        MimeBodyPart attachmentPart = new MimeBodyPart();  
        FileDataSource fds = new FileDataSource(fileName);  
        attachmentPart.setDataHandler(new DataHandler(fds));  
        attachmentPart.setFileName(fds.getName());  
        return attachmentPart;  
    }  
}
