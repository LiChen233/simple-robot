package com.forte.demo.listener;

import com.forte.demo.mapper.PrayDao;
import com.forte.demo.service.PrayService;
import com.forte.demo.utils.PrayEnum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.File;

/**
 * 扭蛋监听
 */
@Beans
public class PrayListener {

    @Depend
    PrayService prayService;
    @Depend
    PrayDao prayDao;

    /**
     * 监听群消息，暂时写成多个方法，之后可以更换成通过单条消息判断
     * @param msg
     * @param sender
     */
    @Filter(value = "公主单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void highOne(GroupMsg msg, MsgSender sender) throws Exception {
        String high = prayService.highOne(msg.getQQ());
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        Integer baodiNum = getBaodiNum(PrayEnum.high, msg.getQQ());
        String baodila = "";
        if (baodiNum<=3){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+high+baodila);
    }

    @Filter(value = "公主十连")
    @Listen(MsgGetTypes.groupMsg)
    public void highTen(GroupMsg msg, MsgSender sender) throws Exception {
        String path = prayService.highTen();
        File file = new File(path);
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+cqCode_image);
        file.delete();
    }

    @Filter(value = "魔女单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void customOne(GroupMsg msg, MsgSender sender) throws Exception {
        String high = prayService.customOne(msg.getQQ());
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        Integer baodiNum = getBaodiNum(PrayEnum.custom, msg.getQQ());
        String baodila = "";
        if (baodiNum<=3){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+high+baodila);
    }

    @Filter(value = "魔女十连")
    @Listen(MsgGetTypes.groupMsg)
    public void customTen(GroupMsg msg, MsgSender sender) throws Exception {
        String path = prayService.customTen();
        File file = new File(path);
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at.toString()+cqCode_image);
        file.delete();
    }

    @Filter(value = "大小姐单抽")
    @Listen(MsgGetTypes.groupMsg)
    public void middleOne(GroupMsg msg, MsgSender sender) throws Exception {
        String high = prayService.middleOne(msg.getQQ());
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        Integer baodiNum = getBaodiNum(PrayEnum.middle, msg.getQQ());
        String baodila = "";
        if (baodiNum<=3){
            baodila = " 剩"+baodiNum+"发保底";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+high+baodila);
    }

    @Filter(value = "大小姐十连")
    @Listen(MsgGetTypes.groupMsg)
    public void middleTen(GroupMsg msg, MsgSender sender) throws Exception {
        String path = prayService.middleTen();
        File file = new File(path);
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(msg.getQQ());
        String cqCode_image = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at.toString()+cqCode_image);
        file.delete();
    }

    /**
     * 获取保底数
     * @param prayEnum
     * @param qq
     * @return
     */
    private Integer getBaodiNum(PrayEnum prayEnum,String qq){
        String qujian = prayEnum.toString()+"qujian";
        String baodi = prayEnum.toString()+"baodi";
        Integer baodiNum = prayDao.getBaodiNum(qujian, baodi, qq);
        if (baodiNum==null){
            return 10;
        }else {
            return baodiNum;
        }
    }
}
