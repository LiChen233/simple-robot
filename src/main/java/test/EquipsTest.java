package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.utils.FindEquipsUtils;
import com.forte.demo.utils.ReadJsonFileUtils;
import com.forte.demo.utils.UuidUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

public class EquipsTest {

    @Test
    public void test1() throws IOException {
        FindEquipsUtils.findEq("3567");
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
