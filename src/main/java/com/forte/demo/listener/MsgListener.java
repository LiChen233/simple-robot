package com.forte.demo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.bean.TAipUtils;
import com.forte.demo.mapper.MessageMapper;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;

/**
 *
 * 这是一个监听器，他监听一个私信消息，并将其保存至数据库中。
 * 一个监听器类尽量不要同时携带@Beans和@Component注解，如果都要携带的话，
 * 则以Spring方面的注解为主要注解，包括字段上的注解也替换为@Autowired
 * 毕竟是优先使用Spring获取
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class MsgListener {

    /**
     * 字段所使用的@Depend注解为在本框架中使用
     * 而@Autowired则在Spring中被使用
     * 请注意不要搞混了
     *
     * 这个mapper是群主写的一个访问数据库的类，用不到
     * */
    @Depend
    private MessageMapper mapper;

    /**
     * 这个是监听私聊的方法，@Listen注解括号里的是枚举，专门用来监听私聊消息的
     * @param privateMsg 这个是私聊过来的消息
     * @param sender 这个是发送数据的
     * @throws Exception
     */
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

    /**
     * 监听群消息
     * @param msg
     * @param sender
     */
    @Listen(MsgGetTypes.groupMsg)
    public void groupMsg(GroupMsg msg, MsgSender sender) throws Exception {

        String result = TAipUtils.getTAIP()
                .nlpTextchat(TAipUtils.getSession(),msg.getMsg());

        boolean b = sender.SENDER.sendGroupMsg(msg.getGroup(), TAipUtils.getAnswer(result));
        System.out.println(b);
    }

}
