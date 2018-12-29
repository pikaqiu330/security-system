package com.example.security.config;

import com.example.security.domain.Media;
import com.example.security.domain.User;
import com.example.security.domain.Voice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoadUserBean implements CommandLineRunner {

    public static Map<String,Media> map = new ConcurrentHashMap<>();

    @Value(value = "classpath:json/user.json")
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        String json = null;
        try {
            File resourceFile = resource.getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resourceFile)));
            StringBuilder sb = new StringBuilder();
            String value;
            while ((value = br.readLine()) != null){
                sb.append(value);
            }
            br.close();
            json = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(json);
        if(!jsonObject.isEmpty()){
            JSONArray userList = jsonObject.getJSONArray("userList");
            Media media;
            User user;
            Voice voice;
            for(int i = 0;i < userList.size();i++){
                media = new Media();
                user = new User();
                voice = new Voice();
                JSONObject userJsonObject = userList.getJSONObject(i);
                user.setName(userJsonObject.getString("name"));
                user.setIp(userJsonObject.getString("ip"));
                user.setVideoLocalIp(userJsonObject.getString("videoLocalIp"));
                user.setVideoRemoteIp(userJsonObject.getString("videoRemoteIp"));
                voice.setRawPath(userJsonObject.getString("rawPath"));
                voice.setWavPath(userJsonObject.getString("wavPath"));
                voice.setNvms(0);
                media.setUser(user);
                media.setVoice(voice);
                map.put(user.getIp(),media);
            }
        }
    }
}
