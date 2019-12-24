package com.forte.demo.timer;

import com.forte.demo.MainApplication;
import com.forte.demo.listener.power.SurpriseListener;
import com.forte.demo.utils.RandomNum;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Random;

//8-23点每7分钟触发一次
@CronTask("0 /7 8-23 * * ?")
//@CronTask("* * * * * ?")
public class SurpriseTimer implements TimeJob {

    private static final String PATH = "src/static/surprise/";

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        //从主类的依赖获取器直接拿到监听器对象
        SurpriseListener surpriseListener = MainApplication.depends.get(SurpriseListener.class);
        Map<String, Date> map = surpriseListener.getMap();
        //遍历map，如果有群已经超过一小时没发言了，则有概率发送一张图片
        Date now = new Date();
        for(Map.Entry<String, Date> entry : map.entrySet()){
            //距离群上次说话是多久，转换成分钟
            long min = (now.getTime() - entry.getValue().getTime()) / 1000 / 60;
            System.out.println("距离上次发言："+min+"分钟");
            //如果有一个小时没人说话了，则触发下面的
            if (min>=0){
                File files = new File(PATH);
                File[] file = files.listFiles();

                //获取是否发送随机数
                Integer isSender = RandomNum.randomNumber(0, 100);
                System.out.println("概率："+isSender);
                //随机选择文件
                Integer num = RandomNum.randomNumber(0, file.length);
                //概率为15%
                if (isSender<15){
                    String cqCode = CQCodeUtil.build()
                            .getCQCode_image("file://" + file[num].getAbsolutePath());
                    msgSender.SENDER.sendGroupMsg(entry.getKey(),cqCode);
                }
            }
        }
    }
}
