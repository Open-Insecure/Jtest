package com.br.dong.xstream;

public class Employee {
	private String name;
	 private String designation;
	 private String department;

	 public String getName() {
	 return name;
	 }
	 public void setName(String name) {
	 this.name = name;
	 }
	 public String getDesignation() {
	 return designation;
	 }
	 public void setDesignation(String designation) {
	 this.designation = designation;
	 }
	 public String getDepartment() {
	 return department;
	 }
	 public void setDepartment(String department) {
	 this.department = department;
	 }
	 @Override
	 public String toString() {
	 return "Name : "+this.name+
	 "\nDesignation : "+this.designation+
	 "\nDepartment : "+this.department;
	 }
}
