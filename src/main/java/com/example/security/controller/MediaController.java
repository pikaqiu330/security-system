package com.example.security.controller;

import com.example.security.domain.Media;
import com.example.security.service.MediaService;
import com.example.security.util.LocalUtil;
import com.example.security.util.UploadFIleUtil;
import com.example.security.util.VoiceLinkJNI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * @param file 媒体文件
     * @return
     */
    @RequestMapping(value = "/start_uploaded_wavFile",method = RequestMethod.POST)
    public Media start_uploaded_wavFile(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file,@RequestParam("name") String name){
        String ip = LocalUtil.getRealIp(request);
        Media media = new Media();
        boolean b_jni = false;
        Map<String,String> map = new HashMap<>();
        if(ip.equals("127.0.0.1")){             //根据ip区分上传文件夹路径
            map.put("rawPath",Media.voicePath_raw1);
            map.put("voicePath",Media.voicePath_node1);
        } else{
            map.put("rawPath",Media.voicePath_raw2);
            map.put("voicePath",Media.voicePath_node2);
            }
            String rawName = map.get("rawPath")+name;
            String voicePath = map.get("voicePath")+name;
        if (uploadFIleUtil.uploadFile(file, rawName + ".raw")){
            String[] cmd = {"cmd","/C","ffmpeg -y -i "+rawName+".raw -f wav -ar 16000 -ac 1 -acodec pcm_s16le "+voicePath+".wav"};
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            b_jni = voiceLinkJNI.AnomalyDetectionJNI(voicePath+".wav");
            System.out.println(b_jni);
        }
        return mediaService.Detection(b_jni);
    }


    /**
     * 获取远端音频资源文件名
     * @return map
     */
    @RequestMapping("start_remote_wavFile")
    public List<Media> start_remote_wavFile(HttpServletRequest request, ModelAndView model){
        String path;
        String ip = LocalUtil.getRealIp(request);
        if(ip.equals("127.0.0.1")){
            path = Media.voicePath_node1;
        }else{
            path = Media.voicePath_node1;
        }
        return uploadFIleUtil.getFilePathList(path);
    }

    @RequestMapping("/")
    public boolean hello(){

        return true;
    }

}
