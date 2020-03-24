package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.SpringRunApplication;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.dao.power.groupPower.GroupPowerDao;
import com.forte.demo.emun.FunEnum;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class EquipsTest {
    @Autowired
    GroupPowerDao groupPowerDao;
    @Autowired
    QqGroupService qqGroupService;

    @Test
    public void test(){
        ArrayList<QqGroup> allGroup = qqGroupService.getAllGroup();
        for (QqGroup group : allGroup) {
            GroupPower groupPower = GroupPower.builder()
                    .fun_id(15)
                    .qq_group(group.getGroupid())
                    .build();
            GroupPower one = groupPowerDao.selectOne(new QueryWrapper<>(groupPower));
            if (null==one){
                groupPower.setStatus(0);
                groupPowerDao.insert(groupPower);
            }

            GroupPower groupPower2 = GroupPower.builder()
                    .fun_id(16)
                    .qq_group(group.getGroupid())
                    .build();
            GroupPower two = groupPowerDao.selectOne(new QueryWrapper<>(groupPower2));
            if (null==two){
                groupPower2.setStatus(0);
                groupPowerDao.insert(groupPower2);
            }
        }
    }
}
