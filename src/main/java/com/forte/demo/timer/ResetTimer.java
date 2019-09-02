package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.mapper.QqGroupDao;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.IOException;

//这是每天0点触发
//@CronTask("0 0 0 * * ? ")
//@CronTask("0/5 * * * * ? *")
public class ResetTimer implements TimeJob {

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //从主类的依赖获取器直接拿到dao对象
        QqGroupDao qqGroupDao = MainApplication.depends.get(QqGroupDao.class);
        PersonDao personDao = MainApplication.depends.get(PersonDao.class);

        //重置签到总人数
        qqGroupDao.resetSigninCount();

        //重置所有人的签到记录
        personDao.resetAllSign();

        //重置所有人的抽签记录
        personDao.resetDraw();

        //刷新蛋池json
        try {
            PrayUtils.flushJson();
        } catch (IOException e) {
            System.out.println("刷新json出错");
            e.printStackTrace();
        }

        //如果魔法少女祈愿过了，则重置魔法少女保底
        if (!PrayUtils.findSpecial()){

        }
        System.out.println("定时器已成功执行！");
    }
}
