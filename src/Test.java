import java.lang.reflect.Field;
import java.util.Date;


public class Test {
	private String a;
	private int b;
	private Date c;

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
	
	public String toString(){
		return a+b+c;
	}
	public static void main(String[] args) {
		Class<?> clazz = Test.class; 
		Field[] fields = clazz.getDeclaredFields();//获取这个类所有的成员变量 
		  for(Field field : fields) {  
	           System.out.println(field.getName());  
	       }  
	  
	}
}
