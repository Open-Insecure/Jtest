package com.br.dong.poi_Excel;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-1
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class ManagerBean {
    private String jobNum;//  工号
    private String name;  // 姓名
    private String sex; // 性别
    private String department;  //部门
    private String job;    //工作
    private String email;   //  email
    private String phone;  //     手机

    @Override
    public String toString() {
        return "ManagerBean{" +
                "jobNum='" + jobNum + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", department='" + department + '\'' +
                ", job='" + job + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String departmentId;   //工号


}
