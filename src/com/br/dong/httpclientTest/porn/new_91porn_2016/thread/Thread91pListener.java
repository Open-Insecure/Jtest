package com.br.dong.httpclientTest.porn.new_91porn_2016.thread;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-13
 * Time: 16:30
 */
public interface Thread91pListener extends EventListener {
   /**加载完成的时候触发的方法*/
   public void complete(Thread91pEvent event);
   /**加载错误的时候触发的方法*/
   public void onError(Thread91pEvent event);
}
