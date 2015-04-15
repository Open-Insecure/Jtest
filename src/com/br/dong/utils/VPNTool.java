package com.br.dong.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-11
 * Time: 14:06
 */
public class VPNTool {
    private synchronized static String executedCmd(String cmd)  throws IOException{
        Process process = Runtime.getRuntime().exec("cmd /c "+cmd);
        StringBuffer sbCmd=new StringBuffer();
        BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream())) ;
        String line="";
        while((line=br.readLine())!=null){
            sbCmd.append(line);
        }
        return sbCmd.toString();
    }
    public  synchronized static boolean disconnectVPN(String vpnName) throws IOException{
        String cmd="rasdial"+vpnName+" /disconnet";
        String result=executedCmd(cmd);
        if(result==null||result.contains("没有连接")){
            return false;
        }
        return true;
    }
    public  synchronized static boolean connettVPN(String vpnName,String name,String password) throws IOException{
        String cmd="rasdial"+vpnName+""+name+""+password;
        String result=executedCmd(cmd);
        if(result==null||result.contains("已经连接"))
            return false;
        return true;
    }

    public static void main(String[] args) throws IOException {
        VPNTool.connettVPN("", "", "");
    }
}
