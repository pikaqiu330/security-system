package com.example.security.service;
import com.example.security.domain.Video;
import com.example.security.domain.Voice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface MediaService {

    /**
     * 设定前端是否需要刷新和其他属性值
     * @param b_jni 检测是否异常
     * @return 一个音频实例
     */
    Voice Detection(Boolean b_jni, Voice voice);

    Video ReadAsChars(HttpServletRequest request);

    /**
     * 上传音频并转换wav
     * @param file 文件
     * @param fileName 文件名
     * @param mediaVoice voice对象
     */
    void voiceDispose(MultipartFile file, String fileName, Voice mediaVoice);
}
