package com.rachein.mmzf.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description 将MultipartFile 转换为 File类
 */
public class MultipartToFileUtil {

    public static File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件后缀：
            String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
            file = File.createTempFile(originalFilename, prefix);
            multipartFile.transferTo(file);
            //删除
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
