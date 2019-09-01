package test;

import com.forte.demo.SpringRunApplication;
import com.forte.demo.service.PersonService;
import com.forte.qqrobot.anno.depend.Depend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class StarTest {


    @Autowired
    PersonService target;

    @Test
    public void drawLots(){
        System.out.println(target.getPerson("599906780").getStar());
    }
}
