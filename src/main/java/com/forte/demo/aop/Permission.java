package com.forte.demo.aop;

import com.forte.demo.anno.Check;
import com.forte.demo.bean.Person;
import com.forte.demo.bean.power.count.Count;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.bean.power.qqPower.QQPower;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.PersonService;
import com.forte.demo.service.power.count.CountService;
import com.forte.demo.service.power.groupPower.GroupPowerService;
import com.forte.demo.service.power.qqPower.QQPowerService;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限校验AOP
 */
@Aspect
@Component
public class Permission {

    @Autowired
    GroupPowerService groupPowerService;
    @Autowired
    QQPowerService qqPowerService;
    @Autowired
    CountService countService;
    @Autowired
    PersonService personService;

    private static final Integer SUCCESS = 0;
    private static final Integer FAIL = 1;

    @Around("@annotation(com.forte.demo.anno.Check)")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取msg对象
        GroupMsg msg = (GroupMsg) joinPoint.getArgs()[0];
        //QQ号
        String qq = msg.getQQ();
        //QQ群
        String group = msg.getGroup();
        //获取发送器对象
        MsgSender sender = (MsgSender) joinPoint.getArgs()[1];
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);

        //如果数据库中没有用户数据，发一句话就返回
        Person person = personService.getPerson(qq);
        if(null==person){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 你还没有注册哦，发送签到，开启萌萌新！");
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取注解
        Check check = signature.getMethod().getAnnotation(Check.class);
        //获取注解中的枚举类，直接打印为枚举名称
        FunEnum funEnum = check.type();
        //功能需要花费的积分
        int cost = check.cost();

        //判断是否是魔法少女
        if (funEnum==FunEnum.special_one || funEnum==FunEnum.special_ten){
            if (!PrayUtils.findSpecial()){
                sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 魔法少女关门啦！请下次再来！");
                return;
            }
        }

        //判断积分是否足够
        if (personService.getStar(qq)<cost){
            sender.SENDER.sendGroupMsg(msg.getGroup(),cqCode_at+" 积分不足！");
            return;
        }else{
            //扣除积分
            personService.reduceStar(new Person(qq,cost,null,null));
        }
        //获取枚举类的序号
        int type = funEnum.ordinal();

        /*
         * 权限校验步骤
         * 1.通过群号判断该群是否开启此功能
         *      1).通过，判断QQ号是否能使用该功能
         *          a.通过，使用群功能次数+1，再放行
         *          b.不通过，发送该人没有权限通知
         *      2).不通过，发送该群没有权限通知
         *
         * 如果次数表中没有该群的记录则新建一条该群的再+1
         */

        //查询本群该功能是否已禁用
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
                    //功能调用总数+1
                    Count count = Count.builder()
                            .funName(funEnum.toString())
                            .qqGroup(group)
                            .build();
                    countService.increase(count);
                    //放行
                    joinPoint.proceed();
                }else {
                    //权限不通过则不放行
                    sender.SENDER.sendGroupMsg(group,cqCode_at+" 您已被禁止使用此功能");
                }
            }else{
                //权限不通过则不放行
                sender.SENDER.sendGroupMsg(group,"本群暂未开放此功能");
            }
        }else{
            //权限不通过则不放行
            sender.SENDER.sendGroupMsg(group,"本群暂未开放此功能");
        }
    }
}
