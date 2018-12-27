package com.example.security.domain;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiao
 * entity
 */

public class Media {

    private static String ip;

    private String path;//音频路径

    private Integer type;//标识：0音/1视频

    private Integer status;//状态：1异常/0正常

    private Integer refresh;//刷新标识：1刷新、0不刷新

    public  String voicePath_node1 = "E:/voice/voice_node1/";//音频文件路径：node1

    public  String voicePath_node2 = "E:/voice/voice_node2/";//音频文件路径：node2

    public  String voicePath_raw1 = "E:/voice/voice_raw1/";//raw1音频文件路径

    public  String voicePath_raw2 = "E:/voice/voice_raw2/";//raw2音频文件路径

    //public static String videoPath;//视频文件路径

    public Integer count=0;//若30个文件之中有多个异常则只返回一次

    public static Map<String,Media> map = new ConcurrentHashMap<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getVoicePath_node1() {
        return voicePath_node1;
    }

    public void setVoicePath_node1(String voicePath_node1) {
        this.voicePath_node1 = voicePath_node1;
    }

    public String getVoicePath_node2() {
        return voicePath_node2;
    }

    public void setVoicePath_node2(String voicePath_node2) {
        this.voicePath_node2 = voicePath_node2;
    }

    public String getVoicePath_raw1() {
        return voicePath_raw1;
    }

    public void setVoicePath_raw1(String voicePath_raw1) {
        this.voicePath_raw1 = voicePath_raw1;
    }

    public String getVoicePath_raw2() {
        return voicePath_raw2;
    }

    public void setVoicePath_raw2(String voicePath_raw2) {
        this.voicePath_raw2 = voicePath_raw2;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Media{" +
                "path='" + path + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", refresh=" + refresh +
                '}';
    }
}
