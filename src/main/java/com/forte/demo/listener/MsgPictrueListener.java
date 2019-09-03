package com.forte.demo.listener;

import com.forte.demo.bean.Person;
import com.forte.demo.service.PersonService;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;

/**
 * 壁纸/色图监听
 */

@Beans
public class MsgPictrueListener {


    //来份色图API
    private static final String FORCOLORIMAGEURL =  "http://www.dmoe.cc/random.php?return=json";
    //来份壁纸API
    private static final String FORWALLPAPER =  "https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302";

    @Depend
    PersonService personService;


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份色图")
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
        person = new Person();
        person.setQq(qq);
        person.setStar(5);
        personService.reduceStar(person);
        Document doc = Jsoup.connect(FORCOLORIMAGEURL).get();
        String docJson = doc.toString();
        String url = docJson.substring(docJson.indexOf('(')+1,docJson.lastIndexOf(')'));
        String c = CQCodeUtil.build().getCQCode_image(url);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+c);
    }


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份壁纸")
    public void forWallpaper(GroupMsg groupMsg, MsgSender sender) throws IOException {
        String wallpaper = CQCodeUtil.build().getCQCode_image(FORWALLPAPER);
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
        person = new Person();
        person.setQq(qq);
        person.setStar(5);
        personService.reduceStar(person);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+wallpaper);
    }

}
