package com.br.dong.json;

import java.io.Serializable;


/**
 * Json与实体对象转化
 * @author hoodoo
 * @data 2012年12月22日
 *
 */
public class Subject implements Serializable {
 private static final long serialVersionUID = 1L;
 private int id;
 private String name;
 public Subject() {
 }
 public Subject(int id,String name) {
 this.id=id;
 this.name=name;
 }
 public int getId() {
 return id;
 }
 public void setId(int id) {
 this.id = id;
 }
 public String getName() {
 return name;
 }
 public void setName(String name) {
 this.name = name;
 }


}
