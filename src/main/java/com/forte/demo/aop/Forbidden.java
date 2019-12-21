package com.forte.demo.aop;

import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Ban人AOP
 */
@Aspect
@Component
public class Forbidden {

    @Around("@annotation(com.forte.demo.anno.Ban)")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取msg对象
        GroupMsg msg = (GroupMsg) joinPoint.getArgs()[0];
        //QQ号
        String qq = msg.getQQ();
        //获取发送器对象
        MsgSender sender = (MsgSender) joinPoint.getArgs()[1];
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);
    }
}
