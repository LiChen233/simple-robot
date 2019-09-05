package com.forte.demo.listener;

import com.forte.demo.utils.TAipUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.File;

@Beans
public class MsgListener {

    /**
     * 私聊使用ai
     * @param msg
     * @param sender
     * @throws Exception
     */
    @Listen(MsgGetTypes.privateMsg)
    public void priMsg(PrivateMsg msg, MsgSender sender) throws Exception{
        //获取消息
        String m = msg.getMsg();
        //从TAip工具类拿单例
        //从工具类直接拿到ai的回答
        String result = TAipUtils.getTAIP()
                .nlpTextchat(TAipUtils.getSession(),m);
        //发送私信，两个参数一个QQ号一个文本
        String answer = TAipUtils.getAnswer(result);
        sender.SENDER.sendPrivateMsg(msg.getQQ(),answer);
    }

    /**
     * 群聊被@的时候使用ai
     * @param msg
     * @param sender
     * @throws Exception
     */
    @Filter(at = true)
    @Listen(MsgGetTypes.groupMsg)
    public void aiMsg(GroupMsg msg, MsgSender sender) throws Exception {
        //获取消息
        String m = msg.getMsg();
        m = m.substring(m.indexOf(" ")+1).trim();
        //从TAip工具类拿单例
        //从工具类直接拿到ai的回答
        String result = TAipUtils.getTAIP()
                .nlpTextchat(TAipUtils.getSession(),m);
        //发送私信，两个参数一个QQ号一个文本
        String answer = TAipUtils.getAnswer(result);
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" "+answer);
    }

    @Filter(value = {"功能","菜单"})
    @Listen(MsgGetTypes.groupMsg)
    public void functionMsg(GroupMsg msg, MsgSender sender) throws Exception {
        String res = "萌萌新还在持续开发中！\n" +
                "现有如下功能：\n" +
                "回复指令获得详情玩法：\n" +
                "注意！指令不需要@\n" +
                "1、@萌萌新 与萌萌新聊天\n" +
                "2、积分功能\n" +
                "3、图片功能\n" +
                "4、扭蛋功能/抽奖功能\n" +
                "-----我从哪来---\n" +
                "作者介绍\n" +
                "投食/打赏\n" +
                "-----注意事项-----\n" +
                "勿频繁使用指令，禁止刷屏";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Filter(value = "积分功能")
    @Listen(MsgGetTypes.groupMsg)
    public void starFunction(GroupMsg msg, MsgSender sender){
        String res = "积分功能介绍：\n" +
                "签到：获得10积分，每日前三有特殊加成哦！\n" +
                "抽签：获得20-50积分\n" +
                "回复指令查询积分：\n" +
                "积分查询/查询积分";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Filter(value = "图片功能")
    @Listen(MsgGetTypes.groupMsg)
    public void imageFunction(GroupMsg msg, MsgSender sender){
        String res = "图片功能介绍\n" +
                "回复指令获取壁纸/涩图：\n" +
                "1、来/张份色图\n" +
                "2、来/张份壁纸\n" +
                "3、来张/份手机壁纸\n" +
                "注:一次指令消耗5积分";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Filter(value = {"扭蛋功能","抽奖功能"})
    @Listen(MsgGetTypes.groupMsg)
    public void PrayFunction(GroupMsg msg, MsgSender sender){
        String res = "抽奖功能介绍\n" +
                "发送以下指令即可抽奖：\n" +
                "1、公主单抽/十连 \n" +
                "2、魔女单抽/十连\n" +
                "3、大小姐单抽/十连：免费\n" +
                "注:公主/魔女消耗1/10积分";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }


    @Filter("作者介绍")
    @Listen(MsgGetTypes.groupMsg)
    public void authorMsg(GroupMsg msg, MsgSender sender) throws Exception {
        String res = "萌萌新由\n" +
                "瑶光天枢 & Macro\n" +
                "于2019-08-26创造\n" +
                "我们来自国服社团3373，欢迎加团！\n" +
                "鸣谢 咸鱼鱼、搞事学园 给予灵感与借鉴！";
        File file = new File("src/static/3373.jpg");
        String imagePath = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(),res+imagePath);
    }

    @Filter(value = {"投食","打赏"})
    @Listen(MsgGetTypes.groupMsg)
    public void giveMsg(GroupMsg msg, MsgSender sender) throws Exception {
        File ma = new File("src/static/zhifu.jpg");
        String zhifu = CQCodeUtil.build().getCQCode_image("file://"+ma.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(),zhifu);
    }



    @Listen(MsgGetTypes.groupMsg)
    public void addAueAndAns(GroupMsg msg,MsgSender sender){
        String sendMsg = msg.getMsg();
    }




    @Listen(MsgGetTypes.groupMsg)
    public void queAndAns(GroupMsg msg,MsgSender sender){
        //当前发送的消息
        String sendMsg = msg.getMsg();
    }

}
