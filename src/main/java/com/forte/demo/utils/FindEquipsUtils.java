package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FindEquipsUtils {

    public static void synthesis(){
        //设置画布
        BufferedImage thumbImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        //设置画板
        Graphics2D g = thumbImage.createGraphics();
        //背景设置白色
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, 1000, 1000);
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置白色黑体
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,26));
        //处理文字
        g.dispose();

        //生成uuid作为名字，防止图片相互覆盖
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //输出图片
        String path = "C:\\Users\\Chen\\Desktop\\"/*"./temp/"*/+"1"+".jpg";
        //获取图片格式
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        try {
            ImageIO.write(thumbImage, /*"GIF"*/ formatName /* format desired */ , new File(path) /* target */ );
        } catch (IOException e) {
            System.out.println("图片合成错误！");
            e.printStackTrace();
        }
        //return path;
    }

    public static void start(String equipsId){
        String path = "src/static/Illustrate.txt";
        String result = ReadJsonFileUtils.readJson(path);
        JSONArray equips = JSON.parseArray(result);
        for (int i = 0; i < equips.size(); i++) {
            JSONObject equip = equips.getJSONObject(i);
            if (equip.getString("id").equals(equipsId)) {
                normal(equip);
            }
        }
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
