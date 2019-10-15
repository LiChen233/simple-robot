package com.forte.demo.listener;

import com.forte.demo.utils.IsNumber;
import com.forte.demo.utils.TAipUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.result.AuthInfo;
import com.forte.qqrobot.beans.messages.result.BanList;
import com.forte.qqrobot.beans.messages.result.GroupInfo;
import com.forte.qqrobot.beans.messages.result.inner.BanInfo;
import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.MethodUtil;
import com.forte.qqrobot.utils.RandomUtil;
import com.sun.tools.javac.util.Convert;
import org.hamcrest.core.Is;

import javax.script.ScriptException;
import java.io.File;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 群事件监听 新人入群/退群等
 */
@Beans
public class EventGroupListener {


    /**
     * 五大社团群号
     */
    private static final String FAMILYQQ[] = {
            "452657413",
            "563721596",
            "110822922",
            "684966897",
            "687726107"
    };

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
        String groupQQ = memberIncrease.getGroup();
        String messege = at.toString() + "欢迎新人加入本群。";
        File file;
        CQCode imagePath;
        if(groupQQ.equals("195943739")){
            messege = "欢迎"+at.toString()+"加入我们，加团可以找管理员，观光请在群名片上注明“观光”，谢谢合作。";
            file = new File("src/static/shetuanImg/0.jpg");
            imagePath = CQCodeUtil.build().getCQCode_Image("file://" + file.getAbsolutePath());
            messege += imagePath;
            sender.SENDER.sendGroupMsg(memberIncrease.getGroup(),messege);
            return;
        }
        for (int i = 0;i < FAMILYQQ.length; i++){
            if(FAMILYQQ[i].equals(groupQQ)){
                messege = "欢迎" + at.toString() + "加入我们";
                file = new File("src/static/shetuanImg/"+(i+1)+".jpg");
                imagePath = CQCodeUtil.build().getCQCode_Image("file://" + file.getAbsolutePath());
                messege += imagePath;
            }

        }
        sender.SENDER.sendGroupMsg(memberIncrease.getGroup(), messege);
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
        //String chinese = "[\\u4E00-\\u9FA5\\w._~!@#$%^&*()_/∞¿]+";
        String chinese = "[(\\d+|\\-|*|/|%|<<|>>\\d)]*";
        String number = "[\\d]*";
        String message = "";
        long duration = 0;
        long ran = RandomUtil.getNumber(1,10);
        boolean isMatch = Pattern.matches(patter,groupMsg.getMsg());
        if(isMatch){
            if(groupMsg.getGroup().equals("195943739")){
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),"本小姐没有这个权限呢~要不你去自己的社团群玩个够吧！嘿嘿");
                return;
            }
            //判断是否为管理员
            Boolean isAdmin = this.isAdmin(sender,groupMsg.getGroup(),groupMsg.getQQ());
            if(isAdmin){
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),"别欺负人家萌萌新啦，你能不能领取套餐你心里没点13数嘛？哼~");
                return;
            }
            String content = groupMsg.getMsg();
            String result = content.substring(content.indexOf("餐")+1,content.length()).trim();
            //如领取套餐后无任何内容则直接返回
            if(result.length() == 0) {
                duration = ran;
                message = "想冷静又不知道冷静多久？那本小姐就大发慈悲的赏你"+duration+"分钟吧 不要谢谢我哟~";
                sender.SETTER.setGroupBan(groupMsg.getGroup(),groupMsg.getQQ(),duration*60);
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),message);
                return;
            }
            //判断领取套餐后的内容是否为数字或者带小数点的数字
            if(IsNumber.isInteger(result) || IsNumber.isDouble(result)){
                double num = Double.parseDouble(result);
                if(num < 1){
                    duration = 1;
                    message = "别这么小气，都不大于1呢，我就大发慈悲给你凑个整好啦！";
                }else{
                    duration = (int)num;
                    message = bannedTime(duration);
                }
            }else{
                //匹配包含数字和运算符的内容，否则机器人无法看懂
                if(Pattern.matches(chinese,result)){
                    //防止只有运算符而没有数字的运算，或者多个运算符 例如：1+++++1 等
                    try{
                        // 计算方法
                        duration = Long.parseLong(MethodUtil.eval(result).toString());
                        if(duration < 0){
                            duration = Math.abs(duration);
                            message = "-"+duration+"？"+"负数？那怎么行呢？已经为您贴心的转为正数了呢~";
                        }else if(duration == 0){
                            duration = ran;
                            message = "0？不不不不不，这可不行呢！就随便赏你"+duration+"分钟吧";
                        }else{
                            message = bannedTime(duration);
                        }
                    }catch (Exception e){
                        duration = ran;
                        message = "完全看不懂你在说什么呢？就让你去小黑屋待会儿吧~ 嘻嘻";
                    }
                }else{
                    duration = ran;
                    message = "完全看不懂你在说什么呢？就让你去小黑屋待会儿吧~ 嘻嘻";
                }
            }
            sender.SETTER.setGroupBan(groupMsg.getGroup(),groupMsg.getQQ(),duration*60);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),message);
        }
    }


    /**
     * 计算分钟数为几天几时几分
     * @param t 分钟数
     * @return 返回字符串结果
     */
    private String bannedTime(long t){
        String result = "";
        long day = t/(24*60);
        long hour = (t%(24*60))/60;
        long minute = (t%(24*60))%60;
        if(day >= 30){
            result = "这这这？这都"+day+"天了！上限30天呢，有一句话叫做不作死就不会死，等下没人给你解除看你找谁哭鼻子去！嘻嘻";
        }else if(day >= 1 ){
            result = "哦哦哦哦，"+day+"天"+hour+"小时"+minute+"分钟"+"是吧？"+"兄跌有对自己有点恨呢！下次不要这样了好不好？萌萌新看着都怪开心的呢~ (偷笑)";
        }
        else if(hour >= 1){
            result = "噢噢噢噢，"+hour+"小时"+minute+"分钟"+"是吧？"+"那就满足你这个大大的要求吧~ 要好好的面壁思过啦！";

        }else{
            result = "嗯嗯嗯嗯，"+minute + "分钟"+"是吧？"+"那就满足你这个小小的要求吧~ 要好好的反省自己哦！";
        }
        return  result;
    }


    /**
     * 判断是否为管理员
     * @param sender 送信器
     * @param groupQQ 群号
     * @param qq
     * @return 返回是否为管理员
     */
    private boolean isAdmin(MsgSender sender,String groupQQ,String qq){
        boolean flag = false;
        GroupInfo groupInfo = sender.GETTER.getGroupInfo(groupQQ);
        String[] admin = groupInfo.getAdminList();
        for(String a : admin){
            if(a.equals(qq)){
                flag = true;
                break;
            }
        }
        if(groupInfo.getOwnerQQ().equals(qq)){
            flag = true;
        }
        return flag;
    }





}
