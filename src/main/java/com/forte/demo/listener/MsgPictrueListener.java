package com.forte.demo.listener;

import com.forte.demo.anno.Check;
import com.forte.demo.bean.Person;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.PersonService;
import com.forte.demo.utils.RandomNum;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;

/**
 * 壁纸/色图监听
 */

@Component
public class MsgPictrueListener {


    //来份色图API
    private static final String FORCOLORIMAGEURL[] =  {
            //"https://api.moonwl.cn/api/tu/acg.php",  //接口已凉 瑶:后续测试接口还能用
            "https://api.w0ai1uo.org/api/dongman/",
            "http://api.mtyqx.cn/tapi/random.php",
            "http://api.btstu.cn/sjbz/?lx=dongman"
    };
    //来份壁纸API
    private static final String FORWALLPAPER[] = {
            "https://acg.yanwz.cn/wallpaper/api.php",
            "http://api.btstu.cn/sjbz/?lx=suiji",
            "https://api.yimian.xyz/img?type=moe&size=1920x1080"
    };
    //手机壁纸API
    private static final String PHONEWALLPAPER = "http://api.btstu.cn/sjbz/?lx=m_dongman";

    @Autowired
    PersonService personService;

    @Check(type = FunEnum.se_count,cost = 10)
    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = {"来份色图","来分色图","来张色图","来份涩图","来分涩图","来张涩图"})
    public synchronized void forColorImage(GroupMsg groupMsg, MsgSender sender) throws IOException {
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        try{
            Integer i = (int) (Math.random()*FORCOLORIMAGEURL.length);
            CQCode img = CQCodeUtil.build().getCQCode_Image(FORCOLORIMAGEURL[i]);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+img);
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，色图加载失败！请重试 0.0");
            throw new RuntimeException("色图加载失败",e);
        }

    }

    @Check(type = FunEnum.se_count,cost = 10)
    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(value = {"来份壁纸","来张壁纸","一张壁纸","电脑壁纸"})
    public synchronized void forWallpaper(GroupMsg groupMsg, MsgSender sender) throws IOException {
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        try{
            Integer i = (int) (Math.random()*FORWALLPAPER.length);
            CQCode wallpaper = CQCodeUtil.build().getCQCode_Image(FORWALLPAPER[i]);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+wallpaper);
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，壁纸加载失败！请重试 0.0");
            throw new RuntimeException("壁纸加载失败",e);
        }

    }

    @Check(type = FunEnum.se_count,cost = 10)
    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"来份手机壁纸","手机壁纸","来张手机壁纸"})
    public synchronized void phoneWallpaper(GroupMsg groupMsg,MsgSender sender){
        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        try {
            String phoneImage = CQCodeUtil.build().getCQCode_image(PHONEWALLPAPER);
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+phoneImage);
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，壁纸加载失败！请重试 0.0");
            throw new RuntimeException("加载手机壁纸异常！",e);
        }
    }
}
