package com.forte.demo.listener;

import com.forte.demo.utils.EquipsUPUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;

import java.util.ArrayList;

/**
 * 查询up记录
 */
@Beans
public class UpRecordListener {

    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"上次up","上一次up","上次什么时候up","上一次什么时候up","什么时候up","up记录"})
    @Listen(MsgGetTypes.groupMsg)
    public void listenUp(GroupMsg msg, MsgSender sender){
        sender.SENDER.sendGroupMsg(msg.getGroup(),"查询装备最近三次up时间：\n" +
                "公主/魔女/魔法少女/使魔up+空格+装备名称(尽量精确名字)\n" +
                "--例--\n" +
                "公主up 犹大的誓约");
    }

    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"公主up","魔女up","魔法少女up","使魔up"})
    @Listen(MsgGetTypes.groupMsg)
    public void searchUp(GroupMsg msg, MsgSender sender){
        String groupMsg = msg.getMsg();
        if (groupMsg.substring(0,2).equals("查询")){
            groupMsg = groupMsg.substring(2);
        }
        if (!groupMsg.substring(2,5).equals("up ")){
            if (!groupMsg.substring(4,7).equals("up ")){
                return;
            }
        }
        String type = groupMsg.substring(0,2);
        String question = groupMsg.substring(4).trim();
        Integer index = 0;
        if (type.equals("公主")){
            index = 0;
        }else if (type.equals("魔女")){
            index = 1;
        }else if (type.equals("魔法")){
            if (groupMsg.substring(0,4).equals("魔法少女")){
                index = 2;
                type = groupMsg.substring(0,4);
                question = groupMsg.substring(6).trim();
            }
        }else if (type.equals("使魔")){
            index = 3;
        }
        ArrayList<String> allUp = EquipsUPUtils.searchUp(index, question);

        if (allUp==null){
            sender.SENDER.sendGroupMsg(msg.getGroup(),"没有查到任何记录呢...");
            return;
        }
        StringBuilder str = new StringBuilder();
        str.append("查询"+type+"up“"+question+"”:");
        if (allUp.size()<3){
            for (String s : allUp) {
                str.append("\n"+s);
            }
        }else {
            for (int i = allUp.size()-1; i > allUp.size()-4; i--) {
                str.append("\n"+allUp.get(i));
            }
        }
        sender.SENDER.sendGroupMsg(msg.getGroup(),str.toString());
    }
}
