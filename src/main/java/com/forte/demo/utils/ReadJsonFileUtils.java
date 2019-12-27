package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadJsonFileUtils {

    public static String readJson(String path){
        File jsonFile = new File(path);
        try {
            //读取文件中的数据
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(jsonFile)));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line=bf.readLine())!=null){
                stringBuilder.append(line);
            }
            bf.close();
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
