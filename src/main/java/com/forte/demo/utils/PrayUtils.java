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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 功能：
 * 获取所有扭蛋json并写入文件
 * 提供其中之一的扭蛋json
 * 提供扭蛋功能，返回状态信息以便其他类进行数据库操作
 * @author li
 */
public class PrayUtils {

    private static final String GAO = "https://hsod2.hongshn.xyz/gacha/data";

    public static boolean findSpecial(){
        JSONObject jsonString = null;
        try {
            jsonString = getJsonString(PrayEnum.special);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray equips = (JSONArray) jsonString.get("equips");
        if (equips.size()==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 该方法用于刷新扭蛋json文件
     * @throws IOException 异常啥的，到时候再捕获吧，不应该抛的
     */
    public static void flushJson() throws IOException {
        //使用Jsoup获取搞事学园的祈愿概率json，并写入文件中，以免更换接口导致祈愿不能使用
        Document doc = Jsoup.connect(GAO).header("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .ignoreContentType(true)
                .timeout(60000)
                .get();
        String result = doc.select("body").text();

        //若是json拿不到，就返回，以免覆写文件
        if ("".equals(result.trim())){
            return;
        }

        //写入文件，默认文件永久存在故不做异常处理，写入模式：覆盖
        File jsonFile = new File("src/static/PrayJson.txt");
        FileOutputStream fos = new FileOutputStream(jsonFile);
        fos.write(result.getBytes(StandardCharsets.UTF_8));
        fos.flush();
        fos.close();
    }

    /**
     * 传入蛋池名字，蛋池名字从枚举类中拿，拿到文件中的蛋池json
     * @param name 蛋池名字
     * @return 返回Json对象
     * @throws IOException 再说
     */
    public static JSONObject getJsonString(PrayEnum name) throws IOException {
        String path = "src/static/PrayJson.txt";
        String result = ReadJsonFileUtils.readJson(path);
        JSONObject json = JSON.parseObject(result);
        return (JSONObject) json.get(name.toString());
    }

    /**
     * 传入要抽的蛋池，进行一次单抽
     * @param prayJson 当前蛋池的json数据
     * @param baodi 当前是否为保底，抽到金就重置，下次区间也重置，防止保底位移
     * @return 返回带有Map，内有装备名字，是否是金
     */
    public static Map<String,String> gacha(JSONObject prayJson, Boolean baodi){
        //返回抽中的装备，以及是否出金，以便后面判断
        Map<String,String> result = new HashMap<>();
        //获取扭蛋总概率
        Integer total = (Integer) prayJson.get("total");
        //获取神器概率区间
        Integer god = (Integer) prayJson.get("god");

        Integer myRate;

        if (baodi && 0!=god){
            //保底了,就从神器区间生成随机数
            myRate = new Random().nextInt(god)+1;
        }else {
            //生成随机数
            myRate = new Random().nextInt(total)+1;
        }

        //拿到装备数组
        JSONArray equips = prayJson.getJSONArray("equips");

        //判断随机数小于那个装备区间，就是抽中这个装备了
        for (int i = 0; i < equips.size(); i++) {
            //获取当前装备的json
            JSONObject equip = equips.getJSONObject(i);
            //当前装备的抽奖概率区间
            Integer equipRate = (Integer)equip.get("rate");
            if (equipRate>=myRate){
                //把装备名字加进去
                result.put("equip",(String) equip.get("name"));
                //如果随机数在神器区间内，就是出金了
                if (myRate<=god){
                    result.put("gold","true");
                }
                return result;
            }
        }
        return result;
    }

}
