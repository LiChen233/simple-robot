package com.forte.demo.listener;

import com.forte.demo.utils.TAipUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.MethodUtil;
import com.forte.qqrobot.utils.RandomUtil;
import com.sun.tools.javac.util.Convert;

import javax.script.ScriptException;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 群事件监听 新人入群/退群等
 */
@Beans
public class EventGroupListener {


    /**
     * 六大社团群号
     */
    private static final String FAMILYQQ[] = {
            "452657413",
            "563721596",
            "110822922",
            "684966897",
            "687726107",
            "195943739"
    };

    /**
     * 监听新人入群方法
     * @param memberIncrease
     * @param sender
     */
    @Listen(MsgGetTypes.groupMemberIncrease)
    public void addGroupMember(GroupMemberIncrease memberIncrease, MsgSender sender){
        String groupQQ = memberIncrease.getGroup();
        String messege = "欢迎新人入群！";
        if(isFamily(groupQQ)){
            messege = welcome(groupQQ);
        }
        //新人入群QQ号
        String qq = memberIncrease.getBeOperatedQQ();
        CQCode at = CQCodeUtil.build().getCQCode_At(qq);
        sender.SENDER.sendGroupMsg(memberIncrease.getGroup(),at.toString() + " \n"+messege);
    }


    /**
     * 五大社团入群欢迎语
     * @param groupQQ 群号
     * @return  返回欢迎提示语字符串
     */
    private String welcome(String groupQQ){
        String family = "";
        String familyId = "";
        File file = new File("src/static/shetuan.jpg");
        String imagePath = CQCodeUtil.build().getCQCode_image("file://" + file.getAbsolutePath());
        if(groupQQ.equals("452657413")){
            family = "Lost one";
            familyId = "3373";
        }else if(groupQQ.equals("563721596")){
            familyId = "47781";
            family = "御姐控";
        }else if(groupQQ.equals("110822922")){
            familyId = "50055";
            family = "雷电芽衣的秘密";
        }else if(groupQQ.equals("684966897")){
            familyId = "50625";
            family = "符华";
        }else if(groupQQ.equals("687726107")){
            familyId = "50666";
            family = "雷电彩葵";
        }else if(groupQQ.equals("195943739")){
            return "欢迎新人加入本群。本群是手游《崩坏学园2》的社团内部交流群。\n" +
                    "\n" +
                    "我们的社团有3373，47781，50055，50625，50666，均为国服活跃社团。想加入我们社团的朋友，请从上述社团中选择一个，并告知管理员，我们会给你安排的。\n" +
                    "[我们社团对加团的朋友不设门槛，然而连续3次缺席周六打团，会被请离社团（游戏内请离社团，群里不会踢人）。]\n" +imagePath+
                    "加入我们的社团以后，请在群名片上添加你所在社团的编号，比如你加入50055，这是我们的3团，你可以在群名片上添加“50055”“3”“③”“3团”等提示字眼。此举为管理需要，谢谢合作。\n" +
                    "\n" +
                    "如果只是想加群聊天和观光的朋友，请在群名片上添加“观光”两字，管理需要，敬请理解，感谢合作。";
        }
        return "欢迎新人加入本群。本群是手游《崩坏学园2》的国服"+familyId+"社团“"+family+"”内部交流群。\n" +
                "（我们的社团有3373，47781，50055，50625，50666，均为国服活跃社团。）\n" +imagePath+
                "已经加入本群的你，想必已经选择了我们。我们社团对加团的朋友不设门槛，然而连续3次缺席周六打团，会被请离社团（游戏内请离社团，群里不会踢人）。\n" +
                "每周六晚20点打团，请记住时间，最好设置一个手机提醒功能。\n" +
                "\n" +
                "加入我们的社团以后，请将群名片与游戏内昵称统一，此举为管理需要，谢谢合作。如果已经退出社团，或者加群只是聊天和观光的朋友，请在群名片上添加“观光”两字，管理需要，敬请理解，感谢合作。";
    }

    /**
     * 判断是否为社团群/主群
     * @param groupQQ 群号
     * @return
     */
    private boolean isFamily(String groupQQ){
        for(int i = 0;i < FAMILYQQ.length;i++){
            if(groupQQ.equals(FAMILYQQ[i])){
                return true;
            }
        }
        return false;
    }



    @Listen(value = MsgGetTypes.groupMsg)
    public void GetThePackage(GroupMsg groupMsg,MsgSender sender) throws ScriptException {
        String patter = "^领取套餐.*?";
        String chinese = "[\\u4E00-\\u9FA5._~!@#$%^&*()_+]+";
        String message = "完全看不懂你在说什么呢？就让你去小黑屋待会儿吧~ 嘻嘻";
        int duration = 0;
        Integer ran = RandomUtil.getNumber(1,10);
        boolean isMatch = Pattern.matches(patter,groupMsg.getMsg());
        if(isMatch){
            String content = groupMsg.getMsg();
            String result = content.substring(content.indexOf("餐")+1,content.length()).trim();
            if(result.length() == 0) {
                sender.SETTER.setGroupBan(groupMsg.getGroup(),groupMsg.getQQ(),duration*60);
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),message);
                return;
            }
            if(!Pattern.matches(chinese,result)){
                duration = (int)MethodUtil.eval(result);
                if(duration < 0){
                    duration = Math.abs(duration);
                    message = "负数？那怎么行？已经为你贴心的转为正数了呢~";
                }
                if(duration == 0){
                    duration = ran;
                    message = "0？不不不不不，这可不行呢！";
                }
            }else{
                message = "完全看不懂你在说什么呢？就让你去小黑屋待会儿吧~ 嘻嘻";
                duration = ran;
            }
            sender.SETTER.setGroupBan(groupMsg.getGroup(),groupMsg.getQQ(),duration*60);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),message);
        }

    }







}
