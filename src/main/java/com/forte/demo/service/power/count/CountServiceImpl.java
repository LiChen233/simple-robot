package com.forte.demo.service.power.count;

import com.forte.demo.bean.power.count.Count;
import com.forte.demo.dao.power.count.CountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class CountServiceImpl implements CountService {
    @Autowired
    CountDao countDao;

    private static final Integer ZERO = 0;

    private String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 日志初始化新的一天
     */
    @Override
    public void newDay() {
        Count count = Count.builder()
                .id(getDate())
                .middleOne(ZERO)
                .middleTen(ZERO)
                .specialOne(ZERO)
                .specialTen(ZERO)
                .customOne(ZERO)
                .customTen(ZERO)
                .highOne(ZERO)
                .highTen(ZERO)
                .aiCount(ZERO)
                .eqCount(ZERO)
                .aqCount(ZERO)
                .seCount(ZERO)
                .build();

        countDao.insert(count);
    }

    @Override
    public void increase(Count count) {
        count.setDay(getDate());
        countDao.increase(count);
    }
}
