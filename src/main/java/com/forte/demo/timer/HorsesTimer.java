package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.dao.QqGroupDao;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 赌马专用计时器
 */
@CronTask("0 0 16,21 6-30 3 ? 2020-2020")
public class HorsesTimer implements TimeJob {
    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        QqGroupDao qqGroupDao = MainApplication.depends.get(QqGroupDao.class);
        ArrayList<QqGroup> allGroup = qqGroupDao.getAllGroup();
        for (QqGroup group : allGroup) {
            File ma = new File("src/static/suoha.jpg");
            String suoha = CQCodeUtil.build().getCQCode_image("file://"+ma.getAbsolutePath());
            msgSender.SENDER.sendGroupMsg(group.getGroupid(),
                    "使魔Dash比赛即将开始，没有应援的小伙伴请抓紧时间"+suoha);
        }
    }
}
