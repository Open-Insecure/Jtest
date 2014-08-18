package com.br.dong.json;

import java.io.Serializable;
/**
 * Json与实体对象转化
 * @author hoodoo
 * @data 2012年12月22日
 *
 */
public class Beans implements Serializable{
 private static final long serialVersionUID = 1L;
 private int gradeId;
 private Subject[] subjects;
 public int getGradeId() {
 return gradeId;
 }
 public Subject[] getSubjects() {
 return subjects;
 }
 public void setSubjects(Subject[] subjects) {
 this.subjects = subjects;
 }
 public void setGradeId(int gradeId) {
 this.gradeId = gradeId;
 }
 
}
 
 
 

 

 
 
 
