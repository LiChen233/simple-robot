package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class EquipsUP {

    private static final String HIGHUP = "https://hsod2.hongshn.xyz/list/auto/high";
    private static final String CUSTOMUP = "https://hsod2.hongshn.xyz/list/auto/custom";
    private static final String SPECIALUP = "https://hsod2.hongshn.xyz/list/auto/special";
    private static final String PETUP = "https://hsod2.hongshn.xyz/list/auto/pet";
    private static final String PETMAP = "https://hsod2.hongshn.xyz/list/auto/pet-map";
    private static final String[] ALL = {HIGHUP,CUSTOMUP,SPECIALUP,PETUP,PETMAP};
    public static final String[] NAME = {"HighUp","CustomUp","SpecialUp","PetUp","PetMap"};

    public static ArrayList<String> getAllUp(String up,String equip){
        String path = "src/static/"+up+".txt";
        String result = ReadJsonFileUtils.readJson(path);
        ArrayList<String > allUp = new ArrayList<>();

        JSONArray jsonArr = JSON.parseArray(result);
        for (Object o : jsonArr) {
            JSONObject json = (JSONObject)o;
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                String item = (String) data.get(i);
                if (item.indexOf(equip)!=-1){
                    String startTime = json.getString("startTime").substring(0,10);
                    if (allUp.size()==0 || allUp.size()==1 || !allUp.get(allUp.size()-1).substring(0,7).equals(startTime.substring(0,7))){
                        allUp.add(startTime);
                    }
                }
            }
        }

        return allUp;
    }

    public static String getPet (String pet){
        String path = "src/static/PetMap.txt";
        String result = ReadJsonFileUtils.readJson(path);
        JSONObject json = JSON.parseObject(result);
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String value = (String) entry.getValue();
            if (value.indexOf(pet)!=-1){
                return entry.getKey();
            }
        }
        return null;
    }

    public static void flushJson(){
        Document doc;
        for (int i = 0; i < ALL.length; i++) {
            try {
                doc = Jsoup.connect(ALL[i]).header("User-Agent",
                        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                        .ignoreContentType(true)
                        .timeout(60000)
                        .get();

                String result = doc.select("body").text();
                //写入文件，默认文件永久存在故不做异常处理，写入模式：覆盖
                File jsonFile = new File("src/static/"+NAME[i]+".txt");
                FileOutputStream fos = new FileOutputStream(jsonFile);
                fos.write(result.getBytes(StandardCharsets.UTF_8));
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
