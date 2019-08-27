package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.plusutils.consoleplus.ConsolePlus;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import com.forte.qqrobot.log.QQLog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyTest {

    //设置APPID/APP_KEY
    public static final String APP_ID = "2121061546";
    public static final String APP_KEY = "hTAA8RaUJj0qipEA";
    public static final String GAO = "https://hsod2.hongshn.xyz/gacha/data";

    /**
     * 获取每种蛋池数据，并写入文件中
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        //使用Jsoup获取搞事学园的祈愿概率json，并写入文件中，以免更换接口导致祈愿不能使用
        Document doc = Jsoup.connect(GAO).header("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .ignoreContentType(true)
                .timeout(60000)
                .get();
        String result = doc.select("body").text();

        //写入文件，默认文件永久存在故不做异常处理，写入模式：覆盖
        File jsonFile = new File("src/static/PrayJson.txt");
        FileOutputStream fos = new FileOutputStream(jsonFile);
        fos.write(result.getBytes(StandardCharsets.UTF_8));
        fos.flush();
        fos.close();

        //读取文件中的数据
        FileInputStream fis = new FileInputStream(jsonFile);
        int len;
        byte[] buffer = new byte[1024];
        StringBuilder pray = new StringBuilder();
        while ((len=fis.read(buffer))!=-1){
            pray.append(new String(buffer,0,len, StandardCharsets.UTF_8));
        }
        fis.close();
        System.out.println("从文本中读取json："+pray);


        //转成对象以备使用
        JSONObject json = JSON.parseObject(pray.toString());
        System.out.println("将字符串转成json："+json.toString());

    }

    /**
     * 祈愿
     */
    @Test
    public void gacha() throws IOException {

        File jsonFile = new File("src/static/PrayJson.txt");
        //读取文件中的数据
        FileInputStream fis = new FileInputStream(jsonFile);
        int len;
        byte[] buffer = new byte[1024];
        StringBuilder pray = new StringBuilder();
        while ((len=fis.read(buffer))!=-1){
            pray.append(new String(buffer,0,len, StandardCharsets.UTF_8));
        }
        fis.close();

        //转成json对象
        JSONObject json = JSON.parseObject(pray.toString());
        JSONObject high = (JSONObject) json.get("high");

        //保底，默认为第10发
        Integer baodi = 10;

        /**
         * 这里开始扭蛋！！！
         */
        for (int i = 0; i < 10; i++) {
            Map<String, String> gacha = null;
            if (baodi == 1){
                gacha= gachaOne(high, true);
            }else {
                gacha= gachaOne(high, false);
            }
            //抽到的装备
            String equip = gacha.get("equip");
            //判断是否出金，抽到金就重置保底
            if("true".equals(gacha.get("baodi"))){
                baodi = 10;

                //文字变金色
                ColorsBuilder builder = Colors.builder();
                Colors colorsEquip = builder
                        .add(equip, Colors.FONT.YELLOW)
                        .build();
                System.out.println(colorsEquip);
            }else{
                baodi--;

                //这里搞个没用的判断，到时候删
                //判断几星
                if ("5".equals(equip.substring(equip.length()-3,equip.length()-2))){
                    //文字变紫色
                    ColorsBuilder builder = Colors.builder();
                    Colors colorsEquip = builder
                            .add(equip, Colors.FONT.PURPLE)
                            .build();
                    System.out.println(colorsEquip);
                }else {
                    //文字变蓝色
                    ColorsBuilder builder = Colors.builder();
                    Colors colorsEquip = builder
                            .add(equip, Colors.FONT.BLUE)
                            .build();
                    System.out.println(colorsEquip);
                }
            }
        }
    }

    /**
     * 传入当前蛋池，进行一次单抽
     * @param high 当前蛋池的json数据
     * @param baodi 当前是否为保底，抽到金就重置，下次区间也重置，防止保底位移
     * @return
     */
    public Map<String,String> gachaOne(JSONObject high, Boolean baodi){

        //返回抽中的装备，以及是否出金，以便后面判断
        Map<String,String> result = new HashMap();

        //获取扭蛋总概率
        Integer total = total = (Integer) high.get("total");
        //获取神器概率区间
        Integer god = (Integer) high.get("god");

        Integer myRate = 0;

        if (baodi){
            //保底了,就从神器区间生成随机数
            myRate = new Random().nextInt(god)+1;
        }else {
            //生成随机数
            myRate = new Random().nextInt(total)+1;
        }

        //拿到装备数组
        JSONArray equips = high.getJSONArray("equips");

        //判断随机数小于那个装备区间，就是抽中这个装备了，直接返回即可
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
                    result.put("baodi","true");
                }
                return result;
            }
        }
        return result;
    }

    /**
     * 彩色控制台
     * @throws UnsupportedEncodingException
     * @throws IllegalAccessException
     */
    @Test
    public void testColor() throws UnsupportedEncodingException, IllegalAccessException {
        ColorsBuilder builder = Colors.builder();
        Colors colors = builder.add("123456789")
                .add("金光", Colors.FONT.YELLOW)
                .add("紫光",Colors.FONT.PURPLE)
                .add("蓝光",Colors.FONT.BLUE)
                .build();
        System.out.println(colors);

        //这个方法是直接覆盖系统自带的输出
        ConsolePlus.updatePrintOut(s -> ColorsBuilder.getInstance().add(s, Colors.FONT.YELLOW).build().toString());
        System.out.println("颜色!");

    }
}