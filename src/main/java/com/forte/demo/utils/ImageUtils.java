package com.forte.demo.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ImageUtils {
    private static final Integer HEIGHT = 210;
    private static final Integer LINE_HEIGHT = 45;
    private static final Integer SIZE = 26;
    private static final String IN = "src/static/bg.png";
    private static final String OUT = "./temp/";
    private static final Integer EQ = 485;
    private static final Integer SM = 500;
    private static final Integer TIME = 100;
    private static final Integer D_WIDTH = 984;
    private static final Integer D_HEIGHT = 649;

    public static String composeImg(ArrayList<String> equips) throws IOException {

        BufferedImage thumbImage = new BufferedImage(D_WIDTH, D_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = thumbImage.createGraphics();

        //底图
        File file = new File(IN);
        Image src = javax.imageio.ImageIO.read(file);
        g.drawImage(src.getScaledInstance(D_WIDTH,D_HEIGHT,Image.SCALE_SMOOTH), 0, 0, null);

        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //消除画图锯齿，很诡异，有时候有变化有时候没变化，建议关
        //g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        //设置白色黑体
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑",Font.BOLD,SIZE));

        //设置日期格式，获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());

        //写入当前时间
        for (int i = 0; i < equips.size(); i++) {
            g.drawString(date,TIME,HEIGHT+LINE_HEIGHT*i);
        }

        for (int i = 0; i < equips.size(); i++) {

            //获取装备名
            String equip = equips.get(i);
            //替换数据中的不必要字符
            equip = equip.replace("[","【");
            equip = equip.replace("]","】");

            //神器装备最后标记了1，判断是不是神器
            if ("*".equals(equip.substring(equip.length()-1))){
                //拿到真正的装备
                equip = equip.substring(0,equip.length()-1);
                equip = reverse(equip);

                //改变文字颜色
                g.setColor(new Color(206,146,255));

                g.drawString(equip,EQ,HEIGHT+LINE_HEIGHT*i);
            }else{
                g.setColor(Color.WHITE);

                //拿到反转后的装备
                String reverse = reverse(equip);

                //如果这个装备和原来一样，那么就是使魔
                if (reverse.equals(equip)){
                    g.drawString(reverse,SM,HEIGHT+LINE_HEIGHT*i);
                }else {
                    g.drawString(reverse,EQ,HEIGHT+LINE_HEIGHT*i);
                }
            }
        }

        //处理文字
        g.dispose();

        //生成uuid作为名字，防止图片相互覆盖
        String uuid = UuidUtils.getUuid();

        //输出图片
        String path = OUT+uuid+".jpg";
        File exOut = new File(OUT);
        if(!exOut.exists()){
            exOut.mkdir();
        }
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        ImageIO.write(thumbImage, /*"GIF"*/ formatName /* format desired */ , new File(path) /* target */ );
        return path;
    }

    /**
     * 装备星级和装备名字反转
     * @param equip 装备名字
     * @return 处理过的装备
     */
    private static String reverse(String equip){
        String star = equip.substring(equip.length() - 3, equip.length() - 2);
        if ("3".equals(star) || "4".equals(star) ||
                "5".equals(star) || "2".equals(star) ||
                "6".equals(star) || "1".equals(star) || "7".equals(star)){
            equip = equip.substring(equip.length()-4)+equip.substring(0,equip.length()-4);
        }
        return equip;
    }
}
