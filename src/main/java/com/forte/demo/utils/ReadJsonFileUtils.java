package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class ReadJsonFileUtils {

    public static String readJson(String path){
        File jsonFile = new File(path);
        try {
            //读取文件中的数据
            FileInputStream fis = new FileInputStream(jsonFile);
            int len;
            byte[] buffer = new byte[1024];
            StringBuilder pray = new StringBuilder();
            while ((len=fis.read(buffer))!=-1){
                pray.append(new String(buffer,0,len, StandardCharsets.UTF_8));
            }
            fis.close();
            return pray.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        //转成对象
    }
}
