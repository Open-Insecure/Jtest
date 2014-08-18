package com.br.dong.list;
/** 
 * @author  hexd
 * 创建时间：2014-6-24 下午6:49:24 
 * 类说明 
 * 排序比较
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
public class T {
  public static void main(String[] args) {
    ArrayList list = new ArrayList();
    list.add("92.8");
    list.add("68.9");
    list.add("168.61");
    list.add("242");
    list.add("317");
    list.add("105");
    // 字符串排序
    Collections.sort(list);
    System.out.println(list.toString()); // [105, 168.61, 242, 317, 68.9, 92.8]
    Collections.sort(list, new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        return new Double((String) o1).compareTo(new Double((String) o2));
      }
    });
    System.out.println(list.toString()); // [68.9, 92.8, 105, 168.61, 242, 317]
  }
}
