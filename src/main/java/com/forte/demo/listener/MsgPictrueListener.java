package com.forte.demo.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;

/**
 * 壁纸/色图监听
 */

@Beans
public class MsgPictrueListener {


    //来份色图API
    private static final String FORCOLORIMAGEURL =  "http://www.dmoe.cc/random.php?return=json";
    //来份壁纸API
    private static final String FORWALLPAPER =  "https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302";


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份色图")
    public void forColorImage(GroupMsg groupMsg, MsgSender sender) throws IOException {
        Document doc = Jsoup.connect(FORCOLORIMAGEURL).get();
        String docJson = doc.toString();
        String url = docJson.substring(docJson.indexOf('(')+1,docJson.lastIndexOf(')'));
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String c = CQCodeUtil.build().getCQCode_image(url);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+c);
    }


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份壁纸")
    public void forWallpaper(GroupMsg groupMsg, MsgSender sender) throws IOException {
        String wallpaper = CQCodeUtil.build().getCQCode_image(FORWALLPAPER);
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+wallpaper);
    }

}
