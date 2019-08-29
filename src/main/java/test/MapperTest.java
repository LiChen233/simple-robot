package test;

import com.forte.demo.SpringRunApplication;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.mapper.PrayDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRunApplication.class)
public class MapperTest {

    @Autowired
    PersonDao personDao;
    @Autowired
    PrayDao prayDao;

    @Test
    public void test1(){
        prayDao.resetBaodi("highbaodi","2943226427");
    }
}
