package test;

import com.forte.demo.SpringRunApplication;
import com.forte.demo.dao.PersonDao;
import com.forte.demo.dao.PrayDao;
import com.forte.demo.service.power.count.CountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class MapperTest {

    @Autowired
    CountService countService;

    @Test
    public void test1(){

    }

}

