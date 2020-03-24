package com.forte.demo.listener.power;

import com.forte.demo.anno.Ban;
import com.forte.demo.anno.Switch;
import com.forte.demo.emun.FunEnum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import org.springframework.stereotype.Component;

/**
 * 权限操作
 * 1.开启/关闭 本群 某功能
 * 2.封禁/解封 某人的使用权限
 * 3.
 */
@Component
public class PowerListener {

    /**
     * 封禁
     */
    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.festival_one,FunEnum.festival_ten},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 扭蛋"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanPray(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.festival_one,FunEnum.festival_ten},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 扭蛋"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanPray(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.se_count},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 色图"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanSe(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.se_count},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 色图"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanSe(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.sign_count},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 签到"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanSign(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.sign_count},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 签到"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanSign(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.eq_count,FunEnum.up_count},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 查询"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanEq(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.eq_count,FunEnum.up_count},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 查询"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanEq(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.ai_count},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 AI"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanAi(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.ai_count},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 AI"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanAi(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.aq_count},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 问答"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanAQ(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.aq_count},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 问答"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanAQ(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.special_one,
            FunEnum.special_ten,FunEnum.custom_one,FunEnum.custom_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.se_count,FunEnum.ai_count,
            FunEnum.eq_count,FunEnum.aq_count,FunEnum.sign_count,FunEnum.up_count,
            FunEnum.festival_one,FunEnum.festival_ten},status = 1)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 萌萌新"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanALL(GroupMsg msg, MsgSender sender){
    }

    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.special_one,
            FunEnum.special_ten,FunEnum.custom_one,FunEnum.custom_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.se_count,FunEnum.ai_count,
            FunEnum.eq_count,FunEnum.aq_count,FunEnum.sign_count,FunEnum.up_count,
            FunEnum.festival_one,FunEnum.festival_ten},status = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 萌萌新"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanALL(GroupMsg msg, MsgSender sender){
    }

    /**
     * 功能开关
     */
    @Switch(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten,
            FunEnum.festival_one,FunEnum.festival_ten},status = 1,admin = 1,name = "扭蛋")
    @Filter(value = {"扭蛋 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void PrayOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten,
            FunEnum.festival_one,FunEnum.festival_ten},status = 0,admin = 1,name = "扭蛋")
    @Filter(value = {"扭蛋 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void PrayON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.se_count},status = 1,admin = 1,name = "色图")
    @Filter(value = {"色图 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void SeOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.se_count},status = 0,admin = 1,name = "色图")
    @Filter(value = {"色图 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void SeON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.sign_count},status = 1,admin = 1,name = "签到")
    @Filter(value = {"签到 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void SignOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.sign_count},status = 0,admin = 1,name = "签到")
    @Filter(value = {"签到 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void SignON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.eq_count,FunEnum.up_count},status = 1,admin = 1,name = "查询")
    @Filter(value = {"查询 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void EqOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.eq_count,FunEnum.up_count},status = 0,admin = 1,name = "查询")
    @Filter(value = {"查询 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void EqON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.ai_count},status = 1,admin = 1,name = "AI")
    @Filter(value = {"AI 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void AiOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.ai_count},status = 0,admin = 1,name = "AI")
    @Filter(value = {"AI 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void AiON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.aq_count},status = 1,admin = 1,name = "问答")
    @Filter(value = {"问答 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void AQOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.aq_count},status = 0,admin = 1,name = "问答")
    @Filter(value = {"问答 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void AQON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.special_one,
            FunEnum.special_ten,FunEnum.custom_one,FunEnum.custom_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.se_count,FunEnum.ai_count,
            FunEnum.eq_count,FunEnum.aq_count,FunEnum.sign_count,FunEnum.up_count,
            FunEnum.festival_one,FunEnum.festival_ten},
            status = 1,admin = 1,name = "萌萌新")
    @Filter(value = {"本群 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void OFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.special_one,
            FunEnum.special_ten,FunEnum.custom_one,FunEnum.custom_ten,
            FunEnum.high_one,FunEnum.high_ten,FunEnum.se_count,FunEnum.ai_count,
            FunEnum.eq_count,FunEnum.aq_count,FunEnum.sign_count,FunEnum.up_count,
            FunEnum.festival_one,FunEnum.festival_ten},
            status = 0,admin = 1,name = "萌萌新")
    @Filter(value = {"本群 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void ON(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.json_flush},status = 1,name = "刷新")
    @Filter(value = {"刷新 关"})
    @Listen(MsgGetTypes.groupMsg)
    public void flushOFF(GroupMsg msg, MsgSender sender){
    }

    @Switch(types = {FunEnum.json_flush},status = 0,name = "刷新")
    @Filter(value = {"刷新 开"})
    @Listen(MsgGetTypes.groupMsg)
    public void flushON(GroupMsg msg, MsgSender sender){
    }
}
