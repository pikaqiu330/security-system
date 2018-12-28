package com.example.security.util;

import com.example.security.domain.Media;
import com.example.security.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author lixiao
 * 操作文件工具类
 */
@Component
public class UploadFIleUtil {

    @Autowired
    private VoiceLinkJNI voiceLinkJNI;

    @Autowired
    private MediaService mediaService;

    private static final Logger LOG = LoggerFactory.getLogger(UploadFIleUtil.class);

    /**
     * 上传音频文件
     * @param file 音频文件
     * @param filePath 音频文件路径
     * @return true or false
     */
    public boolean uploadFile(MultipartFile file,String filePath){
        try {
            if(file.isEmpty()){
                return false;
            }
            File dest = new File(filePath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                LOG.info("新建文件夹...");
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            LOG.info(dest.getName() + "文件开始写入...");
            file.transferTo(dest);// 文件写入
            LOG.info("upload successful");
            return true;
        } catch (IOException e) {
            LOG.error("上传文件失败！");
            e.printStackTrace();
        }
        LOG.info("upload failure");
        return false;
    }


    /**
     *  根据路径删除指定的目录，无论存在与否
     *@param sPath  要删除的目录path
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }


    /**
     * 删除目录以及目录下的文件
     * @param   sPath 被删除目录的路径
     * @return  目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if(files == null || files.length < 7){
            return false;
        }
        for (int i = 0; i < files.length-6; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        return dirFile.delete();
    }

    /**
     * 删除单个文件
     * @param   sPath 被删除文件path
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 获取本地音频文件资源名称
     * @param path 本地资源路径
     * @return 音频文件名集合
     */
    public List<Media> getFilePathList(String path,Media media){
        List<Media> mediaList = null;
        File dest = new File(path);
        File[] listFiles = dest.listFiles();
        if(listFiles!=null){
            Arrays.sort(listFiles, new Comparator<File>() {
                @Override
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            if (listFiles.length > 6){
                File[] files = new File[6];
                int num = 0;
                for (int i = listFiles.length - 6;i < listFiles.length; i++){
                    files[num++] = listFiles[i];
                }
                listFiles = files;
            }
            mediaList = new ArrayList<>();
            for (File file:listFiles
            ) {
                boolean b_jni = voiceLinkJNI.AnomalyDetectionJNI(path + file.getName());
                Media detection = mediaService.Detection(b_jni,media);
                detection.setPath(file.getName());
                mediaList.add(detection);
            }
        }
        return mediaList;
    }

}
