package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyTest {

    //设置APPID/APP_KEY
    public static final String APP_ID = "2121061546";
    public static final String APP_KEY = "hTAA8RaUJj0qipEA";
    public static final String GAO = "https://hsod2.hongshn.xyz/gacha/data";

    @Test
    public void test1() throws Exception {
        //使用Jsoup获取搞事学园的祈愿概率json，并写入文件中，以免更换接口导致祈愿不能使用
        Document doc = Jsoup.connect(GAO).header("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .ignoreContentType(true)
                .timeout(60000)
                .get();
        String result = doc.select("body").text();

        //写入文件，默认文件永久存在故不做异常处理，写入模式：覆盖
        File jsonFile = new File("src/static/PrayJson.txt");
        FileOutputStream fos = new FileOutputStream(jsonFile);
        fos.write(result.getBytes(StandardCharsets.UTF_8));
        fos.flush();
        fos.close();

        //读取文件中的数据
        FileInputStream fis = new FileInputStream(jsonFile);
        int len;
        byte[] buffer = new byte[1024];
        StringBuilder pray = new StringBuilder();
        while ((len=fis.read(buffer))!=-1){
            pray.append(new String(buffer,0,len, StandardCharsets.UTF_8));
        }
        fis.close();
        System.out.println("从文本中读取json："+pray);


        //转成对象以备使用
        JSONObject json = JSON.parseObject(pray.toString());
        System.out.println("将字符串转成json："+json.toString());

    }

}