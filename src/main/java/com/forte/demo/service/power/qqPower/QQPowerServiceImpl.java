package com.forte.demo.service.power.qqPower;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.bean.power.qqPower.QQPower;
import com.forte.demo.dao.power.qqPower.QQPowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QQPowerServiceImpl implements QQPowerService {
    @Autowired
    QQPowerDao qqPowerDao;

    @Override
    public QQPower find(QQPower qqPower) {
        return qqPowerDao.selectOne(new QueryWrapper<>(qqPower));
    }

    @Override
    public void update(QQPower qqPower) {
        qqPowerDao.updateById(qqPower);
    }

    @Override
    public void insert(QQPower qqPower) {
        qqPowerDao.insert(qqPower);
    }
}
