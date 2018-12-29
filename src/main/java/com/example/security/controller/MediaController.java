package com.example.security.controller;

import com.example.security.config.LoadUserBean;
import com.example.security.domain.Media;
import com.example.security.domain.Voice;
import com.example.security.service.MediaService;
import com.example.security.util.LocalUtil;
import com.example.security.util.UploadFIleUtil;
import com.example.security.util.VoiceLinkJNI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
public class MediaController {
    private static final Logger LOG = LoggerFactory.getLogger(MediaController.class);

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
        if(mediaVoice.getNvms() == 0){
            mediaVoice.setNvms(1);
            mediaVoice.setCount(0);
        }
        String rawPath = mediaVoice.getRawPath() + name;
        String wavPath = mediaVoice.getWavPath() + name;
        if (uploadFIleUtil.uploadFile(file, rawPath + ".raw",mediaVoice.getWavPath())) {
            String[] cmd = {"cmd", "/C", "ffmpeg -y -i " + rawPath + ".raw -f wav -ar 16000 -ac 1 -acodec pcm_s16le " + wavPath + ".wav"};
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean b_jni = voiceLinkJNI.AnomalyDetectionJNI(wavPath + ".wav");
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
            if(!in.equals(ip)){
                remotePath = LoadUserBean.map.get(in).getVoice().getWavPath();
            }
         }
        Voice mediaVoice = media.getVoice();
        if(mediaVoice.getNvms() == 1){
            mediaVoice.setNvms(0);
            mediaVoice.setCount(0);
        }
        List<Voice> voiceList = uploadFIleUtil.getVoiceList(remotePath,mediaVoice);
        for (Voice voice:voiceList
             ) {
            System.out.println(voice.getName());
        }
        return voiceList;
    }

    @RequestMapping("/")
    public boolean hello() {

        return true;
    }

}
