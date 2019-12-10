package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.utils.FindEquipsUtils;
import com.forte.demo.utils.ReadJsonFileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class EquipsTest {

    @Test
    public void test1(){
        //FindEquipsUtils.start("3470");
        //FindEquipsUtils.synthesis();
        try {
            //设置url
            URL url = new URL("https://static.image.mihoyo.com/hsod2_webview/images/broadcast_top/equip_icon/png/3188.png");
            //用HttpURLConnection打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3 * 1000);
            InputStream fis = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Chen\\Desktop\\2.jpg");
            byte[] buffer = new byte[1024];
            while ((fis.read(buffer))!=-1){
                fos.write(buffer);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws IOException {
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
    }
}
