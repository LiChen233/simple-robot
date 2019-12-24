package com.forte.demo.listener.power;

import com.forte.demo.anno.Ban;
import com.forte.demo.emun.FunEnum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限操作
 * 1.开启/关闭 本群 某功能
 * 2.封禁/解封 某人的使用权限
 * 3.
 */
@Component
public class PowerListener {

    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten},status = 1,admin = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 扭蛋"})
    @Listen(MsgGetTypes.groupMsg)
    public void banPray(GroupMsg msg, MsgSender sender){

    }

    @Ban(types = {FunEnum.middle_one,FunEnum.middle_ten,FunEnum.custom_one,
            FunEnum.custom_ten,FunEnum.special_one,FunEnum.special_ten,
            FunEnum.high_one,FunEnum.high_ten},status = 0,admin = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 扭蛋"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanPray(GroupMsg msg, MsgSender sender){

    }

    @Ban(types = {FunEnum.se_count},status = 1,admin = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#禁用 色图"})
    @Listen(MsgGetTypes.groupMsg)
    public void BanSe(GroupMsg msg, MsgSender sender){

    }

    @Ban(types = {FunEnum.se_count},status = 0,admin = 0)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"#启用 色图"})
    @Listen(MsgGetTypes.groupMsg)
    public void NotBanSe(GroupMsg msg, MsgSender sender){

    }
}
