package com.example.security.controller;

import com.example.security.domain.Media;
import com.example.security.service.MediaService;
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
    public Media start_uploaded_wavFile(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file, @RequestParam("name") String name, @RequestParam("clientId") String key) {
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getLocalAddr());
        Media media = Media.map.get(key);
        if (media == null) {
            media = new Media();
            if (Media.map.size() > 0) {
                media.setVoicePath_raw1("E:/voice/voice_raw1/");
                media.setVoicePath_node1("E:/voice/voice_node1/");
            } else {
                media.setVoicePath_raw1("E:/voice/voice_raw2/");
                media.setVoicePath_node1("E:/voice/voice_node2/");
            }
            media.setCount(0);
            Media.map.put(key, media);
        }
        boolean b_jni = false;
        String rawName = media.getVoicePath_raw1() + name;
        String voicePath = media.getVoicePath_node1() + name;
        if (uploadFIleUtil.uploadFile(file, rawName + ".raw")) {
            String[] cmd = {"cmd", "/C", "ffmpeg -y -i " + rawName + ".raw -f wav -ar 16000 -ac 1 -acodec pcm_s16le " + voicePath + ".wav"};
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            b_jni = voiceLinkJNI.AnomalyDetectionJNI(voicePath + ".wav");
            System.out.println(b_jni);
        }
        return mediaService.Detection(b_jni,media);
    }


    /**
     * 获取远端音频资源文件名
     *
     * @return map
     */
    @RequestMapping("start_remote_wavFile")
    public List<Media> start_remote_wavFile(@RequestParam("clientId") String key) {
        Media media = Media.map.get(key);
        List<Media> filePathList = uploadFIleUtil.getFilePathList(media.getVoicePath_node1(), media);
        for (Media media1:filePathList
             ) {
            System.out.println(media.getPath());
        }
        return filePathList;
    }

    @RequestMapping("/")
    public boolean hello() {

        return true;
    }

}
