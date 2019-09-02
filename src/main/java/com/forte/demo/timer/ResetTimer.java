package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.mapper.PrayDao;
import com.forte.demo.mapper.QqGroupDao;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//每天0点触发
@CronTask("0 0 0 * * ? ")
//每隔五秒触发
//@CronTask("0/5 * * * * ? *")
public class ResetTimer implements TimeJob {

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //从主类的依赖获取器直接拿到dao对象
        QqGroupDao qqGroupDao = MainApplication.depends.get(QqGroupDao.class);
        PersonDao personDao = MainApplication.depends.get(PersonDao.class);
        PrayDao prayDao = MainApplication.depends.get(PrayDao.class);

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

        //如果魔法少女祈愿时间过了，则重置魔法少女保底
        if (!PrayUtils.findSpecial()){
            prayDao.resetSpecial();
        }

        //获取当前日期，周四和周一重置魔女up
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String currSun = dateFm.format(new Date());
        if("星期一".equals(currSun) || "星期四".equals(currSun)){
            prayDao.resetCustom();
        }
        System.out.println("0点定时器已成功执行！");
    }
}
