package com.example.security.controller;

import com.example.security.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    @RequestMapping("uploaded_wavFile")
    public int uploaded_wavFile(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            return 0;
        }
        System.out.print(file);
        return 0;
    }

    @RequestMapping("/")
    public String hello(){

        return "HelloWorld";
    }

    @RequestMapping("startJNI")
    public String startJNI(@RequestParam(value = "txtUrl") String txtUrl,@RequestParam(value = "voiceDirPath") String voiceDirPath){
        //String txtUrl = "E:/wavfile/detect.txt";
        //String voiceDirPath = "D:/XAMPP/htdocs/web_Record_func/upload/new_wav";
        voiceService.checkAnomalyToLinkJni(txtUrl,voiceDirPath);
        return "startSuccessful";
    }

}
