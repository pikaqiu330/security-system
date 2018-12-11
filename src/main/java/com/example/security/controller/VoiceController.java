package com.example.security.controller;

import com.example.security.domain.Voice;
import com.example.security.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoiceController {

    @Autowired
    private VoiceService voiceService;


    /**
     * 返回三种异常音数据
     * @param voice_id
     * @return
     */
    //@CrossOrigin(origins="*") //配置跨域请求
    @RequestMapping(value = "getVoice",method = RequestMethod.GET)
    public Voice getVoice(@RequestParam(value = "voice_id",required = true) String voice_id){
        if (voice_id.isEmpty()){
            return null;
        }
        Voice voice = voiceService.getVoice(voice_id);
        return voice;
    }
}
