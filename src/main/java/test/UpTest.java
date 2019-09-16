package test;

import cn.xsshome.taip.ocr.TAipOcr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.utils.EquipsUPUtils;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UpTest {

    private static final String APP_ID = "2121061546";
    private static final String APP_KEY = "hTAA8RaUJj0qipEA";
    private static final String URL = "https://api.redbean.tech/illustrate/all?server=merged";

    @Test
    public void test1() throws IOException {
        String equip = "罪之石";
        ArrayList<String> allUp = EquipsUPUtils.getAllUp(EquipsUPUtils.NAME[1],equip);
        if (allUp.size()==0){
            String petName = EquipsUPUtils.getPet(equip);
            if (petName==null){
                System.out.println("查询无果");
                return;
            }
            allUp = EquipsUPUtils.getAllUp(EquipsUPUtils.NAME[1],petName);
        }
        for (String startTime : allUp) {
            System.out.println(startTime);
        }
    }

    @Test
    public void test2() throws Exception {
        TAipOcr ocr = new TAipOcr(APP_ID,APP_KEY);
        String s = ocr.generalOcr("src/static/0.jpg");
        System.out.println(s);

        JSONObject json = JSON.parseObject(s);
        JSONObject data = json.getJSONObject("data");
        JSONArray item_list = data.getJSONArray("item_list");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < item_list.size(); i++) {
            JSONObject inJson = item_list.getJSONObject(i);
            str.append(inJson.getString("itemstring")+"\n");
        }
        System.out.println(str.toString());
    }

    private void getJson() throws IOException {
        //使用Jsoup获取搞事学园的祈愿概率json，并写入文件中，以免更换接口导致祈愿不能使用
        Document doc = Jsoup.connect(URL).header("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .ignoreContentType(true)
                .timeout(60000)
                .get();
        String result = doc.select("body").text();

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

    }

    @Test
    public void test3() throws Exception {
        getJson();
    }
}
