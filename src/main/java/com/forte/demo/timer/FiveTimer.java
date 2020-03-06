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

//每天0点1分钟触发
@CronTask("0 1 0 * * ? *")
public class FiveTimer implements TimeJob {

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        PrayDao prayDao = MainApplication.depends.get(PrayDao.class);

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
    }
}
