package com.forte.demo.listener;

import com.forte.demo.utils.TAipUtils;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;

@Beans
public class MsgListener {

    @Listen(MsgGetTypes.privateMsg)
    public void priMsg(PrivateMsg privateMsg, MsgSender sender) throws Exception {
        //记录消息
        String msg = privateMsg.getMsg();
        //从TAip工具类拿单例
        String result = TAipUtils.getTAIP()
                .nlpTextchat(TAipUtils.getSession(),msg);
        //从工具类直接拿到ai的回答
        //发送私信，两个参数一个QQ号一个文本
        sender.SENDER.sendPrivateMsg(privateMsg.getQQ(),TAipUtils.getAnswer(result));
    }

}
