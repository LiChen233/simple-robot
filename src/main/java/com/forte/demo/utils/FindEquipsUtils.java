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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 装备查询类
 */
public class FindEquipsUtils {

    private static final Integer width = 762;
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
    private static final String error = "https://hsod2.hongshn.xyz/error.png";
    private static final String OUT = "./temp/";
    private static final String EquipsPath = "src/static/Illustrate.txt";
    //经常用的常量
    private static final Integer l28 = 28;
    private static final Integer l3 = 3;
    private static final Integer l64 = 64;
    private static final Integer l32 = 32;
    private static final Integer l0 = 0;
    private static final Integer lineH = 45;
    private static final Integer l128 = 128;
    private static final Integer l50 = 50;
    private static final Integer l400 = 400;
    private static final Integer l205 = 205;
    private static final Integer l170 = 170;
    private static final Integer l292 = 292;
    private static final Integer l290 = 290;
    private static final Integer l48 = 48;
    private static final Integer l340 = 340;
    private static final Integer l29 = 29;
    private static final Integer l95 = 95;
    private static final Integer l145 = 145;
    private static final Integer l10 = 10;
    private static final Integer l98 = 98;
    private static final Integer l42 = 42;
    private static final Integer l35 = 35;
    private static final Integer l2 = 2;
    private static final Integer l20 = 20;
    private static final Integer l1 = 1;
    private static final Integer l4 = 4;
    private static final Integer l5 = 5;
    private static final Integer l_1 = -1;
    private static final Integer l130 = 130;
    private static final Integer l105 = 105;
    private static final Integer l40 = 40;
    private static final Integer l460 = 460;
    private static final Integer l490 = 490;
    private static final Integer l25 = 25;
    private static final Integer l26 = 36;
    private static final Integer l36 = 36;
    private static final Integer l100 = 100;
    private static final Integer l283 = 283;
    private static final Integer l195 = 195;
    private static final Integer l102 = 102;
    private static final Integer l120 = 120;
    private static final Integer l190 = 190;
    private static final Integer l200 = 200;
    private static final Integer buff = 4096;
    //一个格子的大小
    private static final Integer box = l200;
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
     * 关键字查询方法
     */
    public static String search(String key) throws IOException {
        //查询装备关键字
        JSONArray equips = getEquips();
        ArrayList<Map<String,String>> list = new ArrayList<>();
        for (int i = l0; i < equips.size(); i++) {
            if (equips.get(i).toString().indexOf(key)!=l_1){
                Map<String,String> map = new HashMap<>(l2);
                JSONObject equip = equips.getJSONObject(i);
                String id = equip.getString(sid);
                //如果id等于0，则是那些秘境buff，不加进去
                if ("0".equals(id)){
                    continue;
                }
                //忽略使魔
                if(null != equip.getJSONObject("ultraSkill")){
                    continue;
                }
                map.put(sid, id);
                map.put("img", equip.getString("img"));
                //查到的装备数量超过20个就不继续下去了
                if (list.size()>=l20){
                    return null;
                }else {
                    list.add(map);
                }
            }
        }

        /**
         * 图片生成，一个图标128，那么一个格子200
         */
        Integer size = list.size();
        Integer width;
        Integer height;
        //如果查到的只有一个，则直接调用合成返回
        if (l1.equals(size)){
            return findEq(list.get(l0).get(sid));
        }else if (size>16){
            width = l5;
            height = l4;
        }else if (size>12){
            width = l4;
            height = l4;
        }else if (size>9){
            width = l4;
            height = l3;
        }else if (size>6){
            width = l3;
            height = l3;
        }else if (size>4){
            width = l3;
            height = l2;
        }else if (size==l0){
            //如果没找到装备
            return "undefined";
        }else {
            //数量在2-3，则直接一排输出
            width = size;
            height = l1;
        }
        //新建画布
        BufferedImage combined = new BufferedImage(width*box,height*box+l64, BufferedImage.TYPE_INT_RGB);
        //新建画板
        Graphics2D g = combined.createGraphics();
        //设置背景色
        g.setBackground(Color.WHITE);
        //填充背景色
        g.clearRect(l0, l0, width*box, height*box+l64);
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置字体
        g.setFont(new Font("微软雅黑",Font.BOLD, l28));

        /**
         * 写入提示文字
         */
        g.setColor(ORANGE);
        g.drawString("使用方法:装备查询 (id)",28,44);
        g.setColor(GRAY);

        /**
         * 图标以及装备id
         */
        Integer w = l1;
        Integer h = l1;
        for (int i = l0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            BufferedImage img = getBufferedImage(map.get("img"));
            g.drawImage(img,w*box-164,h*box-128,l128,l128,null);
            g.drawString(map.get(sid),w*box-128,h*box+28);
            if (w.equals(width)){
                //如果一行走完，换一行
                w = l1;
                h++;
            }else{
                //如果没走完，则前进一格
                w++;
            }
        }

        //刷新画板
        g.dispose();

        //合成新图片
        String path = OUT + UuidUtils.getUuid() + suffix;
        File file = new File(OUT);
        if (!file.exists()){
            file.mkdirs();
        }
        ImageIO.write(combined, "png", new File(path));

        return path;
    }

    /**
     * 装备图+觉醒图合成方法
     */
    public static String findEq(String equipsId) throws IOException {
        ArrayList<String> list = FindEquipsUtils.synthesis(equipsId);
        //如果是使魔就返回
        if (null == list){
            return "undefined";
        }
        //没有觉醒图就返回装备图片地址
        if (list.get(l1).equals("")){
            return list.get(l0);
        }
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
        File file = new File(OUT);
        if (!file.exists()){
            file.mkdirs();
        }
        //合成新图片
        ImageIO.write(combined, "png", new File(path));

        //删除缓存图片
        for (String imgPath : list) {
            new File(imgPath).delete();
        }
        return path;
    }

    public static ArrayList<String> synthesis(String equipsId) throws IOException {
        /**
         * 先获取装备，查看有没有相关属性，再适配画布高度
         * 默认高度，装备参数开始：283
         * 装备参数计算得出
         * 一个技能高度：230
         * 可拆解：120
         * 可进化：一个公式：120，两个公式：190
         * 可觉醒：120
         */
        //获取装备json
        JSONObject equip = getEquip(equipsId);
        //忽略使魔
        if(null != equip.getJSONObject("ultraSkill")){
            return null;
        }
        //初始高度：283
        Integer H = 240;
        //System.out.println("初始高度:"+H);
        String desc = equip.getString("desc").replace("#n","");
        if (null!=desc && !"".equals(desc)){
            H+=lineH+l10;
        }
        //System.out.println("desc:"+H);
        //拿到装备参数，并计算需要多少高度
        ArrayList<String> params = getParam(equip);
        H += (params.size()+l1) / l2 * lineH+l10;
        //System.out.println("参数行数"+(params.size()+1)/2);
        //System.out.println("装备参数:"+H);
        //查看是否有一技能
        JSONObject prop1 = equip.getJSONObject("prop1");
        float line = 25;
        if (null!=prop1){
            float maxLvDesc = prop1.getString("maxLvDesc").length();
            H+=((int)Math.ceil(maxLvDesc / line) + l1)*lineH;
            //System.out.println("一技能行数:"+((int)Math.ceil(maxLvDesc / line) + l1));
        }
        //System.out.println("一技能:"+H);
        JSONObject prop2 = equip.getJSONObject("prop2");
        if (null!=prop2){
            float maxLvDesc = prop2.getString("maxLvDesc").length();
            H+=((int)Math.ceil(maxLvDesc / line) + l1)*lineH;
            //System.out.println("二技能行数:"+((int)Math.ceil(maxLvDesc / line) + l1));
        }
        H+=l10;
        //System.out.println("二技能:"+H);
        //查看是否可拆解
        Boolean decompose = equip.getBoolean("decompose");
        if (null!=decompose){
            H+=l120;
        }
        //System.out.println("拆解:"+H);

        //查看装备是否可进化
        JSONObject evolveFormula = equip.getJSONObject("evolveFormula");
        JSONArray input = null;
        JSONArray output = null;
        Integer inputSize = l0;
        Integer outputSize = l0;
        if (null!=evolveFormula){
            //此装备可进化
            input = evolveFormula.getJSONArray("input");
            if (null!=input){
                inputSize = input.size();
                if (inputSize == l1){
                    H+=l130;
                }else if (inputSize == l2){
                    H+=l200;
                }
            }
            //System.out.println("进化:"+H);

            //进化至此装备
            output = evolveFormula.getJSONArray("output");
            if (null!=output){
                outputSize = output.size();
                if (outputSize == l1){
                    H+=l130;
                }else if (outputSize == l2){
                    H+=l200;
                }
            }
            //System.out.println("进化至:"+H);
        }

        JSONArray awakenFormula = equip.getJSONArray("awakenFormula");
        if (null!=awakenFormula){
            H+=l120;
        }
        //System.out.println("觉醒:"+H);

        /**
         * 画布画板相关
         */
        //设置画布
        BufferedImage thumbImage = new BufferedImage(width, H, BufferedImage.TYPE_INT_RGB);
        //设置画板
        Graphics2D g = thumbImage.createGraphics();
        //背景设置白色
        g.setBackground(Color.WHITE);
        //填充背景色
        g.clearRect(l0, l0, width, H);
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置字体
        g.setColor(GRAY);
        g.setFont(new Font("微软雅黑",Font.BOLD, l28));

        /**
         * 开始解析装备
         */
        Integer[] paramW = {l50, l400};
        Integer paramH = l283;
        String eqimg = equip.getString("img");
        //获取装备图标
        BufferedImage eqicon = getBufferedImage(eqimg);
        //插入装备图标
        g.drawImage(eqicon, l32, l100, l128, l128,null);

        //插入装备id
        g.drawString("No."+equip.getString("id"), l170, l102 + l28);

        //系列图标
        String seriesId = equip.getString("seriesId");
        if (null!=seriesId){
            if (s0.equals(seriesId)){
                //无系列
                //装备名称,缩进
                g.drawString(equip.getString("title"), l292, l102 + l28);
            }else{
                String seriesPath = series + seriesId + suffix;
                try {
                    BufferedImage sericon = ImageIO.read(new URL(seriesPath));
                    g.drawImage(sericon, l290, l95, l48, l48,null);
                }catch (Exception e){
                    BufferedImage sericon = ImageIO.read(new URL(error));
                    g.drawImage(sericon, l290, l95, l48, l48,null);
                }
                //装备名称
                g.drawString(equip.getString("title"), l340, l102 + l28);
            }
        }

        //伤害类型
        String damageType = equip.getString("damageType");
        if (null!=damageType){
            damageType = type + damageType + suffix;
            BufferedImage damageTypeIcon = ImageIO.read(new URL(damageType));
            g.drawImage(damageTypeIcon, l170, l145, l32, l32,null);
        }


        //装备星级
        Integer rarity = equip.getInteger("rarity");
        BufferedImage rarityIcon = ImageIO.read(new URL(star));
        Integer starWidth = l205;
        for (Integer i = l0; i < rarity; i++) {
            g.drawImage(rarityIcon,starWidth, l145, l29, l29,null);
            starWidth+= l29;
        }

        //装备是否可拆解
        String decomposeremark = "* 该装备可拆解 *";
        if (null==decompose){
            decomposeremark = "* 该装备是地图装 *";
        }
        g.drawString(decomposeremark, l170, l195 + l28);

        //装备说明
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
        for (String param : params) {
            if (pIndex.equals(l2)){
                pIndex= l0;
                paramH+=lineH;
            }
            g.drawString(param,paramW[pIndex],paramH);
            pIndex++;
        }
        paramH+=l10;

        /**
         * 一技能
         */
        if (null!=prop1){
            paramH = getProp(g, paramH, prop1);
        }
        /**
         * 二技能
         */
        if (null!=prop2){
            paramH = getProp(g, paramH, prop2);
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
         *  进化公式
         */
        if (inputSize != l0){
            paramH = evolution(input, g, paramH);
        }
        if (outputSize != l0){
            paramH = evolution(output, g, paramH);
        }

        /**
         * 觉醒公式
         */
        String wakePath = "";
        if (null!=awakenFormula){
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
            /**
             * 调用下载觉醒图片
             */
            String posterId = equip.getString("posterId");
            wakePath = getWake(posterId);
        }

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

    private static Integer evolution(JSONArray output, Graphics2D g, Integer paramH) throws IOException {
        g.setColor(ORANGE);
        paramH+= l48;
        g.drawString("进化至此装备:", l32,paramH);
        g.setColor(GRAY);
        paramH+= l10;
        //0是普通进化，1其他进化
        JSONObject firstEq = output.getJSONObject(l0);
        //写入一次进化
        evolveFormula(g, paramH, firstEq);
        paramH+= l64;

        if (output.size()> l1){
            JSONObject secEq = output.getJSONObject(l1);
            //写入一次进化
            evolveFormula(g, paramH, secEq);
            paramH+= l64;
        }
        return paramH;
    }

    public static String getWake(String posterId) throws IOException {
        /**
         * 觉醒图
         */
        int posterLen = posterId.length();
        if (posterLen ==l3){
            posterId="1"+posterId;
        }else if (posterLen ==l2){
            posterId="10"+posterId;
        }else if (posterLen ==l1){
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
        byte[] buffer = new byte[buff];
        int count;
        while ((count = in.read(buffer)) > l0) {
            out.write(buffer, l0, count);
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
        //System.out.println("图标id"+img);
        if (null==img){
            return ImageIO.read(new URL(error));
        }
        //获取装备图标地址
        if (img.length()==l1){
            img="00"+img;
        }else if (img.length()==l2){
            img=s0+img;
        }
        String eqiconPath = eqImgUrl + img +suffix;
        //拿到图标
        try{
            return ImageIO.read(new URL(eqiconPath));
        }catch (Exception e){
            return ImageIO.read(new URL(error));
        }
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
        int size = maxLvDescList.size()-l1;
        for (int i = l0; i <= size ; i++) {
            g.drawString(maxLvDescList.get(i),l35,paramH);
            if (i!=size){
                paramH+=lineH;
            }
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
     * 通过装备id找到装备
     * @param equipsId 装备id
     */
    private static JSONObject getEquip(String equipsId){
        JSONArray equips = getEquips();
        JSONObject equip = null;
        for (int i = l0; i < equips.size(); i++) {
            equip = equips.getJSONObject(i);
            if (equip.getString(sid).equals(equipsId)) {
                break;
            }
        }
        return equip;
    }

    /**
     * 拿到装备数组
     * @return
     */
    private static JSONArray getEquips(){
        String result = ReadJsonFileUtils.readJson(EquipsPath);
        JSONArray equips = JSON.parseArray(result);
        return equips;
    }
}
