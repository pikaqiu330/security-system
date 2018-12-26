package com.example.security.domain;


/**
 * @author lixiao
 * entity
 */

public class Media {
    private String name;

    //private String ip;//ip地址

    private String path;//音频路径

    private Integer type;//标识：0音/1视频

    private Integer status;//状态：1异常/0正常

    private Integer refresh;//刷新标识：1刷新、0不刷新

    public static String voicePath_node1 = "E:/voice/voice_node1/";//音频文件路径：node1

    public static String voicePath_node2 = "E:/voice/voice_node2/";//音频文件路径：node2

    public static String voicePath_raw1 = "E:/voice/voice_raw1/";//raw1音频文件路径

    public static String voicePath_raw2 = "E:/voice/voice_raw2/";//raw2音频文件路径

    //public static String videoPath;//视频文件路径

    public static Integer count=0;//若30个文件之中有多个异常则只返回一次

    /*public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", refresh=" + refresh +
                '}';
    }
}
