package com.forte.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 装备查询类
 */
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
    //觉醒图地址
    private static final String wake = "https://api-1256168079.cos.ap-chengdu.myqcloud.com/images/awaken/";
    private static final String OUT = "./temp/";
    //经常用的常量
    private static final Integer l28 = 28;
    private static final Integer l64 = 64;
    private static final Integer l32 = 32;
    private static final Integer l0 = 0;
    private static final Integer lineH = 45;
    private static final Integer l128 = 128;
    private static final Integer l50 = 50;
    private static final Integer l400 = 400;
    private static final Integer l205 = 205;
    private static final Integer l56 = 56;
    private static final Integer l170 = 170;
    private static final Integer l58 = 58;
    private static final Integer l292 = 292;
    private static final Integer l290 = 290;
    private static final Integer l48 = 48;
    private static final Integer l340 = 340;
    private static final Integer l29 = 29;
    private static final Integer l96 = 96;
    private static final Integer l95 = 95;
    private static final Integer l145 = 145;
    private static final Integer l10 = 10;
    private static final Integer l98 = 98;
    private static final Integer l42 = 42;
    private static final Integer l35 = 35;
    private static final Integer l2 = 2;
    private static final Integer l1 = 1;
    private static final Integer l130 = 130;
    private static final Integer l105 = 105;
    private static final Integer l40 = 40;
    private static final Integer l460 = 460;
    private static final Integer l490 = 490;
    private static final Integer l25 = 25;
    private static final Integer l26 = 36;
    private static final Integer l36 = 36;
    private static final Integer l100 = 100;
    private static final String suffix = ".png";
    private static final String s0 = "0";
    private static final String sid = "id";
    /**
     * 橙色
     */
    private static final Color ORANGE = Color.decode("#ff9700");
    /**
     * 灰色
     */
    private static final Color GRAY = Color.decode("#727272");

    /**
     * 装备查询总方法
     */
    public static String findEq(String equipsId) throws IOException {
        ArrayList<String> list = FindEquipsUtils.synthesis(equipsId);
        BufferedImage image1 = ImageIO.read(new File(list.get(l0)));
        BufferedImage image2 = ImageIO.read(new File(list.get(l1)));

        //拿到装备图片尺寸
        float img1w = image1.getWidth();
        float img2w = image2.getWidth();
        float img2h = image2.getHeight();

        //算出比例，用来调整觉醒图尺寸
        float bili =  img2w / img1w;
        img2h = (img2h / bili);

        int img1W = image1.getWidth();
        int img1H = image1.getHeight();
        int img2H = (int)img2h;

        //新建画布
        BufferedImage combined = new BufferedImage(img1W,img1H + img2H, BufferedImage.TYPE_INT_RGB);
        //新建画板
        Graphics2D g = combined.createGraphics();
        //设置背景色
        g.setBackground(Color.WHITE);
        //填充背景色
        g.clearRect(l0, l0, img2H, img1H+ img2H);
        //拼接
        g.drawImage(image1, l0, l0,null);
        g.drawImage(image2, l0, img1H, img1W,img2H, null);
        //刷新画板
        g.dispose();

        String path = OUT + UuidUtils.getUuid() + suffix;
        //合成新图片
        ImageIO.write(combined, "png", new File(path));

        //删除缓存图片
        for (String imgPath : list) {
            new File(imgPath).delete();
        }
        return path;
    }

    public static ArrayList<String> synthesis(String equipsId) throws IOException {
        //设置画布
        BufferedImage thumbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //设置画板
        Graphics2D g = thumbImage.createGraphics();
        //背景设置白色
        g.setBackground(Color.WHITE);
        //填充背景色
        g.clearRect(l0, l0, width, height);
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置字体
        g.setColor(GRAY);
        g.setFont(new Font("微软雅黑",Font.BOLD, l28));

        /**
         * 开始解析装备
         */
        Integer[] paramW = {l50, l400};
        Integer paramH = l205 + l28;
        //获取装备json
        JSONObject equip = getEquip(equipsId);
        String eqimg = equip.getString("img");
        //获取装备图标
        BufferedImage eqicon = getBufferedImage(eqimg);
        //插入装备图标
        g.drawImage(eqicon, l32, l56, l128, l128,null);

        //插入装备id
        g.drawString("No."+equip.getString("id"), l170, l58 + l28);

        //系列图标
        String seriesId = equip.getString("seriesId");
        if (null!=seriesId){
            if (s0.equals(seriesId)){
                //无系列
                //装备名称,缩进
                g.drawString(equip.getString("title"), l292, l58 + l28);
            }else{
                String seriesPath = series + seriesId + suffix;
                BufferedImage sericon = ImageIO.read(new URL(seriesPath));
                g.drawImage(sericon, l290, l50, l48, l48,null);
                //装备名称
                g.drawString(equip.getString("title"), l340, l58 + l28);
            }
        }

        //伤害类型
        String damageType = equip.getString("damageType");
        if (null!=damageType){
            damageType = type + damageType + suffix;
            BufferedImage damageTypeIcon = ImageIO.read(new URL(damageType));
            g.drawImage(damageTypeIcon, l170, l95, l32, l32,null);
        }


        //装备星级
        Integer rarity = equip.getInteger("rarity");
        BufferedImage rarityIcon = ImageIO.read(new URL(star));
        Integer starWidth = l205;
        for (Integer i = l0; i < rarity; i++) {
            g.drawImage(rarityIcon,starWidth, l96, l29, l29,null);
            starWidth+= l29;
        }

        //装备是否可拆解
        Boolean decompose = equip.getBoolean("decompose");
        String decomposeremark = "* 该装备可拆解 *";
        if (null==decompose){
            decomposeremark = "* 该装备是地图装 *";
        }
        g.drawString(decomposeremark, l170, l145 + l28);

        //装备说明
        String desc = equip.getString("desc").replace("#n","");
        if (null!=desc && !"".equals(desc)){
            g.setColor(ORANGE);
            g.drawString(desc, l32,paramH- l10);
            paramH+=lineH;
            g.setColor(GRAY);
        }

        /**
         * 开始装备参数
         */
        Integer pIndex = l0;
        ArrayList<String> params = getParam(equip);
        for (String param : params) {
            if (pIndex.equals(l2)){
                pIndex= l0;
                paramH+=lineH;
            }
            g.drawString(param,paramW[pIndex],paramH);
            pIndex++;
        }

        /**
         * 一技能
         */
        JSONObject prop1 = equip.getJSONObject("prop1");
        if (null!=prop1){
            paramH = getProp(g, paramH, prop1);
            paramH-=lineH;
        }
        /**
         * 二技能
         */
        JSONObject prop2 = equip.getJSONObject("prop2");
        if (null!=prop2){
            paramH = getProp(g, paramH, prop2);
            paramH-=lineH;
        }

        /**
         * 可拆解，拆解后是啥
         */
        if (null!=decompose){
            paramH+=lineH;
            g.setColor(ORANGE);
            g.drawString("拆解后回收的装备:", l35,paramH);
            g.setColor(GRAY);
            JSONObject decomposeEquip = equip.getJSONObject("decomposeEquip");
            //获取拆解后的装备id
            String decImg = decomposeEquip.getString("img");
            BufferedImage decEqIcon = getBufferedImage(decImg);
            paramH+= l10;
            g.drawImage(decEqIcon, l32,paramH, l64, l64,null);
            //拆解后的装备名称
            String decTitle = decomposeEquip.getString("title");
            g.drawString(decTitle, l98,paramH+ l42);
            paramH+= l64;
        }

        /**
         *  进化至此装备
         */
        JSONObject evolveFormula = equip.getJSONObject("evolveFormula");
        //此装备可进化
        JSONArray input = evolveFormula.getJSONArray("input");
        if (input.size()!= l0){

        }
        //进化至此装备
        JSONArray output = evolveFormula.getJSONArray("output");
        if (output.size()!= l0){
            g.setColor(ORANGE);
            paramH+= l28;
            g.drawString("进化至此装备:", l32,paramH);
            g.setColor(GRAY);
            paramH+= l10;
            //0是普通进化，1其他进化
            JSONObject firstEq = output.getJSONObject(l0);
            //写入一次进化
            evolveFormula(g, paramH, firstEq);

            if (output.size()> l1){
                paramH+= l64;
                JSONObject secEq = output.getJSONObject(l1);
                //写入一次进化
                evolveFormula(g, paramH, secEq);
                paramH+= l64;
            }
        }

        /**
         * 觉醒公式
         */
        JSONArray awakenFormula = equip.getJSONArray("awakenFormula");
        if (awakenFormula.size()!= l0){
            paramH+= l28;
            g.setColor(ORANGE);
            g.drawString("觉醒公式：", l32,paramH);
            g.setColor(GRAY);
            paramH+= l10;
            Integer wakeW = l32;
            for (int i = l0; i < awakenFormula.size(); i++) {
                g.drawImage(getBufferedImage(awakenFormula.get(i).toString()),
                        wakeW,paramH, l64, l64,null);
                wakeW+= l64;
            }
        }

        /**
         * 调用下载觉醒图片
         */
        String posterId = equip.getString("posterId");
        String wakePath = getWake(posterId);

        //刷新画板
        g.dispose();

        //生成临时文件夹
        File outFile = new File(OUT);
        if (!outFile.exists()){
            outFile.mkdir();
        }
        //输出图片
        String equipPath = OUT+UuidUtils.getUuid()+suffix;
        try {
            ImageIO.write(thumbImage,"png", new File(equipPath));
        } catch (IOException e) {
            System.out.println("装备查询图片渲染错误！");
            e.printStackTrace();
        }

        /**
         * 返回链接
         */
        ArrayList<String> arr = new ArrayList<>();
        arr.add(equipPath);
        arr.add(wakePath);
        return arr;
    }

    public static String getWake(String posterId) throws IOException {
        /**
         * 觉醒图
         */
        int posterLen = posterId.length();
        if (posterLen ==3){
            posterId="1"+posterId;
        }else if (posterLen ==2){
            posterId="10"+posterId;
        }else if (posterLen ==1){
            posterId="100"+posterId;
        }
        String posterImgPath = wake + posterId+suffix;
        /**
         *下载图片
         */
        URL url = new URL(posterImgPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //伪造referer
        conn.setRequestProperty("referer", "https://hsod2.hongshn.xyz/illustrate/v3");
        BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
        //文件名字
        String filePath = "./temp/" + UuidUtils.getUuid() + ".png";
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] buffer = new byte[4096];
        int count;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        /**
         * 关闭
         */
        out.close();
        in.close();
        conn.disconnect();

        return filePath;
    }

    /**
     * 插入进化公式
     * @param g 画板
     * @param paramH 高度
     * @param smailEq 进化公式json
     * @throws IOException
     */
    private static void evolveFormula(Graphics2D g, Integer paramH, JSONObject smailEq) throws IOException {
        //插入进化前的图片
        String inputEquip = smailEq.getString("inputEquip");
        BufferedImage inputEquipImg = getBufferedImage(inputEquip);
        g.drawImage(inputEquipImg, l32,paramH,l64,l64,null);
        //进化材料
        g.drawString("+",l105,paramH+l40);
        JSONArray materials = smailEq.getJSONArray("materials");
        Integer clWidth = l130;
        for (int i = l0; i < materials.size(); i++) {
            BufferedImage clImage = getBufferedImage(materials.get(i).toString());
            g.drawImage(clImage,clWidth,paramH,l64,l64,null);
            clWidth+=l64;
        }
        //插入进化后的图片
        g.drawString(">",l460,paramH+l40);
        String outputEquip = smailEq.getString("outputEquip");
        BufferedImage outputEquipImg = getBufferedImage(outputEquip);
        g.drawImage(outputEquipImg,l490,paramH,l64,l64,null);
    }

    /**
     * 装备图片和材料的链接合成
     * @param img 图片地址
     * @return 返回合成链接
     * @throws IOException 抛出io异常
     */
    private static BufferedImage getBufferedImage(String img) throws IOException {
        //获取装备图标地址
        if (img.length()==l1){
            img="00"+img;
        }else if (img.length()==l2){
            img=s0+img;
        }
        String eqiconPath = eqImgUrl + img +suffix;
        //拿到图标
        return ImageIO.read(new URL(eqiconPath));
    }

    /**
     * 技能写入
     * @param g 画板
     * @param paramH 高度
     * @param prop1 技能json
     * @return 返回高度
     * @throws IOException 抛出io异常
     */
    private static Integer getProp(Graphics2D g, Integer paramH, JSONObject prop1) throws IOException {
        paramH=paramH+lineH;
        //技能名字
        String prop1title = prop1.getString("title");
        g.setColor(ORANGE);
        g.drawString(prop1title,l35,paramH);
        //技能伤害类型
        String prop1damageType = prop1.getString("damageType");
        String prop1damageTypeUrl = type + prop1damageType + ".png";
        BufferedImage prop1damageTypeIcon = ImageIO.read(new URL(prop1damageTypeUrl));
        g.drawImage(prop1damageTypeIcon,prop1title.length()*l36,paramH-l26,l32,l32,null);
        //技能介绍
        g.setColor(GRAY);
        paramH+=lineH;
        String maxLvDesc = prop1.getString("maxLvDesc");
        ArrayList<String> maxLvDescList = new ArrayList<>();
        while (true){
            if (maxLvDesc.length()>=l25){
                maxLvDescList.add(maxLvDesc.substring(l0,l25));
                maxLvDesc = maxLvDesc.substring(l25);
            }else{
                maxLvDescList.add(maxLvDesc);
                break;
            }
        }
        for (String str : maxLvDescList) {
            g.drawString(str,l35,paramH);
            paramH+=lineH;
        }
        return paramH;
    }

    /**
     * 装备参数适配
     * @param equip 装备json
     * @return 返回弹夹
     */
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
        //满级载弹
        String ammoMaxLv = equip.getString("ammoMaxLv");
        addParam(params,"满级载弹: ",ammoMaxLv);
        //满级攻速
        String fireRateMaxLv = equip.getString("fireRateMaxLv");
        addParam(params,"满级攻速: ",fireRateMaxLv);
        //暴击率
        String criticalRate = equip.getString("criticalRate");
        if (null!=criticalRate){
            int i = (int) (Float.parseFloat(criticalRate) * l100);
            if (i!=l0){
                criticalRate = "暴击率: "+i+"%";
                params.add(criticalRate);
            }
        }
        //满级存在时间
        String countDownTimeMaxLv = equip.getString("countDownTimeMaxLv");
        addParam(params,"满级存在时间: ",countDownTimeMaxLv);
        //满级生命值
        String hpMaxLv = equip.getString("hpMaxLv");
        addParam(params,"满级生命: ",hpMaxLv);
        //存在上限
        String limitedNumber = equip.getString("limitedNumber");
        addParam(params,"存在上限: ",limitedNumber);

        return params;
    }

    /**
     * 装备属性装填器
     * @param list 弹夹
     * @param param1 属性名字
     * @param param2 属性值
     */
    private static void addParam(ArrayList list, String param1, String param2){
        if (null!=param2 && !s0.equals(param2)){
            list.add(param1+param2);
        }
    }

    /**
     * 解析json文件找到装备
     * @param equipsId 装备id
     */
    private static JSONObject getEquip(String equipsId){
        String result = ReadJsonFileUtils.readJson("src/static/Illustrate.txt");
        JSONArray equips = JSON.parseArray(result);
        JSONObject equip = null;
        for (int i = l0; i < equips.size(); i++) {
            equip = equips.getJSONObject(i);
            if (equip.getString(sid).equals(equipsId)) {
                break;
            }
        }
        return equip;
    }
}
