package com.forte.demo.listener.power;

import com.forte.demo.bean.QqGroup;
import com.forte.demo.service.QqGroupService;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SurpriseListener {

    @Autowired
    QqGroupService groupService;

    private static Map<String, Date> MAP;

    /**
     * 每次有群发言，则更新一下该群的发言时间
     */
    @Listen(MsgGetTypes.groupMsg)
    public void leader(GroupMsg msg, MsgSender sender){
        Map<String, Date> map = getMap();
        map.put(msg.getGroup(),new Date());
    }

    public Map<String, Date> getMap(){
        if (null == MAP){
            Date now = new Date();
            MAP = new HashMap<>();
            ArrayList<QqGroup> groups = groupService.getAllGroup();
            for (QqGroup group : groups) {
                MAP.put(group.getGroupid(),now);
            }
        }
        return MAP;
    }
}
