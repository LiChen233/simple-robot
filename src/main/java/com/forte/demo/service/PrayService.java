package com.forte.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.forte.demo.mapper.PrayDao;
import com.forte.demo.utils.PrayEnum;
import com.forte.demo.utils.PrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 祈愿
 * 提供4种祈愿功能：公主，魔女，魔法少女，大小姐
 * 保底0表示还在，1表示没了
 */
@Service
public class PrayService {

    @Autowired
    PrayDao prayDao;

    public String highOne(String qq) throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.high);
        return gachaOne(prayJson, qq,PrayEnum.high);
    }

    public ArrayList<String> highTen() throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.high);
        return gachaTen(prayJson,PrayEnum.high);
    }

    public String customOne(String qq) throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.custom);
        return gachaOne(prayJson, qq,PrayEnum.custom);
    }

    public ArrayList<String> customTen() throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.custom);
        return gachaTen(prayJson,PrayEnum.custom);
    }

    public String middleOne(String qq) throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.middle);
        return gachaOne(prayJson, qq,PrayEnum.middle);
    }

    public ArrayList<String> middleTen() throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.middle);
        return gachaTen(prayJson,PrayEnum.middle);
    }

    public String specialOne(String qq) throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.special);
        return gachaOne(prayJson, qq,PrayEnum.special);
    }

    public ArrayList<String> specialTen() throws IOException {
        JSONObject prayJson = PrayUtils.getJsonString(PrayEnum.special);
        return gachaTen(prayJson,PrayEnum.special);
    }

    /**
     * 公主系列的单抽，需要频繁使用数据库，以及很笨的字符串获取当前蛋池保底，以后再优化
     * @param prayJson
     * @param qq
     * @return
     */
    private String  gachaOne(JSONObject prayJson,String qq,PrayEnum prayEnum){

        //当前区间以及保底的字段名就是：某蛋池+"qujian" 或者 某蛋池+"保底"
        String nowQujian = prayEnum.toString()+"qujian";
        String nowBaodi = prayEnum.toString()+"baodi";

        //拿到该蛋池的区间
        Integer qujian = prayDao.getQujian(nowQujian,qq);
        Integer baodi = prayDao.getBaodi(nowBaodi,qq);

        //判断区间数，魔女7发一区间，魔女除外10发
        Integer qujianshu = 10;
        if (prayEnum == PrayEnum.custom){
            qujianshu = 7;
        }

        Map<String, String> gacha;
        if (1 == qujian && 0 == baodi){
            //进行一次单抽，吃保底
            gacha= PrayUtils.gacha(prayJson, true);
        }else {
            //进行一次单抽，没有吃保底
            gacha= PrayUtils.gacha(prayJson, false);
        }
        //抽到的装备
        String equip = gacha.get("equip");
        //判断是否出金，抽到金就重置保底
        if("true".equals(gacha.get("gold"))){
            //如果当前是你的最后一发，则重置区间，重置保底
            if (qujian==1){
                prayDao.resetQujian(nowQujian,qujianshu,qq);
                //如果你抽的不是大小姐，则重置保底
                if (baodi==1 && prayEnum!=PrayEnum.middle){
                    prayDao.resetBaodi(nowBaodi,qq);
                }
            }else {
                //出金了,并且保底还在。设置保底没了
                if (baodi==0 && prayEnum!=PrayEnum.middle){
                    prayDao.resetBaodi(nowBaodi,qq);
                }
                prayDao.reduceQujian(nowQujian,qq);
            }
        }else{
            //如果当前是你的最后一发，则重置区间，重置保底
            if (qujian==1){
                prayDao.resetQujian(nowQujian,qujianshu,qq);
                if (baodi==1 && prayEnum!=PrayEnum.middle){
                    prayDao.resetBaodi(nowBaodi,qq);
                }
            }else {
                //没有出金，区间-1
                prayDao.reduceQujian(nowQujian,qq);
            }
        }
        return equip;
    }

    /**
     * 十连，不需要访问数据库，至少pray数据库不用访问
     * 积分-10，不需要在这里写
     * @param prayJson
     * @return
     */
    private ArrayList<String>  gachaTen(JSONObject prayJson,PrayEnum prayEnum){
        Integer baodi = 10;
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> gacha;
            if (baodi == 1 && prayEnum!=PrayEnum.middle){
                gacha= PrayUtils.gacha(prayJson, true);
            }else {
                gacha= PrayUtils.gacha(prayJson, false);
            }
            //抽到的装备
            String equip = gacha.get("equip");
            res.add(equip);
            //判断是否出金，抽到金就重置保底
            if("true".equals(gacha.get("gold"))){
                baodi = 10;
            }else{
                baodi--;
            }
        }
        return res;
    }
}
