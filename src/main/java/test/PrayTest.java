package test;

import com.forte.demo.SpringRunApplication;
import com.forte.demo.service.PrayService;
import com.forte.demo.utils.PrayUtils;
import com.forte.plusutils.consoleplus.ConsolePlus;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.plusutils.consoleplus.console.ColorsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class PrayTest {

    @Autowired
    PrayService prayService;

    @Test
    public void test1() throws IOException {
        PrayUtils.flushJson();
    }

    /**
     * 彩色控制台
     * @throws UnsupportedEncodingException
     * @throws IllegalAccessException
     */
    @Test
    public void testColor() throws UnsupportedEncodingException, IllegalAccessException {
        ColorsBuilder builder = Colors.builder();
        Colors colors = builder.add("123456789")
                .add("金光", Colors.FONT.YELLOW)
                .add("紫光",Colors.FONT.PURPLE)
                .add("蓝光",Colors.FONT.BLUE)
                .build();
        System.out.println(colors);

        //这个方法是直接覆盖系统自带的输出
        ConsolePlus.updatePrintOut(s -> ColorsBuilder.getInstance().add(s, Colors.FONT.YELLOW).build().toString());
        System.out.println("颜色!");
    }
}
