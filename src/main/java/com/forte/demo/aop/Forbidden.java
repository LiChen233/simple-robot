package com.forte.demo.aop;

import com.forte.demo.anno.Ban;
import com.forte.demo.bean.power.adminPower.AdminPower;
import com.forte.demo.bean.power.qqPower.QQPower;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.power.adminPower.AdminPowerService;
import com.forte.demo.service.power.qqPower.QQPowerService;
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

import java.util.List;

/**
 * Ban人AOP
 */
@Aspect
@Component
public class Forbidden {

    @Autowired
    AdminPowerService adminPowerService;
    @Autowired
    QQPowerService qqPowerService;

    @Around("@annotation(com.forte.demo.anno.Ban)")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取msg对象
        GroupMsg msg = (GroupMsg) joinPoint.getArgs()[0];
        //获取发送器对象
        MsgSender sender = (MsgSender) joinPoint.getArgs()[1];
        //发送者QQ号
        String qq = msg.getQQ();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);

        //获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Ban ban = signature.getMethod().getAnnotation(Ban.class);
        //本功能所需要的权限
        int admin = ban.admin();

        //判断发送者QQ号是否有管理员权限
        AdminPower adminPower = AdminPower.builder()
                .qq(qq)
                .admin(admin)
                .status(0)
                .build();
        adminPower = adminPowerService.find(adminPower);
        if (null==adminPower){
            sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 您没有该权限！");
            return;
        }

        //被ban的人的QQ号
        String banqq = "";
        List<CQCode> cqCodes = CQCodeUtil.build().getCQCodeFromMsg(msg.getMsg());
        for (CQCode cqCode : cqCodes) {
            banqq = cqCode.get("qq");
        }
        if (banqq.equals("")){
            sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 指令错误！");
            return;
        }

        //获取要修改的功能和状态
        int status = ban.status();
        FunEnum[] types = ban.types();
        //查看数据库中是否已有数据，有则修改，没有则添加
        for (FunEnum type : types) {
            QQPower qqPower = QQPower.builder()
                    .qq(banqq)
                    .fun_id(type.ordinal())
                    .build();
            QQPower isNull = qqPowerService.find(qqPower);
            qqPower.setStatus(status);
            if (null==isNull){
                qqPowerService.insert(qqPower);
            }else{
                qqPower.setId(isNull.getId());
                qqPowerService.update(qqPower);
            }
        }
        String str = "";
        if (status==0){
            str = "已启用 ";
        }else{
            str = "已禁用 ";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(), str+banqq);
    }
}
