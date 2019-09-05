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
@CronTask("0 30 19 0 0 6 ")
public class InformTeamFightTwoTimer implements TimeJob {

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
        String message = "还有30分钟就要打团啦，请各位准备好打团。到50分左右就可以上游戏做好准备啦~各位加油！！";
        for(int i = 0;i < FAMILYQQ.length;i++){
            msgSender.SENDER.sendGroupMsg(FAMILYQQ[i],atAll+"\n"+message);
        }
    }

    @Override
    public void execute(JobExecutionContext context) {

    }
}
