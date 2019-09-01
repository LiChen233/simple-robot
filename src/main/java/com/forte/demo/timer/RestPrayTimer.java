package com.forte.demo.timer;

import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.IOException;

//每周固定时间触发，基本是公主魔女up轮换的时候
@CronTask("0 0 0 0 0 4,5,7 ")
public class RestPrayTimer implements TimeJob {


    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //刷新蛋池json
        try {
            PrayUtils.flushJson();
        } catch (IOException e) {
            System.out.println("刷新json出错");
            e.printStackTrace();
        }
        System.out.println("定时器已成功执行！");
    }
}
