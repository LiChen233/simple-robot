package com.forte.demo.aop;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 权限校验AOP
 */
@Aspect
@Component
public class Permission {

    @Around("@annotation(com.forte.demo.anno.Check)")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取msg对象
        GroupMsg msg = (GroupMsg) joinPoint.getArgs()[0];
        //QQ号
        String qq = msg.getQQ();
        //QQ群
        String group = msg.getGroup();

        MsgSender sender = (MsgSender) joinPoint.getArgs()[1];
        if (true){
            //权限通过则放行
            joinPoint.proceed();
            //获取sender对象
            sender.SENDER.sendGroupMsg(group,"权限校验成功！");
        }else{
            //权限不通过则不放行
            sender.SENDER.sendGroupMsg(group,"权限校验失败！");
        }
    }

    /*@Before("@annotation(com.forte.demo.anno.Check)")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

    }

    @After("@annotation(com.forte.demo.anno.Check)")
    public void doAfter(JoinPoint joinPoint) throws Throwable {

    }*/
}
