package com.br.dong.utils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-1
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 * base 64加密
 */
public class EncodeUtil {
    public static String encodeString(String str) throws IOException
    {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String encodedStr = new String(encoder.encodeBuffer(str.getBytes()));

        return encodedStr.trim();
    }
    public static String decodeString(String str) throws IOException
    {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String value = new String(dec.decodeBuffer(str));
        return value;
    }

    public static void main(String[] args) throws IOException {
//        String str="123456789";
//
//        try {
//            String encode=EncodeUtil.encodeString(str);
//            String decode=EncodeUtil.decodeString(encode) ;
//            System.out.println();
//            System.out.println(encode+":"+decode);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        String str="aHR0cCUzQSUyRiUyRmlxaXlpMS55b3VrdS1jb20tMTYzLWNvbS5jb20lMkZpcGhvbmUlMkY4OTQ3Lm1wNA==";
        System.out.println(URLDecoder.decode(EncodeUtil.decodeString(str)));

    }
}
