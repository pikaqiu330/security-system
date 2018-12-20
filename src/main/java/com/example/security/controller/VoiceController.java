package com.example.security.controller;

import com.example.security.domain.Constant;
import com.example.security.service.VoiceService;
import com.example.security.util.LocalUtil;
import com.example.security.util.UploadFIleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


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
    public boolean start_uploaded_wavFile(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "fileName") String fileName){
        String ip = LocalUtil.getRealIp(request);
        if(ip.equals("127.0.0.1"))
            return uploadFIleUtil.uploadFile(Constant.FILEPATH_LOCAL,file,fileName);              //上传音频文件
        else
            return uploadFIleUtil.uploadFile(Constant.FILEPATH_REMOTE,file,fileName);
    }


    /**
     * 获取远端音频资源文件名
     * @return map
     */
    @RequestMapping("start_remote_wavFile")
    public Map<String,Object> start_remote_wavFile(){
        Map<String,Object> map = new HashMap<>();
        map.put("filePathList",uploadFIleUtil.getFilePathList(Constant.FILEPATH_LOCAL));
        return map;
    }


    @RequestMapping(value = "/startJNI")
    public String startJNI(@RequestParam(value = "txtUrl") String txtUrl,@RequestParam(value = "voiceDirPath") String voiceDirPath){
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
