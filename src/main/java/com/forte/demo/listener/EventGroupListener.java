package com.forte.demo.listener;

import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.io.File;


/**
 * 群事件监听 新人入群/退群等
 */
@Beans
public class EventGroupListener {


    /**
     * 监听新人入群方法
     * @param memberIncrease
     * @param sender
     */
    @Listen(MsgGetTypes.groupMemberIncrease)
    public void addGroupMember(GroupMemberIncrease memberIncrease, MsgSender sender){
        //新人入群QQ号
        String qq = memberIncrease.getBeOperatedQQ();
        CQCode at = CQCodeUtil.build().getCQCode_At(qq);
        File file = new File("src/static/shetuan.jpg");
        String imagePath = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        String message = "欢迎新人加入本群。本群是手游《崩坏学园2》的社团内部交流群。\n" +
                "\n" +
                "我们的社团有3373，47781，50055，50625，50666，均为国服活跃社团。想加入我们社团的朋友，请从上述社团中选择一个，并告知管理员，我们会给你安排的。\n" +
                "[我们社团对加团的朋友不设门槛，然而连续3次缺席周六打团，会被请离社团（游戏内请离社团，群里不会踢人）。]" + imagePath +
                "加入我们的社团以后，请在群名片上添加你所在社团的编号，比如你加入50055，这是我们的3团，你可以在群名片上添加“50055”“3”“③”“3团”等提示字眼。此举为管理需要，谢谢合作。\n" +
                "\n" +
                "如果只是想加群聊天和观光的朋友，请在群名片上添加“观光”两字，管理需要，敬请理解，感谢合作。";
        sender.SENDER.sendGroupMsg(memberIncrease.getGroup(),at.toString() + " \n"+message);
    }


}
