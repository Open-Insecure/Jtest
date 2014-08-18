package com.br.dong.mac;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/** 
 * @author  hexd
 * 创建时间：2014-6-4 上午11:17:49 
 * 类说明 
 */
public class GetMac {
	public static List<String> getAllMacAddresses()  
	{  
	    List<String> addresses = new ArrayList<String>();  
	  
	    StringBuffer sb = new StringBuffer();  
	    try  
	    {  
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();  
	        while(networkInterfaces.hasMoreElements())  
	        {  
	            NetworkInterface netInterface = networkInterfaces.nextElement();  
	            byte[] mac = netInterface.getHardwareAddress();  
	            if(mac != null && mac.length != 0)  
	            {  
	                sb.delete(0, sb.length());  
	                for(byte b : mac)  
	                {  
	                    String hexString = Integer.toHexString(b & 0xFF);  
	                    sb.append((hexString.length() == 1) ? "0" + hexString : hexString);  
	                }  
	                addresses.add(sb.toString());  
	            }  
	        }  
	    }  
	    catch(SocketException e)  
	    {  
	        e.printStackTrace();  
	    }  
	  
	    return addresses;  
	}  
	
	public static void main(String[] args) {
		List list=getAllMacAddresses();
		System.out.println(list.size()+list.get(0).toString());
	}
}
