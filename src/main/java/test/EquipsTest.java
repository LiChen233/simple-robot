package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

    @Test
    public void test() throws IOException {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH,30);
        Date after = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
        System.out.println(sdf.format(after));
        Integer isSender = RandomNum.randomNumber(0, 100);
        System.out.println(isSender);
    }

    @Test
    public void test1() throws IOException {
        /**
         * 衣服，武器，徽章
         * 是否有觉醒
         * 是否有二技能
         * 能否进化
         * 能否kira
         * 仅有一个kira
         */
        Date date1 = new Date();
        FindEquipsUtils.findEq("828");
        Date date2 = new Date();
        System.out.println("运行时间："+(date2.getTime() - date1.getTime())/1000+" 秒");
    }

    @Test
    public void test2() throws IOException {

    }
}
