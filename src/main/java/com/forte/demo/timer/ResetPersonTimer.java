package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.mapper.QqGroupDao;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

//这是每天0点触发
@CronTask("0 0 0 * * ? ")
public class ResetPersonTimer implements TimeJob {

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //从主类的依赖获取器直接拿到dao对象
        QqGroupDao qqGroupDao = MainApplication.depends.get(QqGroupDao.class);

        //重置签到总人数
        qqGroupDao.resetSigninCount();

        //重置所有人的签到记录

        System.out.println("定时器已成功执行！");
    }
}
