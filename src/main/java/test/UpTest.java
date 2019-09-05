package test;

import com.forte.demo.utils.EquipsUP;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class UpTest {

    @Test
    public void test1() throws IOException {
        String equip = "罪之石";
        ArrayList<String> allUp = EquipsUP.getAllUp(EquipsUP.NAME[1],equip);
        if (allUp.size()==0){
            String petName = EquipsUP.getPet(equip);
            if (petName==null){
                System.out.println("查询无果");
                return;
            }
            allUp = EquipsUP.getAllUp(EquipsUP.NAME[1],petName);
        }
        for (String startTime : allUp) {
            System.out.println(startTime);
        }
    }

    @Test
    public void test2(){
        EquipsUP.flushJson();
    }
}
