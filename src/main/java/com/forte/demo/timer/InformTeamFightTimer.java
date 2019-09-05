package com.forte.demo.timer;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.quartz.JobExecutionContext;

/**
 * 团本/团战 定时艾特全体    周五
 */
@CronTask("0 0 20 0 0 5 ")
public class InformTeamFightTimer implements TimeJob {

    /**
     * 六大社团群号
     */
    private static final String FAMILYQQ[] = {
            "452657413",
            "563721596",
            "110822922",
            "684966897",
            "687726107"
    };


    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        CQCode atAll = CQCodeUtil.build().getCQCode_AtAll();
        String message = "今天是周五了，明天周六晚上20点，是游戏一周一次的打团活动。请大家不要忘记，打团大概占用15分钟时间，请提前安排时间，感谢合作。";
        for(int i = 0;i < FAMILYQQ.length;i++){
            msgSender.SENDER.sendGroupMsg(FAMILYQQ[i],atAll+"\n"+message);
        }
    }

    @Override
    public void execute(JobExecutionContext context) {

    }
}
