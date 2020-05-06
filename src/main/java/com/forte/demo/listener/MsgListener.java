package com.forte.demo.listener;

import com.forte.demo.MainApplication;
import com.forte.demo.anno.Check;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.dao.PersonDao;
import com.forte.demo.dao.PrayDao;
import com.forte.demo.dao.QqGroupDao;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.power.count.CountService;
import com.forte.demo.service.power.count.CountServiceImpl;
import com.forte.demo.utils.EquipsUPUtils;
import com.forte.demo.utils.TAipUtils;
import com.forte.demo.utils.UuidUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.result.ImageInfo;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.CQUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MsgListener {

    @Autowired
    QqGroupDao qqGroupDao;
    @Autowired
    PersonDao personDao;
    @Autowired
    CountService countService;
    @Autowired
    PrayDao prayDao;

    private static final String QQ = "2943226427";

    /**
     * 直接发图片给机器人，然后保存到惊喜图里
     * @param msg
     * @param sender
     */
    @Listen(MsgGetTypes.privateMsg)
    public void img(PrivateMsg msg, MsgSender sender){
        try{
            //获取uuid
            CQCodeUtil codeUtil = CQCodeUtil.build();
            List<CQCode> codes = codeUtil.getCQCodeFromMsg(msg.getMsg());
            for (CQCode code : codes) {
                String uuid = code.getParam("file");
                //拿到图片url
                ImageInfo imageInfo = sender.GETTER.getImageInfo(uuid);
                //下载图片
                URL url = new URL(imageInfo.getUrl());
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                //文件名字
                String filePath = "src/static/surprise/" + UuidUtils.getUuid();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] buffer = new byte[8192];
                int count;
                while ((count = in.read(buffer)) > 0) {
                    out.write(buffer, 0, count);
                }
                out.close();
                in.close();
                conn.disconnect();
                File file = new File(filePath);
                String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
                sender.SENDER.sendPrivateMsg(msg.getQQ(), cqCode_image+"保存图片成功，文件id："+uuid);
            }
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(msg.getQQ(), "保存图片出错啦");
        }
    }

    @Listen(MsgGetTypes.privateMsg)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value =
            {"通知"})
    public void notice(PrivateMsg msg, MsgSender sender){
        String notice = msg.getMsg().substring(2).trim().replaceAll("],","]");
        ArrayList<QqGroup> allGroup = qqGroupDao.getAllGroup();
        for (QqGroup group : allGroup) {
            sender.SENDER.sendGroupMsg(group.getGroupid(),notice);
        }
        sender.SENDER.sendPrivateMsg(msg.getQQ(),"通知发送成功");
    }

    /**
     * 私聊
     */
    @Listen(MsgGetTypes.privateMsg)
    @Filter(value = "重置")
    public void priMsg(PrivateMsg msg, MsgSender sender){
        sender.SENDER.sendPrivateMsg(QQ,"开始重置");

        try {
            //重置签到总人数
            qqGroupDao.resetSigninCount();
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(QQ,"重置签到总人数出错");
            sender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            //重置所有人的签到记录
            personDao.resetAllSign();
            //重置所有人的抽签记录
            personDao.resetDraw();
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(QQ,"重置签到记录出错");
            sender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            //刷新每日日志
            countService.newDay();
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(QQ,"刷新每日日志出错");
            sender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            //获取当前日期，周四和周一重置魔女up
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            String currSun = dateFm.format(new Date());
            if("星期一".equals(currSun) || "星期四".equals(currSun)){
                prayDao.resetCustom();
            }
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(QQ,"重置魔女up出错");
            sender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            //刷新up记录
            EquipsUPUtils.flushJson();
        }catch (Exception e){
            sender.SENDER.sendPrivateMsg(QQ,"刷新up记录出错");
            sender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        sender.SENDER.sendPrivateMsg(QQ,"重置完毕");
    }

    /**
     * 群聊被@的时候使用ai
     * @param msg
     * @param sender
     * @throws Exception
     */
    @Check(type = FunEnum.ai_count)
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

    @Check(type = FunEnum.ai_count)
    @Filter(value = {"功能","菜单"})
    @Listen(MsgGetTypes.groupMsg)
    public void functionMsg(GroupMsg msg, MsgSender sender) throws Exception {
        String res = "萌萌新还在持续开发中！\n" +
                "现有如下功能：\n" +
                "回复指令获得详情玩法：\n" +
                "注意！指令不需要@\n" +
                "@萌萌新 与萌萌新聊天\n" +
                "积分功能\n" +
                "图片功能\n" +
                "扭蛋功能/抽奖功能\n" +
                "上次up\n" +
                "装备查询功能\n" +
                "-----我从哪来---\n" +
                "作者介绍\n" +
                "投食/打赏\n" +
                "-----注意事项-----\n" +
                "勿频繁使用指令，禁止刷屏";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Check(type = FunEnum.ai_count)
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

    @Check(type = FunEnum.ai_count)
    @Filter(value = "图片功能")
    @Listen(MsgGetTypes.groupMsg)
    public void imageFunction(GroupMsg msg, MsgSender sender){
        String res = "图片功能介绍\n" +
                "回复以下指令获取相应图片：\n" +
                "来张/份色图/壁纸/手机壁纸/头像\n" +
                "例如：来张色图 或 来份手机壁纸";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Check(type = FunEnum.ai_count)
    @Filter(value = {"扭蛋功能","抽奖功能"})
    @Listen(MsgGetTypes.groupMsg)
    public void PrayFunction(GroupMsg msg, MsgSender sender){
        String res = "抽奖功能介绍\n" +
                "发送以下指令即可抽奖：\n" +
                "1、公主单抽/十连 \n" +
                "2、魔女单抽/十连\n" +
                "3、大小姐单抽/十连：免费\n" +
                "注:单抽/十连需1/10消耗积分";
        sender.SENDER.sendGroupMsg(msg.getGroup(),res);
    }

    @Check(type = FunEnum.ai_count)
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

    @Check(type = FunEnum.ai_count)
    @Filter(value = {"投食","打赏","恰饭"})
    @Listen(MsgGetTypes.groupMsg)
    public void giveMsg(GroupMsg msg, MsgSender sender) throws Exception {
        File ma = new File("src/static/zhifu.jpg");
        String zhifu = CQCodeUtil.build().getCQCode_image("file://"+ma.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(),"钱包空空的,给点恰饭钱吧..."+zhifu);
    }

    /**
     * 打印报错信息
     */
    private String getMsg(Exception e){
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTraces = e.getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            sb.append(stackTrace);
            sb.append("\n");
        }
        return sb.toString();
    }
}
