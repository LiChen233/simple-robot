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
    private static final Integer ONE = 1;


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

    /**
     * 大小姐单抽+1
     */
    @Override
    public void middle_one_add() {
        countDao.middle_one_add(getDate());
    }

    @Override
    public void middle_ten_add() {
        countDao.middle_ten_add(getDate());
    }

    @Override
    public void special_one_add() {
        countDao.special_one_add(getDate());
    }

    @Override
    public void special_ten_add() {
        countDao.special_ten_add(getDate());
    }

    @Override
    public void custom_one_add() {
        countDao.custom_one_add(getDate());
    }

    @Override
    public void custom_ten_add() {
        countDao.custom_ten_add(getDate());
    }

    @Override
    public void high_one_add() {
        countDao.high_one_add(getDate());
    }

    @Override
    public void high_ten_add() {
        countDao.high_ten_add(getDate());
    }

    @Override
    public void ai_count_add() {
        countDao.ai_count_add(getDate());
    }

    @Override
    public void eq_count_add() {
        countDao.eq_count_add(getDate());
    }

    @Override
    public void aq_count_add() {
        countDao.aq_count_add(getDate());
    }

    @Override
    public void se_count_add() {
        countDao.se_count_add(getDate());
    }
}
