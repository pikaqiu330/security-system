package com.example.security.domain;

/**
 * @author lixiao
 * entity 视频实体
 */
public class Video {

    private Integer threshold;      //当前阈值

    private Integer currentNumber;      //当前人数

    private String status;     //warning/normal

    private String cameraIP;        //增加一个摄像头的IP地址

    private long timestamp;     //时间戳

    private Integer isAnomaly;      //1：接收到了正常告警、0：没有

    public Integer getIsAnomaly() {
        return isAnomaly;
    }

    public void setIsAnomaly(Integer isAnomaly) {
        this.isAnomaly = isAnomaly;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
                ", status='" + status + '\'' +
                ", cameraIP='" + cameraIP + '\'' +
                ", timestamp=" + timestamp +
                ", isAnomaly=" + isAnomaly +
                '}';
    }
}
