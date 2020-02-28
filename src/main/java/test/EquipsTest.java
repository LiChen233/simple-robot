package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

public class EquipsTest {

    /*public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            new Thread() {
                public void run() {
                    download();
                }
            }.start();
        }
    }*/

    public static void download(){
        int err = 0;
        for (int i = 0; i < 100; i++) {
            try {
                long start = System.currentTimeMillis();
                String imgPath = "https://api.lolicon.app/setu/?size1200&r18=1";
                //加载色图
                Document doc = Jsoup.connect(imgPath).header("User-Agent",
                        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                        .ignoreContentType(true)
                        //.header("referer", "https://api.lolicon.app/setu/view.html")
                        .timeout(30000)
                        .get();

                String result = doc.select("body").text();
                JSONObject json = JSONObject.parseObject(result);

                //成功
                String imgUrl = json.getJSONArray("data").getJSONObject(0).getString("url");

                //下载图片
                URL url = new URL(imgUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //伪造成浏览器
                conn.addRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                //伪造referer
                //conn.setRequestProperty("referer", "https://api.lolicon.app/setu/view.html");
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
                long over = System.currentTimeMillis();
                System.out.println("图片下载完成，共使用 " + (over - start) + " ms");
            }catch (Exception e){
                System.out.println("报错:"+e.getMessage());
                err++;
            }
        }
        System.err.println("线程执行完毕，失败率"+err+"%");
    }
}
