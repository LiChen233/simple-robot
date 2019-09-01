package com.forte.demo.timer;

import com.forte.demo.mapper.QqGroupDao;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;

//这是每天0点触发
//@CronTask("0 0 0 * * ? ")
@CronTask("* * * * * ? *")
public class ResetTimer implements TimeJob {

    @Depend
    QqGroupDao qqGroupDao;

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        qqGroupDao.resetSigninCount();
    }
}
