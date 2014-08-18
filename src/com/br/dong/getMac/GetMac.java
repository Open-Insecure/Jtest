package com.br.dong.getMac;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class GetMac {
	public static void main(String[] args) {
		System.out.println(getMACAddress("127.0.0.1"));
	}
	
    public static String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //if (str.indexOf("MAC Address") > 1) {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(
                                str.indexOf("=") + 2, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }
}
