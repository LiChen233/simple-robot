package com.forte.demo.listener;

import com.forte.demo.bean.Person;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.service.PersonService;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.Date;
import java.util.Random;

/**
 * 积分监听
 */
@Beans
public class StarListener {


    @Depend
    PersonService personService;


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "签到")
    public void drawLots(GroupMsg groupMsg, MsgSender sender){
        String qq = groupMsg.getQQ();
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        int star = 20+(int)(Math.random()*30);
        Person person = personService.getPerson(qq);
        if(person==null){
            person = new Person(qq,100,0,new Date());
            personService.addPerson(person);
        }
        if(person.getSignin() == 1){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+"今天已经签到！明日再来");
            return;
        }

        person = new Person(person.getQq(),person.getStar()+star,1,new Date());
        personService.addStar(person);
        Integer countSignin = personService.countSignin();
        String success = "签到成功，获得积分"+star+",积分剩余："+person.getStar()+"。\n今天已签到"+countSignin+"人";
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+success);
    }

}
