package com.forte.demo.listener;

import com.forte.demo.bean.Person;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.service.PersonService;
import com.forte.demo.service.PrayService;
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


/**
 * 积分监听
 */
@Beans
public class StarListener {


    @Depend
    PersonService personService;

    @Depend
    QqGroupService qqGroupService;

    @Depend
    PrayService prayService;


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "签到")
    public void drawLots(GroupMsg groupMsg, MsgSender sender){
        String qq = groupMsg.getQQ();
        String qqgroupId = groupMsg.getGroup();
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String message = this.qqGroupSignin(qqgroupId,qq);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" "+message);
    }

    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "抽签")
    public void draw(GroupMsg msg, MsgSender sender){
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if (person.getDraw()!=0){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你今天已经抽过签啦！不要贪心哦~");
            return;
        }
        Integer star = RandomNum.randomNumber(20, 50);
        person = new Person(person.getQq(), person.getStar() + star, 1,0);
        personService.addStar(person);
        String message = "";
        if (star>=40){
            message=" 抽到上上签！";
        }else if (star>=30){
            message=" 抽到上签！";
        }else if (star>=20){
            message=" 抽到上平签！";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+message+"获得"+star+"积分！");
        person.setDraw(1);
        person.setStar(person.getStar()+star);
        personService.setDraw(person);
    }

    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "积分查询")
    public void getStar(GroupMsg msg, MsgSender sender){
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 剩余积分："+personService.getStar(qq));
    }



        public String qqGroupSignin(String groupid,String personid){
            String message;
            Integer star;

            //如果数据库中没有用户数据，就插入一条数据
            Person person = personService.getPerson(personid);
            if(person==null){
                //初始化用户
                person = new Person(personid,100,0,0);
                personService.addPerson(person);
                //初始化祈愿数据
                prayService.addPray(personid);
            }
            if(person.getSignin() == 1){
                message = "今天已经签到！明日再来~";
            }else {
                //查询群信息
                QqGroup qqGroup = qqGroupService.selectGroup(groupid);
                //判断群是否已存在数据库，否则初始化群
                if (qqGroup == null) {
                    qqGroup = new QqGroup(groupid, 0);
                    qqGroupService.addGroup(qqGroup);
                }
                if (qqGroup.getSigninCount() >= 0 && qqGroup.getSigninCount() <= 2) {
                    star = RandomNum.randomNumber(15, 25);
                }else {
                    star = 10;
                }
                qqGroupService.updateGroup(groupid);
                person = new Person(person.getQq(), person.getStar() + star, 1,0);
                personService.addStar(person);
                message = "签到成功，你是本群第" + (qqGroup.getSigninCount()+1) + "个签到。\n" +
                        "获得积分：" + star + "。剩余积分：" + person.getStar() + "。\n" +
                        "发送“抽签”获得更多积分。";
            }
            return message;
        }
    }
