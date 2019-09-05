package com.forte.demo.timer;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.JobExecutionContext;

/**
 * 团本/团战 定时艾特全体   周六
 */
@CronTask("0 50 19 ? * SAT")
public class InformTeamFightTwoTimer implements TimeJob {

    /**
     * 六大社团群号
     */
    private static final String FAMILYQQ[] = {
            "452657413",
            "563721596",
            "110822922",
            "684966897",
            "687726107",
            "195943739"
    };


    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        CQCode atAll = CQCodeUtil.build().getCQCode_AtAll();
        String message = "每周的打团活动快要开始啦，还有10分钟哦~，快快拿起你的手机做好打团的准备啦。加油哦~~";
        for(int i = 0;i < FAMILYQQ.length;i++){
            msgSender.SENDER.sendGroupMsg(FAMILYQQ[i],atAll+"\n"+message);
        }
    }

    @Override
    public void execute(JobExecutionContext context) {

    }
}
