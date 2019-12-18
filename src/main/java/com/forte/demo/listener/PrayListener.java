package com.forte.demo.listener;

import com.forte.demo.anno.Check;
import com.forte.demo.bean.Person;
import com.forte.demo.service.PersonService;
import com.forte.demo.service.PrayService;
import com.forte.demo.emun.PrayEnum;
import com.forte.demo.service.power.count.CountService;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 扭蛋监听
 */
//@Beans
@Component
public class PrayListener {

    //@Depend
    @Autowired
    PrayService prayService;
    //@Depend
    @Autowired
    PersonService personService;
    //@Depend
    @Autowired
    CountService countService;

    private static final Integer ONE = 1;
    private static final Integer TEN = 10;
    private static final Integer BAODI = 3;

    /**
     * 监听群消息，暂时写成多个方法，之后可以更换成通过单条消息判断
     * @param msg
     * @param sender
     */
    @Check
    @Filter(value = "公主单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void highOne(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<ONE){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,ONE,null,null));
        String high = prayService.highOne(qq);
        Integer baodiNum = prayService.getBaodiNum(PrayEnum.high, qq);
        String baodila = "";
        if (baodiNum<=BAODI){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" "+high+baodila);
    }

    @Check
    @Filter(value = "公主十连")
    @Listen(MsgGetTypes.groupMsg)
    public void highTen(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<TEN){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,TEN,null,null));
        String path = prayService.highTen();
        File file = new File(path);
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+cqCode_image);
        file.delete();
    }

    @Check
    @Filter(value = "魔女单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void customOne(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<ONE){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,ONE,null,null));
        String high = prayService.customOne(qq);
        Integer baodiNum = prayService.getBaodiNum(PrayEnum.custom, qq);
        String baodila = "";
        if (baodiNum<=BAODI){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" "+high+baodila);
    }

    @Check
    @Filter(value = "魔女十连")
    @Listen(MsgGetTypes.groupMsg)
    public void customTen(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<TEN){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,TEN,null,null));
        String path = prayService.customTen();
        File file = new File(path);
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at.toString()+cqCode_image);
        file.delete();
    }

    @Check
    @Filter(value = "魔法少女单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void specialOne(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if (!PrayUtils.findSpecial()){
            sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 魔法少女关门啦！请下次再来！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<TEN){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,ONE,null,null));
        String high = prayService.specialOne(qq);
        Integer baodiNum = prayService.getBaodiNum(PrayEnum.special, qq);
        String baodila = "";
        if (baodiNum<=BAODI){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" "+high+baodila);
    }

    @Check
    @Filter(value = "魔法少女十连")
    @Listen(MsgGetTypes.groupMsg)
    public void specialTen(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if (!PrayUtils.findSpecial()){
            sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 魔法少女关门啦！请下次再来！");
            return;
        }
        //积分不足
        if (personService.getStar(qq)<TEN){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }
        personService.reduceStar(new Person(qq,TEN,null,null));
        String path = prayService.specialTen();
        File file = new File(path);
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at.toString()+cqCode_image);
        file.delete();
    }

    @Check
    @Filter(value = "大小姐单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void middleOne(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String high = prayService.middleOne(msg.getQQ());
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+high);
    }

    @Check
    @Filter(value = "大小姐十连")
    @Listen(MsgGetTypes.groupMsg)
        public void middleTen(GroupMsg msg, MsgSender sender) throws Exception {
        if (msg.getGroup().equals("195943739")){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"主群暂不开放扭蛋功能呢，请去社团群里玩吧~");
            return;
        }
        String path = prayService.middleTen();
        File file = new File(path);
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at.toString()+cqCode_image);
        file.delete();
    }
}
