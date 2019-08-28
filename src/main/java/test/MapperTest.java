package test;

import com.forte.demo.SpringRunApplication;
import com.forte.demo.bean.Person;
import com.forte.demo.bean.Pray;
import com.forte.demo.mapper.PersonDao;
import com.forte.demo.mapper.PrayDao;
import com.forte.demo.utils.PrayEnum;
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
        Person person = personDao.getPerson("2943226427");
        System.out.println(person);

        Pray pray = prayDao.getPray("2943226427");
        System.out.println(pray);

        personDao.addStar("2943226427",10);

        personDao.reduceStar("2943226427",10);
    }
}
