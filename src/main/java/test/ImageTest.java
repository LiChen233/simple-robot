package test;

import cn.xsshome.taip.nlp.TAipNlp;
import com.forte.demo.utils.TAipUtils;
import org.junit.Test;

public class ImageTest {


    @Test
    public void test1() throws Exception {
        TAipNlp taip = TAipUtils.getTAIP();
        String res = taip.nlpTextchat("1", "成语接龙");
        String answer = TAipUtils.getAnswer(res);
        System.out.println(answer);
        String res2 = taip.nlpTextchat("1", "异想天开");
        String answer2 = TAipUtils.getAnswer(res2);
        System.out.println(answer2);
    }
}
