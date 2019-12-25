package com.forte.demo.aop;

import com.forte.demo.anno.Ban;
import com.forte.demo.anno.Switch;
import com.forte.demo.bean.power.adminPower.AdminPower;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.bean.power.qqPower.QQPower;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.power.adminPower.AdminPowerService;
import com.forte.demo.service.power.groupPower.GroupPowerService;
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

@Aspect
@Component
public class FunSwitch {
    @Autowired
    AdminPowerService adminPowerService;
    @Autowired
    GroupPowerService groupPowerService;

    @Around("@annotation(com.forte.demo.anno.Switch)")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取msg对象
        GroupMsg msg = (GroupMsg) joinPoint.getArgs()[0];
        //获取发送器对象
        MsgSender sender = (MsgSender) joinPoint.getArgs()[1];
        //发送者QQ号
        String qq = msg.getQQ();
        //发送者当前群
        String group = msg.getGroup();
        CQCode cqCode_at = CQCodeUtil.build().getCQCode_At(qq);

        //获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Switch Switch = signature.getMethod().getAnnotation(Switch.class);
        //本功能所需要的权限
        int admin = Switch.admin();

        //判断发送者QQ号是否有权限
        AdminPower adminPower = AdminPower.builder()
                .qq(qq)
                .admin(admin)
                .status(0)
                .build();
        adminPower = adminPowerService.find(adminPower);
        if (null==adminPower){
            //如果需要权限不是管理者，且该账户并没有权限，则查询是否有管理员权限
            if (admin!=0){
                adminPower = AdminPower.builder()
                        .qq(qq)
                        .admin(0)
                        .status(0)
                        .build();
                adminPower = adminPowerService.find(adminPower);
                if (null==adminPower){
                    //连管理员权限都没有，返回
                    sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 您没有该权限！");
                    return;
                }
            }else{
                sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_at+" 您没有该权限！");
                return;
            }
        }

        //获取要修改的功能和状态
        int status = Switch.status();
        FunEnum[] types = Switch.types();
        //查看数据库中是否已有数据，有则修改，没有则添加
        for (FunEnum type : types) {
            GroupPower groupPower = GroupPower.builder()
                    .qq_group(group)
                    .fun_id(type.ordinal())
                    .build();
            GroupPower isNull = groupPowerService.find(groupPower);
            groupPower.setStatus(status);
            if (null==isNull){
                groupPowerService.insert(groupPower);
            }else{
                groupPower.setId(isNull.getId());
                groupPowerService.update(groupPower);
            }
        }

        //功能名字
        String name = Switch.name();

        String str = "";
        if (status==0){
            str = "本群 " + name + " 开";
        }else{
            str = "本群 " + name + " 关";
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(), str);
    }
}
