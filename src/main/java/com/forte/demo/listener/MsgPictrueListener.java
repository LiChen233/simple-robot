package com.forte.demo.listener;

import com.forte.demo.bean.Person;
import com.forte.demo.service.PersonService;
import com.forte.demo.utils.RandomNum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;

/**
 * 壁纸/色图监听
 */

@Beans
public class MsgPictrueListener {


    //来份色图API
    private static final String FORCOLORIMAGEURL[] =  {
            "http://api.btstu.cn/sjbz/?lx=dongman",
            "http://api.mtyqx.cn/api/random.php"
    };
    //来份壁纸API
    private static final String FORWALLPAPER[] = {
            "https://acg.yanwz.cn/wallpaper/api.php",
            "http://api.btstu.cn/sjbz/?lx=suiji",
            "https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302",
            "https://cdn.mom1.cn/?mom=302"
    };

    private static final String PHONEWALLPAPER = "http://api.btstu.cn/sjbz/?lx=m_dongman";



    @Depend
    PersonService personService;


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = {"来份色图","来分色图","来张色图","来份涩图","来分涩图","来张涩图"})
    public void forColorImage(GroupMsg groupMsg, MsgSender sender) throws IOException {
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String qq = groupMsg.getQQ();
        Person person = personService.getPerson(groupMsg.getQQ());
        if(person==null){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if(person.getStar() < 5){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" 积分不足！");
            return;
        }
        try{
            Integer i = (int) (Math.random()*FORCOLORIMAGEURL.length);
            String forcolorimage = CQCodeUtil.build().getCQCode_image(FORCOLORIMAGEURL[i]);
            person = new Person();
            person.setQq(qq);
            person.setStar(5);
            personService.reduceStar(person);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+forcolorimage);
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，色图加载失败！请重试 0.0");
            throw new RuntimeException("色图加载失败",e);
        }

    }


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = {"来份壁纸","来张壁纸","一张壁纸","电脑壁纸"})
    public void forWallpaper(GroupMsg groupMsg, MsgSender sender) throws IOException {
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String qq = groupMsg.getQQ();
        Person person = personService.getPerson(groupMsg.getQQ());
        if(person==null){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if(person.getStar() < 5){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" 积分不足！");
            return;
        }
        try{
            Integer i = (int) (Math.random()*FORWALLPAPER.length);
            String wallpaper = CQCodeUtil.build().getCQCode_image(FORWALLPAPER[i]);
            person = new Person();
            person.setQq(qq);
            person.setStar(5);
            personService.reduceStar(person);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+wallpaper);
        }catch (Exception e){
            personService.addPerson(person);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，壁纸加载失败！请重试 0.0");
            throw new RuntimeException("壁纸加载失败",e);
        }

    }


    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"来份手机壁纸","手机壁纸","来张手机壁纸"})
    public void phoneWallpaper(GroupMsg groupMsg,MsgSender sender){
        String qq = groupMsg.getQQ();
        CQCode at = CQCodeUtil.build().getCQCode_At(qq);
        Person person = personService.getPerson(groupMsg.getQQ());
        if(person==null){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if(person.getStar() < 5){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" 积分不足！");
            return;
        }
        try {
            String phoneImage = CQCodeUtil.build().getCQCode_image(PHONEWALLPAPER);
            person = new Person();
            person.setQq(qq);
            person.setStar(5);
            personService.reduceStar(person);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+phoneImage);
        }catch (Exception e){
            throw new RuntimeException("加载手机壁纸异常！",e);
        }

    }

}
