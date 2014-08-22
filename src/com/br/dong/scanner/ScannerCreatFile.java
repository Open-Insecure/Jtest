package com.br.dong.scanner;


import com.br.dong.file.FileOperate;
import com.br.dong.utils.DateUtil;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-22
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class ScannerCreatFile {

    private static Boolean finishFlag=false;
    public static void main(String[] args) {
        FileOperate fo=new FileOperate();
        fo.newFolderMuti(scannerMain("f:/vedios/default"));

    }
    public static String  scannerMain(String folder){
         String local=folder;
        //提示信息
        System.out.println("请输入下载到本地硬盘的路径，默认为F盘下的vedios文件下的当前日期文件夹下(如["+local+"])，" +
                "如果文件不存在，则自动创建");
        //数组缓冲
        byte[] b = new byte[1024];
        //有效数据个数
        int n = 0;
        try{
            System.out.println("请输入是否使用默认路径[1,表示使用默认路径。2,表示自定义路径]");
            while(true){
                //读取数据
               int type= readKeyboard();
               //默认路径
                if(type==1){
                     break;
                }
                if(!checkType(type)){
                    System.out.println("输入非法，请重新输入[输入1或2]！");
                    continue;
                }else{
                    System.out.println("请选择盘符[C,D,E,F]");
                    String disk="F";
                    String floder="vedios";
                    while (true){
                        disk=readKeyBoardChar().toUpperCase();
                        if(!checkDisk(disk)) {
                            System.out.println("输入非法，请重新选择盘符[C,D,E,F]！");
                            continue;
                        }else{
                            break;
                        }
                    }
                    System.out.println("请输入"+disk+"盘下要创建的文件名");
                    while(true){
                        Scanner scanner=new Scanner(System.in);
                        floder=scanner.nextLine();
                        if("".equals(floder)){
                            continue;
                        }else{
                            System.out.println("你选择的下载目录为"+disk+":/"+floder+"/");
                            local=disk+":/"+floder+"/"+ DateUtil.getCurrentDay() ;
                            finishFlag=true;
                            break;
                        }
                    }
                }
               if(finishFlag){
                   break;
               }
            }
        }catch(Exception e){}
        System.out.println("开始创建下载目录.."+local);
        return local;
    }

    /**
     * @param type 类型
     * @return true代表符合要求，false代表不符合
     */
    public static boolean checkType(int type) {
        if (type == 1 || type == 2) {
            return true;
        } else {
            return false;
        }
    }
   public static boolean checkDisk(String disk){
        if("CDEF".contains(disk.toUpperCase())) {
            return true;
        }
       return false;
   }

    /**
     * 读取用户输入
     * @return 玩家输入的整数，如果格式非法则返回0
     */
    public static int readKeyboard() {
        try {
            // 缓冲区数组
            byte[] b = new byte[1024];
            // 读取用户输入到数组b中，
            // 读取的字节数量为n
            int n = System.in.read(b);
            // 转换为整数
            String s = new String(b, 0, n - 1);
            int num = Integer.parseInt(s);
            return num;
        } catch (Exception e) {}
        return 0;
    }

    public static String readKeyBoardChar(){
        String s="NO";
        try {
            // 缓冲区数组
            byte[] b = new byte[1024];
            // 读取用户输入到数组b中，
            // 读取的字节数量为n
            int n = System.in.read(b);
            // 转换为整数
            s = new String(b, 0, n - 1);

        } catch (Exception e) {}
        return s;
    }
}
