package com.forte.demo.listener;

import com.alibaba.fastjson.JSONObject;
import com.forte.demo.anno.Check;
import com.forte.demo.bean.Person;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.PersonService;
import com.forte.demo.utils.RandomNum;
import com.forte.demo.utils.UuidUtils;
import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.forte.qqrobot.utils.RandomUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /**
     * 来份色图接口，旧版
     */
    /*@Check(type = FunEnum.se_count,cost = 10)
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

    }*/

    /**
     * 来份色图接口，新的，但是性能不太好
     */
    @Check(type = FunEnum.se_count,cost = 10)
    @Listen(value = MsgGetTypes.groupMsg)
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS,value = {"来份色图","#来份色图","r来份色图"})
    public synchronized void forColorImage(GroupMsg groupMsg, MsgSender sender){
        StringBuilder imgPath = new StringBuilder("https://api.lolicon.app/setu/?size1200");
        String msg = groupMsg.getMsg();
        int indexOf = msg.indexOf("来份色图");
        if (indexOf >= 2){
            return;
        }
        if ("#".equals(msg.substring(0,1))){
            imgPath.append("&r18=2");
        }else if ("r".equals(msg.substring(0,1))){
            imgPath.append("&r18=1");
        }
        String key = msg.substring(indexOf + 4).trim();
        if (key.length()>0){
            imgPath.append("&keyword=").append(key);
        }

        CQCode at = CQCodeUtil.build().getCQCode_At(groupMsg.getQQ());
        try{
            System.out.println(imgPath);
            //加载色图
            Document doc = Jsoup.connect(imgPath.toString()).header("User-Agent",
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                    .ignoreContentType(true)
                    .timeout(30000)
                    .get();

            String result = doc.select("body").text();
            JSONObject json = JSONObject.parseObject(result);
            Integer code = json.getInteger("code");
            if (code==0){
                //成功
                String imgUrl = json.getJSONArray("data").getJSONObject(0).getString("url");
                System.out.println(imgUrl);

                sender.SENDER.sendGroupMsg(groupMsg.getGroup()," 色图加载中...");

                //下载图片
                URL url = new URL(imgUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //伪造成浏览器
                conn.addRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                //伪造referer
                conn.setRequestProperty("referer", "www.pixiv.net");
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                //文件名字
                String filePath = "./temp/" + UuidUtils.getUuid() + ".png";
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] buffer = new byte[1024*8];
                int count;
                while ((count = in.read(buffer)) > 0) {
                    out.write(buffer, 0, count);
                }
                //关闭流
                out.close();
                in.close();
                conn.disconnect();

                File file = new File(filePath);
                String img = CQCodeUtil.build().getCQCode_image("file://"+ file.getAbsolutePath());
                System.out.println(img);
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+img);
                //file.delete();
            }else if (code==1){
                //没有符合关键字的色图
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" 没有符合关键字的色图");
            }else if(code==-1){
                //内部错误，请向 i@loli.best 反馈
                sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at.toString()+" 色图接口错误，图被吞了XD");
            }
        }catch (Exception e){
            sender.SENDER.sendGroupMsg(groupMsg.getGroup(),at+" 由于网络波动，色图加载失败！请重试 0.0");
            e.printStackTrace();
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
