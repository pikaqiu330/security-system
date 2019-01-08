package com.example.security.domain;

/**
 * @author lixiao
 * entity 视频实体
 */
public class Video {

    private Integer threshold;      //当前阈值

    private Integer currentNumber;      //当前人数

    private Integer status;     //warning:1/normal:0

    private String cameraIP;        //增加一个摄像头的IP地址

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCameraIP() {
        return cameraIP;
    }

    public void setCameraIP(String cameraIP) {
        this.cameraIP = cameraIP;
    }

    @Override
    public String toString() {
        return "Video{" +
                "threshold=" + threshold +
                ", currentNumber=" + currentNumber +
                ", status=" + status +
                ", cameraIP='" + cameraIP + '\'' +
                '}';
    }
}
