package com.forte.demo.timer;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

/**
 * 阵营战提醒
 */
@CronTask("0 55 18 1,2,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31 7,8 ? ")
public class CampWar implements TimeJob {

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {

    }
}
