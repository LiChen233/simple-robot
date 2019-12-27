package com.forte.demo.service.power.count;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.bean.QqGroup;
import com.forte.demo.bean.power.count.Count;
import com.forte.demo.dao.power.count.CountDao;
import com.forte.demo.service.QqGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class CountServiceImpl implements CountService {
    @Autowired
    CountDao countDao;
    @Autowired
    QqGroupService qqGroupService;

    private static final Integer ZERO = 0;

    private String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 日志初始化新的一天
     */
    @Override
    public void newDay() {
        /*ArrayList<QqGroup> groups = qqGroupService.getAllGroup();
        Count count = Count.builder()
                .today(getDate())
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
                .signCount(ZERO)
                .upCount(ZERO)
                .build();
        for (QqGroup group : groups) {
            count.setQqGroup(group.getGroupid());
            countDao.insert(count);
        }*/
        System.out.println(123);
    }

    /**
     * 当前功能日志记录加一
     * @param count
     */
    @Override
    public void increase(Count count) {
        //AOP传进来的是没有日期和id的
        count.setToday(getDate());
        Count temp = countDao.selectOne(new QueryWrapper<>(count));
        if (null!=temp){
            count.setId(temp.getId());
            countDao.increase(count);
        }else {
            System.out.println("每日日志还未初始化就有人插入数据了");
        }
    }
}
