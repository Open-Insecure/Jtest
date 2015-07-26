package com.br.dong.log;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015/6/11
 * Time: 16:36
 */
public class Log4jTest {

    private static Logger logger = Logger.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        // System.out.println("This is println message.");

        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
}