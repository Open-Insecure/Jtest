//package com.br.dong.torrentTest;
//
//import java.io.File;
//
///**
// * Created with IntelliJ IDEA.
// * User: Dong
// * Date: 14-11-3
// * Time: 下午5:04
// * To change this template use File | Settings | File Templates.
// */
//public class Test {
//    public static void main(String[] args) throws Exception
//    {
//        String path = "d:/0623060253062306025306.torrent";
//
//        TorrentFile file = new TorrentFile(new File(path));
//
//        String[] strs = file.getFilenames();
//        long[] longs = file.getLengths();
//        System.out.println(strs.length + " " + longs.length);
//
//        for(int i = 0; i < strs.length; i ++)
//        {
//            System.err.println(strs[i] + " --->  " + longs[i]);
//        }
//
//
//    }
//}
