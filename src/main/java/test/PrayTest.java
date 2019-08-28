package test;

import com.forte.demo.utils.PrayEnum;
import com.forte.demo.utils.PrayUtils;
import com.forte.plusutils.consoleplus.ConsolePlus;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PrayTest {

    //设置APPID/APP_KEY
    public static final String APP_ID = "2121061546";
    public static final String APP_KEY = "hTAA8RaUJj0qipEA";
    public static final String GAO = "https://hsod2.hongshn.xyz/gacha/data";



    @Test
    public void gacha() throws IOException {

        //保底，默认为第10发
        Integer baodi = 10;

        /*for (int i = 0; i < 10; i++) {
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
        }*/
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
