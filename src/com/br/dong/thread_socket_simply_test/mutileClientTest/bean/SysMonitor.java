package com.br.dong.thread_socket_simply_test.mutileClientTest.bean;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-10
 * Time: 10:00
 * 系统监控表信息bean
 */
public class SysMonitor extends BaseInfo{
    private String name;//系统名称
    private String namespell;//名称拼音
    private String namesimplify;//名称简写
    private String monitem;//监控项
    private String moninfo;//监控项信息
    private String monintr;//监控项说明
    private String monvalue;//监控值
    private String monthreshold;//监控阀值
    private String monitortime;//监控时间

    public SysMonitor() {

    }

    public SysMonitor(String name, String namespell, String namesimplify, String monitem, String moninfo, String monintr, String monvalue, String monthreshold, String monitortime) {
        this.name = name;
        this.namespell = namespell;
        this.namesimplify = namesimplify;
        this.monitem = monitem;
        this.moninfo = moninfo;
        this.monintr = monintr;
        this.monvalue = monvalue;
        this.monthreshold = monthreshold;
        this.monitortime = monitortime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespell() {
        return namespell;
    }

    public void setNamespell(String namespell) {
        this.namespell = namespell;
    }

    public String getNamesimplify() {
        return namesimplify;
    }

    public void setNamesimplify(String namesimplify) {
        this.namesimplify = namesimplify;
    }

    public String getMonitem() {
        return monitem;
    }

    public void setMonitem(String monitem) {
        this.monitem = monitem;
    }

    public String getMoninfo() {
        return moninfo;
    }

    public void setMoninfo(String moninfo) {
        this.moninfo = moninfo;
    }

    public String getMonintr() {
        return monintr;
    }

    public void setMonintr(String monintr) {
        this.monintr = monintr;
    }

    public String getMonvalue() {
        return monvalue;
    }

    public void setMonvalue(String monvalue) {
        this.monvalue = monvalue;
    }

    public String getMonthreshold() {
        return monthreshold;
    }

    public void setMonthreshold(String monthreshold) {
        this.monthreshold = monthreshold;
    }

    public String getMonitortime() {
        return monitortime;
    }

    public void setMonitortime(String monitortime) {
        this.monitortime = monitortime;
    }
}
