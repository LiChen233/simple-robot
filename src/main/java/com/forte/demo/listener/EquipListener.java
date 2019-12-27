package com.forte.demo.listener;

import com.forte.demo.anno.Check;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.utils.EquipsUPUtils;
import com.forte.demo.utils.FindEquipsUtils;
import com.forte.demo.utils.PrayUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 查询up记录
 */
@Component
public class EquipListener {

    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"查询往期up功能","上次up","上一次up","上次什么时候up","上一次什么时候up","什么时候up","up记录"})
    @Listen(MsgGetTypes.groupMsg)
    public void listenUp(GroupMsg msg, MsgSender sender){
        sender.SENDER.sendGroupMsg(msg.getGroup(),"查询装备最近三次up时间：\n" +
                "公主/魔女/魔法少女/使魔up+空格+装备名称(尽量精确名字)\n" +
                "---例---\n" +
                "公主up 犹大的誓约");
    }

    @Check(type = FunEnum.up_count)
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

    @Check(type = FunEnum.eq_count)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"装备查询","查询装备"})
    @Listen(MsgGetTypes.groupMsg)
    public void searchEq(GroupMsg msg, MsgSender sender) throws IOException {
        String str = msg.getMsg();
        //拿到后面的内容
        str = str.substring(4).trim();
        if ("".equals(str)){
            return;
        }
        if ("0".equals(str)){
            sender.SENDER.sendGroupMsg(msg.getGroup(), "没有id为0的装备，请回吧");
            return;
        }
        //判断是id还是搜索内容
        try {
            //拿到装备id
            str = new BigDecimal(str).toString();
            //拿到拼接觉醒图的最终成品
            String equip = FindEquipsUtils.findEq(str);
            if ("undefined".equals(equip)){
                sender.SENDER.sendGroupMsg(msg.getGroup(), "暂不支持查询使魔");
            }
            File file = new File(equip);
            String cqCode_image = CQCodeUtil.build().
                    getCQCode_image("file://" + file.getAbsolutePath());
            sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_image);
            file.delete();
        } catch (Exception e) {
            //异常 说明包含非数字。则用来搜索
            String search = FindEquipsUtils.search(str);
            if (null==search){
                sender.SENDER.sendGroupMsg(msg.getGroup(),
                        "搜索结果超过20个的活我可不干，换一个准点的关键字再来吧");
            }else if ("undefined".equals(search)){
                sender.SENDER.sendGroupMsg(msg.getGroup(), "啥都没找到");
            }else{
                File file = new File(search);
                String cqCode_image = CQCodeUtil.build().
                        getCQCode_image("file://" + file.getAbsolutePath());
                sender.SENDER.sendGroupMsg(msg.getGroup(), cqCode_image);
                file.delete();
            }
        }
    }

    @Check(type = FunEnum.json_flush)
    @Filter(value = "刷新数据")
    @Listen(MsgGetTypes.groupMsg)
    public void flushJson(GroupMsg msg, MsgSender sender) {
        try {
            //使用Jsoup获取搞事学园的祈愿概率json，并写入文件中，以免更换接口导致祈愿不能使用
            Document doc = Jsoup.connect("https://api.redbean.tech/illustrate/all?server=merged")
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .get();
            String result = doc.select("html").text();

            //若是json拿不到，就返回，以免覆写文件
            if ("".equals(result.trim())){
                return;
            }

            //写入文件，默认文件永久存在故不做异常处理，写入模式：覆盖
            File jsonFile = new File("src/static/Illustrate.txt");
            FileOutputStream fos = new FileOutputStream(jsonFile);
            fos.write(result.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();

            PrayUtils.flushJson();

            sender.SENDER.sendGroupMsg(msg.getGroup(), "刷新成功");
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(msg.getGroup(), "刷新失败");
        }
    }
}
