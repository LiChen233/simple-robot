package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class FindEquipsUtils {

    private static final Integer width = 762;
    private static final Integer height = 1294;
    //装备图标url
    private static final String eqImgUrl = "https://static.image.mihoyo.com/hsod2_webview/images/broadcast_top/equip_icon/png/";
    //系列图标url
    private static final String series = "https://hsod2.hongshn.xyz/images/icons/SeriesNo/";
    //伤害类型url
    private static final String type = "https://hsod2.hongshn.xyz/images/icons/Type/";
    //装备星级
    private static final String star = "https://hsod2.hongshn.xyz/images/star-full.png";

    private static final String OUT = "./temp/";
    private static final Integer SIZE = 28;
    private static final String suffix = ".png";

    public static void synthesis(String equipsId) throws IOException {
        //设置画布
        BufferedImage thumbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //设置画板
        Graphics2D g = thumbImage.createGraphics();
        //背景设置白色
        g.setBackground(Color.WHITE);
        //填充背景色
        g.clearRect(0, 0, width, height);
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置字体
        g.setColor(Color.GRAY);
        g.setFont(new Font("微软雅黑",Font.BOLD,SIZE));

        //获取装备json
        JSONObject equip = getEquip(equipsId);
        //图片缓存list
        ArrayList<String> icons = new ArrayList<>();

        //获取装备图标地址
        String img = equip.getString("img");
        if (img.length()==1){
            img="00"+img;
        }else if (img.length()==2){
            img="0"+img;
        }
        String eqiconPath = eqImgUrl + img +suffix;
        icons.add(eqiconPath);
        //拿到图标
        BufferedImage eqicon = ImageIO.read(new URL(eqiconPath));
        //装备图标
        g.drawImage(eqicon,32,56,eqicon.getWidth(),eqicon.getHeight(),null);

        //插入装备id
        g.drawString("No."+equip.getString("id"),170,58+SIZE);

        //系列图标
        String seriesPath = series + equip.getString("seriesId") + suffix;
        icons.add(seriesPath);
        BufferedImage sericon = ImageIO.read(new URL(seriesPath));
        g.drawImage(sericon,290,50,sericon.getWidth(),sericon.getHeight(),null);

        //装备名称
        g.drawString(equip.getString("title"),340,58+SIZE);

        //伤害类型
        String damageType = equip.getString("damageType");
        if (null!=damageType){
            damageType = type + damageType + suffix;
            icons.add(damageType);
            BufferedImage damageTypeIcon = ImageIO.read(new URL(damageType));
            g.drawImage(damageTypeIcon,170,95,32,32,null);
        }


        //装备星级
        icons.add(star);
        Integer rarity = equip.getInteger("rarity");
        BufferedImage rarityIcon = ImageIO.read(new URL(star));
        Integer starWidth = 205;
        for (Integer i = 0; i < rarity; i++) {
            g.drawImage(rarityIcon,starWidth,96,29,29,null);
            starWidth+=29;
        }

        //装备是否可拆解
        Boolean decompose = equip.getBoolean("decompose");
        String decomposeremark = "* 该装备可拆解 *";
        if (null==decompose){
            decomposeremark = "* 该装备是地图装 *";
        }
        g.drawString(decomposeremark,170,145+SIZE);

        /**
         * 开始装备参数
         */
        Integer[] paramW = {50,400};
        Integer paramH = 205+SIZE;
        Integer lineH = 45;
        Integer Pindex = 0;
        ArrayList<String> params = getParam(equip);
        for (String param : params) {
            g.drawString(param,paramW[Pindex],paramH);
            Pindex++;
            if (Pindex==2){
                Pindex=0;
                paramH+=lineH;
            }
        }

        /**
         * 一技能
         */
        JSONObject prop1 = equip.getJSONObject("prop1");
        if (null!=prop1){
            paramH = getProp(g, paramH, lineH, prop1);
        }
        /**
         * 二技能
         */
        JSONObject prop2 = equip.getJSONObject("prop2");
        if (null!=prop2){
            getProp(g, paramH, lineH, prop2);
        }



        //处理画板
        g.dispose();

        //生成uuid作为名字，防止图片相互覆盖
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //输出图片
        String path = OUT+"1"+".png";
        //获取图片格式
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        try {
            ImageIO.write(thumbImage, /*"GIF"*/ formatName /* format desired */ , new File(path) /* target */ );
        } catch (IOException e) {
            System.out.println("图片合成错误！");
            e.printStackTrace();
        }
        for (String icon : icons) {
            new File(icon).delete();
        }
        //return path;
    }

    private static Integer getProp(Graphics2D g, Integer paramH, Integer lineH, JSONObject prop1) throws IOException {
        paramH=paramH+lineH;
        //技能名字
        String prop1title = prop1.getString("title");
        g.setColor(Color.orange);
        g.drawString(prop1title,35,paramH);
        //技能伤害类型
        String prop1damageType = prop1.getString("damageType");
        String prop1damageTypeUrl = type + prop1damageType + ".png";
        BufferedImage prop1damageTypeIcon = ImageIO.read(new URL(prop1damageTypeUrl));
        g.drawImage(prop1damageTypeIcon,prop1title.length()*36,paramH-26,32,32,null);
        //技能介绍
        g.setColor(Color.GRAY);
        paramH+=lineH;
        String maxLvDesc = prop1.getString("maxLvDesc");
        ArrayList<String> maxLvDescList = new ArrayList<>();
        while (true){
            if (maxLvDesc.length()>=25){
                maxLvDescList.add(maxLvDesc.substring(0,25));
                maxLvDesc = maxLvDesc.substring(25);
            }else{
                maxLvDescList.add(maxLvDesc);
                break;
            }
        }
        for (String str : maxLvDescList) {
            g.drawString(str,35,paramH);
            paramH+=lineH;
        }
        return paramH;
    }

    private static ArrayList<String> getParam(JSONObject equip){
        ArrayList<String> params = new ArrayList<>();
        //装备负重
        String cost = equip.getString("cost");
        addParam(params,"负重: ",cost);
        //最大等级
        String maxlv = equip.getString("maxlv");
        addParam(params,"最大等级: ",maxlv);
        //类型
        String baseType = equip.getString("baseType");
        addParam(params,"类型: ",baseType);
        //满级攻击力
        String damageMaxLv = equip.getString("damageMaxLv");
        addParam(params,"满级攻击力: ",damageMaxLv);
        //满级攻速
        String fireRateMaxLv = equip.getString("fireRateMaxLv");
        addParam(params,"满级攻速: ",fireRateMaxLv);
        //满级载弹
        String ammoMaxLv = equip.getString("ammoMaxLv");
        addParam(params,"满级载弹: ",ammoMaxLv);
        //存在上限
        String limitedNumber = equip.getString("limitedNumber");
        addParam(params,"存在上限: ",limitedNumber);
        //满级存在时间
        String countDownTimeMaxLv = equip.getString("countDownTimeMaxLv");
        addParam(params,"满级存在时间: ",countDownTimeMaxLv);

        return params;
    }

    private static void addParam(ArrayList list, String param1, String param2){
        if (null==param2 || "0".equals(param2)){
            return;
        }else{
            list.add(param1+param2);
        }
    }

    /**
     * 解析json文件找到装备
     * @param equipsId
     */
    public static JSONObject getEquip(String equipsId){
        String path = "src/static/Illustrate.txt";
        String result = ReadJsonFileUtils.readJson(path);
        JSONArray equips = JSON.parseArray(result);
        JSONObject equip = null;
        for (int i = 0; i < equips.size(); i++) {
            equip = equips.getJSONObject(i);
            if (equip.getString("id").equals(equipsId)) {
                break;
            }
        }
        return equip;
    }

    public static void normal(JSONObject equip){
        String img = equip.getString("img");
        System.out.println("图片id："+img);
        String title = equip.getString("title");
        System.out.println("装备名称："+title);
        String desc = equip.getString("desc");
        System.out.println("装备描述："+desc);
        String rarity = equip.getString("rarity");
        System.out.println("装备星级："+rarity);
        String cost = equip.getString("cost");
        System.out.println("所需负重："+cost);
        String maxlv = equip.getString("maxlv");
        System.out.println("最大等级："+maxlv);
        String uid = equip.getString("uid");
        System.out.println("uid："+uid);
        String baseType = equip.getString("baseType");
        System.out.println("装备类型："+baseType);
        String id = equip.getString("id");
        System.out.println("装备id："+id);
        String damageBase = equip.getString("damageBase");
        System.out.println("基础攻击力："+damageBase);
        String damageAdd = equip.getString("damageAdd");
        System.out.println("每级加的攻击力："+damageAdd);
        String damageMaxLv = equip.getString("damageMaxLv");
        System.out.println("满级加的攻击力："+damageMaxLv);
        String ammoBase = equip.getString("ammoBase");
        System.out.println("基础载弹："+ammoBase);
        String ammoAdd = equip.getString("ammoAdd");
        System.out.println("每级加的载弹："+ammoAdd);
        String ammoMaxLv = equip.getString("ammoMaxLv");
        System.out.println("满级的载弹："+ammoMaxLv);
        String fireRateBase = equip.getString("fireRateBase");
        System.out.println("基础攻速："+fireRateBase);
        String fireRateAdd = equip.getString("fireRateAdd");
        System.out.println("每级加的攻速："+fireRateAdd);
        String fireRateMaxLv = equip.getString("fireRateMaxLv");
        System.out.println("满级的攻速："+fireRateMaxLv);
        String countDownTimeBase = equip.getString("countDownTimeBase");
        System.out.println("基础停留时间："+countDownTimeBase);
        String countDownTimeAdd = equip.getString("countDownTimeAdd");
        System.out.println("每级加的停留时间："+countDownTimeAdd);
        String countDownTimeMaxLv = equip.getString("countDownTimeMaxLv");
        System.out.println("满级停留时间："+countDownTimeMaxLv);
        String hpBase = equip.getString("hpBase");
        System.out.println("基础生命值："+hpBase);
        String hpAdd = equip.getString("hpAdd");
        System.out.println("每级加的生命值："+hpAdd);
        String hpMaxLv = equip.getString("hpMaxLv");
        System.out.println("满级的生命值："+hpMaxLv);
        String criticalRate = equip.getString("criticalRate");
        System.out.println("criticalRate："+criticalRate);
        String multiShootLineNum = equip.getString("multiShootLineNum");
        System.out.println("criticalRate："+multiShootLineNum);
        String limitedNumber = equip.getString("limitedNumber");
        System.out.println("放置上限："+limitedNumber);
        String damageType = equip.getString("damageType");
        System.out.println("伤害属性："+damageType);
        String seriesId = equip.getString("seriesId");
        System.out.println("系列id："+seriesId);
        String seriesText = equip.getString("seriesText");
        System.out.println("装备系列："+seriesText);
        /**
         * 开始解析技能
         */
        System.out.println("--------------------------------------------");
        JSONObject prop1 = equip.getJSONObject("prop1");
        prop(prop1);
        JSONObject prop2 = equip.getJSONObject("prop2");
        prop(prop2);
        /**
         * 觉醒相关
         */
        System.out.println("--------------------------------------------");
        String posterId = equip.getString("posterId");
        System.out.println("觉醒id："+posterId);
        /**
         * 拆解相关
         */
        System.out.println("--------------------------------------------");
        String decompose = equip.getString("decompose");
        System.out.println("是否可拆解："+decompose);
        if (decompose!=null){
            JSONObject decomposeEquip = equip.getJSONObject("decomposeEquip");
            String decUid = decomposeEquip.getString("uid");
            System.out.println("拆解后的装备uid："+decUid);
            String decTitle = decomposeEquip.getString("title");
            System.out.println("拆解后的装备名称："+decTitle);
            String decImg = decomposeEquip.getString("img");
            System.out.println("拆解后的装备贴图："+decImg);
        }
        /**
         * 进化相关
         */
        System.out.println("--------------------------------------------");
        JSONObject evolveFormula = equip.getJSONObject("evolveFormula");
        System.out.println("进化公式："+evolveFormula);
        JSONArray input = evolveFormula.getJSONArray("input");
        System.out.println("input："+input);
        JSONArray output = evolveFormula.getJSONArray("output");
        System.out.println("output："+output);
        //五星装备进化公式在input，六星在output
        if (input.size()==0){
            System.out.println("进化至此装备：");
            InOrOut(output);
            if (output.size()>1){
                System.out.println("进化至此装备：");
                kira(output);
            }
        }else {
            System.out.println("此装备可进化：");
            InOrOut(input);
        }
        /**
         * 觉醒公式
         */
        System.out.println("--------------------------------------------");
        JSONArray awakenFormula = equip.getJSONArray("awakenFormula");
        System.out.println("觉醒公式："+awakenFormula);
    }

    /**
     * 进化公式输出
     * @param json
     */
    public static void InOrOut(JSONArray json){
        System.out.println("--------------------------------------------");
        JSONObject evo1 = json.getJSONObject(0);
        String evo1id = evo1.getString("id");
        System.out.println("进化公式id："+evo1id);
        String evo1input = evo1.getString("input");
        System.out.println("该装备由："+evo1input+"进化而来");
        String evo1output = evo1.getString("output");
        System.out.println("进化成："+evo1output);
        JSONArray evo1materials = evo1.getJSONArray("materials");
        System.out.println("普通进化所需材料："+evo1materials);
        //装备id形式的进化前后
        String evo1inputEquip = evo1.getString("inputEquip");
        System.out.println("该装备由："+evo1inputEquip+"进化而来");
        String evo1outputEquip = evo1.getString("outputEquip");
        System.out.println("进化成："+evo1outputEquip);
    }

    /**
     * kira进化
     * @param json
     */
    public static void kira(JSONArray json){
        //进化公式2,这里一般是指kira
        System.out.println("--------------------------------------------");
        JSONObject evo2 = json.getJSONObject(1);
        String evo2id = evo2.getString("id");
        System.out.println("kira进化公式id："+evo2id);
        String evo2input = evo2.getString("input");
        System.out.println("kira进化前的装备id："+evo2input);
        String evo2output = evo2.getString("output");
        System.out.println("kira进化后的装备id："+evo2output);
        JSONArray evo2materials = evo2.getJSONArray("materials");
        System.out.println("普通进化所需材料："+evo2materials);
        //装备id形式的进化前后
        String evo2inputEquip = evo2.getString("inputEquip");
        System.out.println("该装备由："+evo2inputEquip+"进化而来");
        String evo2outputEquip = evo2.getString("outputEquip");
        System.out.println("进化成："+evo2outputEquip);
    }

    /**
     * 技能介绍
     * @param prop
     */
    public static void prop(JSONObject prop){
        if (prop==null)
            return;
        String title = prop.getString("title");
        System.out.print(title);
        String damageType = prop.getString("damageType");
        System.out.println("  "+damageType);
        String desc = prop.getString("desc");
        System.out.println(desc);
        String alb = prop.getString("alb");
        System.out.println(alb);
        String maxLvDesc = prop.getString("maxLvDesc");
        System.out.println(maxLvDesc);
    }
}
