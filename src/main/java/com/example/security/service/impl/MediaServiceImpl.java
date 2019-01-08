package com.example.security.service.impl;
import com.example.security.domain.Voice;
import com.example.security.service.MediaService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class MediaServiceImpl implements MediaService {

    @Override
    public Voice Detection(Boolean b_jni, Voice voice) {
        if (b_jni){
            if (voice.getIsAnomaly() == 1){
                voice.setCount(0);
                voice.setRefresh(0);
            }else {
                voice.setIsAnomaly(1);
                voice.setRefresh(1);
            }
            voice.setIsAnomaly(1);    //出现异常音
            voice.setStatus(1);
        }else {
            if (voice.getIsAnomaly() == 1){   //前面出现异常音
                voice.setCount(voice.getCount() + 1);
                if (voice.getCount() == 10){     //连续出现10个正常音刷回正常状态
                    voice.setRefresh(1);
                    voice.setIsAnomaly(0);
                    voice.setCount(0);  //累计正常音数量归零
                }else {
                    voice.setRefresh(0);
                }
            }else{
                voice.setRefresh(0);
            }
            voice.setStatus(0);
        }
        return voice;
    }

    @Override
    public void clearProcess(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            new Thread(() -> {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try
                {
                    while((line = in.readLine()) != null)
                    {
                        System.out.println("output: " + line);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        in.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = null;

                try
                {
                    while((line = err.readLine()) != null)
                    {
                        System.out.println("err: " + line);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        err.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
