package com.example.security.domain;

/**
 * @author lixiao
 * entity 音频实体
 */
public class Voice {

    private Integer nvms;       //监控端：1本端/0远端

    private String wavPath;     //wav路径

    private String rawPath;     //raw路径

    private Integer status;     //状态：1异常/0正常

    private Integer refresh;    //刷新标识：1刷新/0不刷新

    private String name;        //文件名称    注：用来返回给前端拼接路径

    private int count;          //累计正常音，满20个刷回正常状态

    private int isAnomaly;      //是否开始累计正常音标识：1累计/0不累计

    public String getWavPath() {
        return wavPath;
    }

    public void setWavPath(String wavPath) {
        this.wavPath = wavPath;
    }

    public String getRawPath() {
        return rawPath;
    }

    public void setRawPath(String rawPath) {
        this.rawPath = rawPath;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIsAnomaly() {
        return isAnomaly;
    }

    public void setIsAnomaly(int isAnomaly) {
        this.isAnomaly = isAnomaly;
    }

    public Integer getNvms() {
        return nvms;
    }

    public void setNvms(Integer nvms) {
        this.nvms = nvms;
    }

    @Override
    public String toString() {
        return "Voice{" +
                "nvms=" + nvms +
                ", wavPath='" + wavPath + '\'' +
                ", rawPath='" + rawPath + '\'' +
                ", status=" + status +
                ", refresh=" + refresh +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", isAnomaly=" + isAnomaly +
                '}';
    }
}
