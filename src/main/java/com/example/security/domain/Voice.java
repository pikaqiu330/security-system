package com.example.security.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lixiao
 * VoiceJNI
 */
@Getter
@Setter
@ToString
public class Voice {

    private Integer anomaly;//1代表异常

    private float anomalyTimeBegin;//异常开始时间

    private float anomalyTimeEnd;//异常结束时间
}
