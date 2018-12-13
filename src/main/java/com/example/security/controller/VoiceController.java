package com.example.security.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VoiceController {

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


}
