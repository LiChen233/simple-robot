package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.SpringRunApplication;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class Img2Base64UtilTest {

    public static void main(String[] args) throws IOException {

        String url = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.toString());






//        String api = "http://www.dmoe.cc/random.php?return=json";
//        Document doc = Jsoup.connect(api).get();
//        String ll = doc.toString();
//        String url = ll.substring(ll.indexOf('(')+1,ll.lastIndexOf(')'));
//        System.out.println(url);


    }

    @Test
    public void test() throws IOException {
        String url = "https://bing.biturl.top/?resolution=1920&format=json&index=0&mkt=zh-CN";
//        String url = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String xx = doc.select("body").text();
        JSONObject json = JSON.parseObject(xx);
        System.out.println(json.get("url"));
    }




}
