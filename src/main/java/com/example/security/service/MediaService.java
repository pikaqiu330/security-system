package com.example.security.service;

import com.example.security.domain.Media;

public interface MediaService {

    /**
     * 设定前端是否需要刷新和其他属性值
     * @param b_jni 检测是否异常
     * @return 一个音频实例
     */
    Media Detection(Boolean b_jni);
}
