package com.br.dong.jdbctemplate;
/** 
 * @author  hexd
 * 创建时间：2014-8-1 下午2:53:39 
 * 类说明 
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class SpringUtil {
	//ClassPathXmlApplicationContext 从src文件下开始找
    private static ApplicationContext  ctx = new ClassPathXmlApplicationContext("com/br/dong/jdbctemplate/applicationContext.xml");
    
    public static Object getBean(String beanName){
         return ctx.getBean(beanName);
    }    
    public static void main(String[] args) {
    	 
	}
}
