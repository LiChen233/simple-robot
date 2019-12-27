package com.forte.demo.listener;

import com.forte.demo.anno.Check;
import com.forte.demo.bean.Person;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.bean.power.count.Count;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.bean.power.qqPower.QQPower;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.PersonService;
import com.forte.demo.service.PrayService;
import com.forte.demo.service.QqGroupService;
import com.forte.demo.service.power.count.CountService;
import com.forte.demo.service.power.groupPower.GroupPowerService;
import com.forte.demo.service.power.qqPower.QQPowerService;
import com.forte.demo.utils.RandomNum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 积分监听
 */
@Component
public class StarListener {

    @Autowired
    PersonService personService;
    @Autowired
    QqGroupService qqGroupService;
    @Autowired
    PrayService prayService;
    @Autowired
    GroupPowerService groupPowerService;
    @Autowired
    QQPowerService qqPowerService;
    @Autowired
    CountService countService;

    private static final Integer FAIL = 1;
    private static final Integer SUCCESS = 0;

    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "签到")
    public void drawLots(GroupMsg groupMsg, MsgSender sender){
        String qq = groupMsg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        String group = groupMsg.getGroup();
        FunEnum funEnum = FunEnum.sign_count;
        int type = funEnum.ordinal();
        /**
         * 单独拿出来的权限校验，因为不适合添加注解
         */
        GroupPower groupPower = GroupPower.builder()
                .qq_group(group)
                .fun_id(type)
                .status(FAIL)
                .build();
        GroupPower temp = groupPowerService.find(groupPower);
        if (null==temp){
            groupPower.setStatus(SUCCESS);
            groupPower = groupPowerService.find(groupPower);
            //如果开启了该功能才能继续鉴查权限
            if (null!=groupPower){
                //检查QQ号权限
                QQPower qqPower = QQPower.builder()
                        .qq(qq)
                        .fun_id(type)
                        .status(FAIL)
                        .build();
                qqPower = qqPowerService.find(qqPower);
                if (null==qqPower){
                    //该功能调用总数+1
                    Count count = Count.builder()
                            .funName(funEnum.toString())
                            .qqGroup(group)
                            .build();
                    countService.increase(count);
                }else {
                    //权限不通过则不放行
                    sender.SENDER.sendGroupMsg(group,cqCode_at+" 您已被禁止使用此功能");
                    return;
                }
            }else{
                //权限不通过则不放行
                sender.SENDER.sendGroupMsg(group,"本群暂未开放此功能");
            }
        }else{
            //权限不通过则不放行
            sender.SENDER.sendGroupMsg(group,"本群暂未开放此功能");
            return;
        }
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String message = this.qqGroupSignin(group,qq);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" "+message);
    }

    @Check(type = FunEnum.sign_count)
    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = {"积分查询","查询积分","我的积分"})
    public void getStar(GroupMsg msg, MsgSender sender){
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
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
            person = new Person(person.getQq(), person.getStar() + star, 1,null);
            personService.addStar(person);
            personService.setSignin(person);
            message = "签到成功，你是本群第" + (qqGroup.getSigninCount()+1) + "个签到。\n" +
                    "获得积分：" + star + "。剩余积分：" + person.getStar();
        }
        return message;
    }

    /*@Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "抽签")
    public void draw(GroupMsg msg, MsgSender sender){
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(person==null){
            sender.SENDER.sendGroupMsg(msg.getQqGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }
        if (person.getDraw()!=0){
            sender.SENDER.sendGroupMsg(msg.getQqGroup(),cqCode_at+" 你今天已经抽过签啦！不要贪心哦~");
            return;
        }
        Integer star = RandomNum.randomNumber(20, 50);
        person = new Person(person.getQq(), person.getStar() + star, null,1);
        personService.addStar(person);
        String message = "";
        if (star>=40){
            message=" 抽到上上签！";
        }else if (star>=30){
            message=" 抽到上签！";
        }else if (star>=20){
            message=" 抽到上平签！";
        }
        sender.SENDER.sendGroupMsg(msg.getQqGroup(),cqCode_at+message+"获得"+star+"积分！");
        person.setDraw(1);
        person.setStar(person.getStar()+star);
        personService.setDraw(person);
    }*/
}
