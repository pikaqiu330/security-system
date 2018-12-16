package com.example.security.controller;

import com.example.security.service.VoiceService;
import com.example.security.util.UploadFIleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class VoiceController {
    private static final Logger LOG = LoggerFactory.getLogger(VoiceController.class);

    @Autowired
    private VoiceService voiceService;

    @Autowired
    private UploadFIleUtil uploadFIleUtil;      //上传文件工具类



    /**
     * 实时收音上传并且检测
     * @param file
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/start_uploaded_wavFile",method = RequestMethod.POST)
    public boolean start_uploaded_wavFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "fileName") String fileName){
        String filePath = "/Users/lixiao/IdeaProjects/dirVoice/";
        boolean b = uploadFIleUtil.uploadFile(filePath,file,fileName);              //上传音频文件
        return b;
    }



    @RequestMapping(value = "/startJNI")
    public String startJNI(@RequestParam(value = "txtUrl") String txtUrl,@RequestParam(value = "voiceDirPath") String voiceDirPath){
        //String txtUrl = "E:/wavfile/detect.txt";
        //String voiceDirPath = "D:/XAMPP/htdocs/web_Record_func/upload/new_wav";
        voiceService.checkAnomalyToLinkJni(txtUrl,voiceDirPath);
        return "startSuccessful";
    }


    /**
     * 终止JNI程序
     * @return
     */
    @RequestMapping(value = "/close_uploaded_wavFile")
    public void close_uploaded_wavFile(){


    }


    @RequestMapping("/")
    public boolean hello(){

        return true;
    }

}
