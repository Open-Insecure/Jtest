package com.br.dong.httpclientTest._1024gc_2015_04_10;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-5-28
 * Time: 16:13
 * 发表skydrive中采集的种子信息的帖子
 */
public class UploadForLinux {


    public static void main(String[] args) {
        int type = 0; // 要发布的网站
       //选择要发布的站点
        while(true){
            System.out.println("please chose website:[1 for xinbali,2 for mmhose,3 for yuhuawangchao]");// 1表示发布新巴黎 2 mmhouse 3御花王朝
            type = readKeyboard();
            // 校验
            if (!checkType(type)) {
                //输入非法则重新进入循环
                System.out.println("Enter illegal, please re-enter");
                continue;
            }else{
                break;
            }
        }
        //选择好站点后 选择要发布的板块
        while(true){

           break;
        }
        //校验合法，开始发布
        System.out.println("start thread ..");
        switch(type){
            case 1:
                System.out.println("type:"+type);
                break;
            case 2:
                System.out.println("type:"+type);
                break;
            case 3:
                System.out.println("type:"+type);
                break;
            default:
                System.out.println("type:"+type);
        }


    }
    /**
     * 押的类型校验
     * @param type 类型
     * @return true代表符合要求，false代表不符合
     */
    public static boolean checkType(int type) {
        if (type == 1 || type == 2 ||type==3) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 读取用户输入
     * @return  输入的整数，如果格式非法则返回0
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
}
