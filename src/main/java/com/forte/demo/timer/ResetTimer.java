package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.dao.PersonDao;
import com.forte.demo.dao.PrayDao;
import com.forte.demo.dao.QqGroupDao;
import com.forte.demo.service.power.count.CountServiceImpl;
import com.forte.demo.utils.EquipsUPUtils;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//每天0点触发
@CronTask("0 0 0 * * ? *")
//每隔五秒触发
//@CronTask("0/5 * * * * ? *")
public class ResetTimer implements TimeJob {

    private static final String QQ = "2943226427";

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //msgSender.SENDER.sendPrivateMsg(QQ,"0点定时器开始执行");

        try {
            QqGroupDao qqGroupDao = MainApplication.depends.get(QqGroupDao.class);
            //重置签到总人数
            qqGroupDao.resetSigninCount();
        }catch (Exception e){
            msgSender.SENDER.sendPrivateMsg(QQ,"重置签到总人数出错");
            msgSender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            PersonDao personDao = MainApplication.depends.get(PersonDao.class);
            //重置所有人的签到记录
            personDao.resetAllSign();
            //重置所有人的抽签记录
            personDao.resetDraw();
        }catch (Exception e){
            msgSender.SENDER.sendPrivateMsg(QQ,"重置签到记录出错");
            msgSender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            CountServiceImpl countService = MainApplication.depends.get(CountServiceImpl.class);
            //刷新每日日志
            countService.newDay();
        }catch (Exception e){
            msgSender.SENDER.sendPrivateMsg(QQ,"刷新每日日志出错");
            msgSender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            PrayDao prayDao = MainApplication.depends.get(PrayDao.class);
            //获取当前日期，周四和周一重置魔女up
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            String currSun = dateFm.format(new Date());
            if("星期一".equals(currSun) || "星期四".equals(currSun)){
                prayDao.resetCustom();
            }
        }catch (Exception e){
            msgSender.SENDER.sendPrivateMsg(QQ,"重置魔女up出错");
            msgSender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        try {
            //刷新up记录
            EquipsUPUtils.flushJson();
        }catch (Exception e){
            msgSender.SENDER.sendPrivateMsg(QQ,"刷新up记录出错");
            msgSender.SENDER.sendPrivateMsg(QQ,getMsg(e));
        }

        //msgSender.SENDER.sendPrivateMsg(QQ,"0点定时器执行完毕");
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
