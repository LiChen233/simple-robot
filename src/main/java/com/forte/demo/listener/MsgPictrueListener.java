package com.forte.demo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.utils.Img2Base64Util;
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
import org.jsoup.nodes.Element;


import java.io.IOException;

@Beans
public class MsgPictrueListener {



    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份色图")
    public void forColorImage(GroupMsg groupMsg, MsgSender sender) throws IOException {
        String api = "http://www.dmoe.cc/random.php?return=json";
        Document doc = Jsoup.connect(api).get();
        String ll = doc.toString();
        String url = ll.substring(ll.indexOf('(')+1,ll.lastIndexOf(')'));
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        String c = CQCodeUtil.build().getCQCode_image(url);
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+c);
    }


    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = "来份壁纸")
    public void forWallpaper(GroupMsg groupMsg, MsgSender sender) throws IOException {
        //String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        String url = "https://bing.biturl.top/?resolution=1920&format=json&index=0&mkt=zh-CN";
        Document doc = Jsoup.connect(url).header("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .ignoreContentType(true)
                .timeout(60000)
                .get();
//        String body = doc.select("body").text();
//        JSONObject json = JSON.parseObject(body);
//        JSONArray images = (JSONArray) json.get("images");
//        JSONObject jsonObject = (JSONObject)images.get(0);
//        String imageurl = (String)jsonObject.get("url");
//        String imagePath = "https://www.bing.com/"+imageurl;
        //String wallpaper = CQCodeUtil.build().getCQCode_image(imagePath);
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString());
    }





}
