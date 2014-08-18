package com.br.dong.mail.mail163;
/** 
 * @author  hexd
 * 创建时间：2014-7-22 下午4:29:15 
 * 类说明 
 */
import javax.mail.*;  

public class MyAuthenticator extends Authenticator {  
      
    String userName = null;  
    String password = null;  
  
    public MyAuthenticator() {  
    }  
  
    public MyAuthenticator(String username, String password) {  
        this.userName = username;  
        this.password = password;  
    }  
  
    protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(userName, password);  
    }  
}  
