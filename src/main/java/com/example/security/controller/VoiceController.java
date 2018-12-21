package com.example.security.controller;

import com.example.security.domain.Constant;
import com.example.security.util.LocalUtil;
import com.example.security.util.UploadFIleUtil;
import com.example.security.util.VoiceLinkJNI;
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
    private UploadFIleUtil uploadFIleUtil;      //上传文件工具类

    @Autowired
    private VoiceLinkJNI voiceLinkJNI;


    /**
     * 实时收音上传并且检测
     * @param file
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/start_uploaded_wavFile",method = RequestMethod.POST)
    public boolean start_uploaded_wavFile(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "fileName") String fileName){
        String ip = LocalUtil.getRealIp(request);
        boolean b_jni = false;
        if(ip.equals("127.0.0.1")){
            b_jni = uploadFIleUtil.uploadFile(Constant.FILEPATH_LOCAL,file,fileName);
            if(b_jni){
                b_jni = voiceLinkJNI.AnomalyDetectionJNI(Constant.FILEPATH_LOCAL + fileName);
            }            //上传音频文件
        } else{
                if(uploadFIleUtil.uploadFile(Constant.FILEPATH_REMOTE,file,fileName)){
                    b_jni = voiceLinkJNI.AnomalyDetectionJNI(Constant.FILEPATH_REMOTE + fileName);
                }
            }
        System.out.println(b_jni);
        return b_jni;
    }


    /**
     * 获取远端音频资源文件名
     * @return map
     */
    @RequestMapping("start_remote_wavFile")
    public Map<String,Object> start_remote_wavFile(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String ip = LocalUtil.getRealIp(request);
        if(ip.equals("127.0.0.1")){
            map.put("filePathList",uploadFIleUtil.getFilePathList(Constant.FILEPATH_REMOTE));
        }else{
            map.put("filePathList",uploadFIleUtil.getFilePathList(Constant.FILEPATH_REMOTE));
        }
        return map;
    }




    @RequestMapping("/")
    public boolean hello(){

        return true;
    }

}
