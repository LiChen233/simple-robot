package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.SpringRunApplication;
import com.forte.demo.bean.Person;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.dao.PersonDao;
import com.forte.demo.dao.PrayDao;
import com.forte.demo.dao.power.groupPower.GroupPowerDao;
import com.forte.demo.emun.FunEnum;
import com.forte.demo.service.PrayService;
import com.forte.demo.service.QqGroupService;
import com.forte.demo.service.power.adminPower.AdminPowerService;
import com.forte.demo.service.power.groupPower.GroupPowerService;
import com.forte.demo.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class EquipsTest {

    @Test
    public void test(){

    }
}
