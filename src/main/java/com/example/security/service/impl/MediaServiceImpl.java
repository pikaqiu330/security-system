package com.example.security.service.impl;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.example.security.config.LoadUserBean;
import com.example.security.domain.Media;
import com.example.security.domain.Video;
import com.example.security.domain.Voice;
import com.example.security.service.MediaService;
import com.example.security.util.UploadFIleUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private UploadFIleUtil uploadFIleUtil;      //上传文件工具类

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
    public Video ReadAsChars(HttpServletRequest request){
        BufferedReader reader;
        String body = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            body = IOUtils.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Video video = null;
        if(body!=null){
            video = new Video();
            JSONObject object = JSONObject.fromObject(body);
            video.setThreshold(object.getInt("threshold"));
            video.setCurrentNumber(object.getInt("currentNumber"));
            video.setStatus(object.getString("status"));
            video.setCameraIP(object.getString("cameraIP"));
        }
        return video;
    }

    @Override
    public void voiceDispose(MultipartFile file, String fileName, Voice mediaVoice) {
        String rawPath = mediaVoice.getRawPath() + fileName;
        String wavPath = mediaVoice.getWavPath() + fileName;
        if (uploadFIleUtil.uploadFile(file, rawPath + ".raw",mediaVoice.getWavPath())) {
            String[] cmd = {"cmd", "/C", "ffmpeg -loglevel quiet -y -i " + rawPath + ".raw -f wav -ar 16000 -ac 1 -acodec pcm_s16le " + wavPath + ".wav"};
            Process process;
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void videoDispose(Video video) {
        if(video.getStatus().equalsIgnoreCase("Warning")){
            for (String in:LoadUserBean.map.keySet()
                 ) {
                Media media = LoadUserBean.map.get(in);
                WebSocketServerImpl socketServer = WebSocketServerImpl.map.get(media.getUser().getIp());
                if(socketServer != null) {
                    if (media.getNvms().equals(1)) {
                        if (media.getUser().getVideoLocalIp().equals(video.getCameraIP())) {
                            //if (media.getVideo().getStatus().equalsIgnoreCase("Normal")) {
                                media.getVideo().setStatus("Warning");
                                socketServer.sendMessage("Warning");
                            //}
                            media.getVideo().setTimestamp(System.currentTimeMillis());
                        }
                    } else {
                        if (media.getUser().getVideoRemoteIp().equals(video.getCameraIP())) {
                            socketServer.sendMessage("Warning");
                            for (String is : LoadUserBean.map.keySet()
                            ) {
                                Media isMedia = LoadUserBean.map.get(is);
                                if (!in.equals(is)) {
                                    isMedia.getVideo().setStatus("Warning");
                                    isMedia.getVideo().setTimestamp(System.currentTimeMillis());
                                }
                            }
                        }
                    }
                }
            }
        }else {
            for (String in:LoadUserBean.map.keySet()
                 ) {
                Media media = LoadUserBean.map.get(in);
                WebSocketServerImpl socketServer = WebSocketServerImpl.map.get(media.getUser().getIp());
                if (media.getNvms().equals(1)) {
                    if(media.getUser().getVideoLocalIp().equals(video.getCameraIP())){
                        long outTime = (System.currentTimeMillis() - media.getVideo().getTimestamp()) / 1000;
                        if(outTime > 30){
                            socketServer.sendMessage(media.getVideo().getStatus());
                        }else {
                            media.getVideo().setIsAnomaly(1);
                        }
                    }
                }else {
                    if(media.getUser().getVideoRemoteIp().equals(video.getCameraIP())){
                        for (String is:LoadUserBean.map.keySet()
                             ) {
                            if(!in.equals(is)){
                                Media isMedia = LoadUserBean.map.get(is);
                                long outTime = (System.currentTimeMillis() - isMedia.getVideo().getTimestamp()) / 1000;
                                if(outTime < 30){
                                    isMedia.getVideo().setIsAnomaly(1);
                                }else {
                                    socketServer.sendMessage(isMedia.getVideo().getStatus());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void videoNvmsSwitch(Media media, String status, Integer nvms) {
        media.setNvms(nvms);
        String ip = media.getUser().getIp();
        WebSocketServerImpl socketServer;
        if(nvms.equals(1)){
            if(!status.equalsIgnoreCase(media.getVideo().getStatus())){
                socketServer = WebSocketServerImpl.map.get(ip);
                socketServer.sendMessage(media.getVideo().getStatus());
            }
        }else {
            for (String in:LoadUserBean.map.keySet()
            ) {
                if(!ip.equals(in)){
                    if(!status.equalsIgnoreCase(LoadUserBean.map.get(in).getVideo().getStatus())){
                        socketServer = WebSocketServerImpl.map.get(ip);
                        socketServer.sendMessage(LoadUserBean.map.get(in).getVideo().getStatus());
                    }
                }
            }
        }
    }

}
