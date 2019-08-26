package com.forte.demo.listener;

import cn.xsshome.taip.nlp.TAipNlp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.mapper.MessageMapper;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
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
     * */
    @Depend
    private MessageMapper mapper;

    public static final String APP_ID = "2121061546";
    public static final String APP_KEY = "hTAA8RaUJj0qipEA";



    @Listen(MsgGetTypes.privateMsg)
    public void priMsg(PrivateMsg privateMsg, MsgSender sender) throws Exception {
        //记录消息
        String msg = privateMsg.getMsg();
        TAipNlp aipNlp = new TAipNlp(APP_ID, APP_KEY);
        String session = System.currentTimeMillis()/1000+"";

        //基于腾讯ai的基础闲聊
        String result = aipNlp.nlpTextchat(session,msg);

        JSONObject json = JSON.parseObject(result);
        JSONObject data = (JSONObject) json.get("data");
        String answer = (String) data.get("answer");
        sender.SENDER.sendPrivateMsg(privateMsg.getQQ(),answer);
    }


}
