package com.br.dong.httpclientTest._1024gc_2015_04_10;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-24
 * Time: 下午12:11
 * To change this template use File | Settings | File Templates.
 * 网盘文件基本信息表
 */
public class FileBaseInfo {
    private int id;// id号
    private int userId;//上传该文件的用户id 用户id作为文件的根目录名
    private String userName="";//上传该文件的用户名
    private String uploadTime="";//上传时间
    private String fileName="";//文件名
    private String fileSize="";//文件大小 mb
    private String fileType="";//文件类型 分为文件夹类型与文件类型
    private String fileFlag="0";//文件标志 0公开 1私有 2删除 3彻底删除
    private int downloads=0;//下载次数

    @Override
    public String toString() {
        return "FileBaseInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileFlag='" + fileFlag + '\'' +
                ", downloads=" + downloads +
                '}';
    }

    public FileBaseInfo() {
    }

    public FileBaseInfo(int userId, String userName, String uploadTime, String fileName, String fileSize, String fileType) {
        this.userId = userId;
        this.userName = userName;
        this.uploadTime = uploadTime;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
