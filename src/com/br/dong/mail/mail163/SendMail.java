package com.br.dong.mail.mail163;
/** 
 * @author  hexd
 * 创建时间：2014-7-22 下午4:30:38 
 * 类说明 
 * 发送QQ邮箱 带附件类型的
 */
public class SendMail {
	 public static void main(String[] args) {  
	        SendMail.send_163();  
	    }  
	      
	    // 163邮箱  
	    public static void send_163() {  
	        // 这个类主要是设置邮件  
	        MailSenderInfo mailInfo = new MailSenderInfo();  
	        mailInfo.setMailServerHost("smtp.qq.com");  
	        mailInfo.setMailServerPort("25");  
	        mailInfo.setValidate(true);  
	        mailInfo.setUserName("837484691@qq.com"); // 实际发送者  
	        mailInfo.setPassword("dong95b004");// 您的邮箱密码  
	        mailInfo.setFromAddress("837484691@qq.com"); // 设置发送人邮箱地址  
	        mailInfo.setToAddress("837484691@qq.com"); // 设置接受者邮箱地址  
	        mailInfo.setSubject("标题");  
	        mailInfo.setContent("<b>内容</b>");  
	        // 这个类主要来发送邮件  
	        SimpleMailSender sms = new SimpleMailSender();  
	        sms.sendTextMail(mailInfo); // 发送文体格式  
	        sms.sendHtmlMail(mailInfo,"f:\\aa.txt"); // 发送html格式  
	    }  
}
