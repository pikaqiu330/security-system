package com.example.security.controller;

import com.example.security.config.LoadUserBean;
import com.example.security.domain.Media;
import com.example.security.domain.User;
import com.example.security.domain.Video;
import com.example.security.domain.Voice;
import com.example.security.service.MediaService;
import com.example.security.util.LocalUtil;
import com.example.security.util.UploadFIleUtil;
import com.example.security.util.VoiceLinkJNI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MediaController {
    @Autowired
    private UploadFIleUtil uploadFIleUtil;      //上传文件工具类

    @Autowired
    private MediaService mediaService;

    @Autowired
    private VoiceLinkJNI voiceLinkJNI;

    /**
     * 实时收音上传并且检测
     *
     * @param file 媒体文件
     * @return
     */
    @RequestMapping(value = "/start_uploaded_wavFile", method = RequestMethod.POST)
    public Voice start_uploaded_wavFile(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file, @RequestParam("name") String name) {
        String ip = LocalUtil.getRealIp(request);
        Media media = LoadUserBean.map.get(ip);
        Voice mediaVoice = media.getVoice();
        if(media.getNvms() == 0){      //若监控端在远端收音则修改标识为本端录音并且累计变量count归零
            media.setNvms(1);
            mediaVoice.setCount(0);
        }
        String wavPath = mediaVoice.getWavPath() + name;
        mediaService.voiceDispose(file,name,mediaVoice);        //上传音频并转换wav
        boolean b_jni = voiceLinkJNI.AnomalyDetectionJNI(wavPath + ".wav");     //调用c++算法
        System.out.println(b_jni);
        mediaVoice.setName(name);
        return mediaService.Detection(b_jni,mediaVoice);
    }


    /**
     * 获取远端音频资源文件名
     *
     * @return map
     */
    @RequestMapping("start_remote_wavFile")
    public List<Voice> start_remote_wavFile(HttpServletRequest request) {
        String ip = LocalUtil.getRealIp(request);
        Media media = LoadUserBean.map.get(ip);
        String remotePath = null;
        for (String in : LoadUserBean.map.keySet()) {
             //map.keySet()返回的是所有key的值
            if(!in.equals(ip)){     //获取到对端的音频路径
                remotePath = LoadUserBean.map.get(in).getVoice().getWavPath();
            }
         }
        Voice mediaVoice = media.getVoice();
        if(media.getNvms() == 1){  //若监控端在本端录音则修改标识为远端收音并且累计变量count归零
            media.setNvms(0);
            mediaVoice.setCount(0);
        }
        return uploadFIleUtil.getVoiceList(remotePath,mediaVoice);
    }

    @RequestMapping("initialize")
    public User initialize(HttpServletRequest request) {
        String ip = LocalUtil.getRealIp(request);
        Media media = LoadUserBean.map.get(ip);
        //User user = null;
        //if(media != null){
            Voice voice = media.getVoice();
            voice.setCount(0);
            voice.setIsAnomaly(0);
            //user = media.getUser();
        //}
        return media.getUser();
    }

    @RequestMapping(value = "onap-ai/report-status",method = RequestMethod.POST)
    public void CallBackRequest(HttpServletRequest request){
        Video video = mediaService.ReadAsChars(request);
        mediaService.videoDispose(video);
    }

    @RequestMapping(value = "switchLocalRemote")
    public void switchLocalRemote(HttpServletRequest request,@RequestParam("status") String status,@RequestParam("nvms") Integer nvms){
        String ip = LocalUtil.getRealIp(request);
        Media media = LoadUserBean.map.get(ip);
        media.setNvms(nvms);
        mediaService.videoNvmsSwitch(media,status);
    }
}
