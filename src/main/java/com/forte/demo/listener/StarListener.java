package com.forte.demo.listener;

import com.forte.demo.bean.Person;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.service.PersonService;
import com.forte.demo.service.QqGroupService;
import com.forte.demo.utils.RandomNum;
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

    @Depend
    QqGroupService qqGroupService;


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "签到")
    public void drawLots(GroupMsg groupMsg, MsgSender sender){
        String qq = groupMsg.getQQ();
        String qqgroupId = groupMsg.getGroup();
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String message = this.qqGroupSignin(qqgroupId,qq);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+message);
    }



    public String qqGroupSignin(String groupid,String personid){
        String message = "";
        Integer star = 0;

        Person person = personService.getPerson(personid);
        if(person==null){
            person = new Person(personid,100,0);
            personService.addPerson(person);
        }
        if(person.getSignin() == 1){
            message = "今天已经签到！明日再来";
        }else {
            QqGroup qqGroup = qqGroupService.selectGroup(groupid);
            if (qqGroup == null) {
                qqGroup = new QqGroup(groupid, 0);
                qqGroupService.addGroup(qqGroup);
            }
            System.out.println(qqGroup.getSigninCount());
            if (qqGroup.getSigninCount() >= 0 && qqGroup.getSigninCount() <= 2) {
                star = RandomNum.randomNumber(10, 20);
            }else {
                star = 10;
            }
            qqGroupService.updateGroup(groupid);
            person = new Person(person.getQq(), person.getStar() + star, 1);
            personService.addStar(person);
            Integer countSignin = qqGroupService.getSigninCount(groupid);
            message = "签到成功，今天你是第" + (qqGroup.getSigninCount()+1) + "个签到。\n" +
                    "获得积分：" + star + "。剩余积分：" + person.getStar() + "。";
        }
        return message;
    }


}



//    String message = "";
//    int star = 20+(int)(Math.random()*30);
//
//
//    Person person = personService.getPerson(qq);
//        if(person==null){
//                person = new Person(qq,100,0);
//                personService.addPerson(person);
//                }
//                if(person.getSignin() == 1){
//                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+"今天已经签到！明日再来");
//                return;
//                }
//
//
//
//                QqGroup qqGroup = qqGroupService.selectGroup(qqgroupId);
//                if(qqGroup == null){
//                qqGroup = new QqGroup(qqgroupId,0);
//                qqGroupService.addGroup(qqGroup);
//                }else{
//                qqGroupService.updateGroup(qqgroupId);
//                }
//                if(qqGroup.getSigninCount() == 0){
//                message = "签到成功，今天你是第"+qqGroup.getSigninCount()+1+"个签到";
//                }
//
//
//
//
//                person = new Person(person.getQq(),person.getStar()+star,1);
//                personService.addStar(person);
//                Integer countSignin = personService.countSignin();
//                String success = "签到成功，获得积分"+star+",积分剩余："+person.getStar()+"。\n今天已签到"+countSignin+"人";
