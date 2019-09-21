package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.SpringRunApplication;
import com.forte.demo.service.PersonService;
import com.forte.demo.utils.Img2Base64Util;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class Img2Base64UtilTest {

    @Autowired
    private PersonService target;

    @Test
    public void test() throws IOException {
        String url = "https://bing.biturl.top/?resolution=1920&format=json&index=0&mkt=zh-CN";
//        String url = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String xx = doc.select("body").text();
        JSONObject json = JSON.parseObject(xx);
        System.out.println(json.get("url"));
    }

    @Test
    public void random(){
        System.out.print("请输入总的分钟：");
        Scanner scanner = new Scanner(System.in);
        int totalMinutes = scanner.nextInt();
        //计算天 1天 = 24*60 1小时=60
        int day = totalMinutes/(24*60);
        int hour = (totalMinutes%(24*60))/60;
        int minute = (totalMinutes%(24*60))%60;
        System.out.println(day+"天"+hour+"小时"+minute+"分");

    }

    public static void main(String[] args) {
        System.out.print("请输入总的分钟：");
        Scanner scanner = new Scanner(System.in);
        int totalMinutes = scanner.nextInt();
        //计算天 1天 = 24*60 1小时=60
        int day = totalMinutes/(24*60);
        int hour = (totalMinutes%(24*60))/60;
        int minute = (totalMinutes%(24*60))%60;
        System.out.println(day+"天"+hour+"小时"+minute+"分");
    }




}
