package com.br.dong.httpclientTest.sis001.sis001For94lu;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-02
 * Time: 9:10
 * 针对94lu的发帖程序
 * 新增一张针对94lu发帖的记录表，防止重复发帖
 * 发帖:标题 标签 视频链接 时长 图片地址
 *
 */
public class Sis001PostTask extends SisBase{

    private static Logger logger = Logger.getLogger(Sis001PostTask.class);//日志

    public static void main(String[] args) {
        //登录
        Boolean loginflag=false;
        loginflag= login(username,password);
        if(!loginflag){
            logger.info(username + "login error,try login again！");
            return ;
        }
        logger.info(username + "login success!");
        //开始进行发帖
    }
}
