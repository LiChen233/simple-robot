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
            "https://acg.iclart.com/api.php"
    };
    //来份壁纸API
    private static final String FORWALLPAPER[] = {
            "https://acg.yanwz.cn/wallpaper/api.php",
            "http://api.btstu.cn/sjbz/?lx=suiji",
            "https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302"
    };

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
            Integer i = (int) (Math.random()*2);
            String forcolorimage = CQCodeUtil.build().getCQCode_image(FORWALLPAPER[i]);
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
    @Filter(value = {"来份壁纸","来张壁纸"})
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
            Integer i = (int) (Math.random()*3);
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

}
