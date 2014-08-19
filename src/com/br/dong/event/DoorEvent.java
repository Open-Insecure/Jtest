package com.br.dong.event;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 下午2:38:52 
 * 类说明 
 *  定义事件对象，必须继承EventObject 
 */
import java.util.EventObject;  
public class DoorEvent extends EventObject {  
    private String doorState = "";//表示门的状态，有“开”和“关”两种  
  
    public DoorEvent(Object source, String doorState) {  
        super(source);  
        this.doorState = doorState;  
    }  
  
    public void setDoorState(String doorState) {  
        this.doorState = doorState;  
    }  
  
    public String getDoorState() {  
        return this.doorState;  
    }  
}
