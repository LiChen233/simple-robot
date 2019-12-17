package com.forte.demo.service.power.groupPower;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.dao.power.groupPower.GroupPowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupPowerServiceImpl implements GroupPowerService{
    @Autowired
    GroupPowerDao groupPowerDao;

    @Override
    public GroupPower find(GroupPower groupPower) {
        return groupPowerDao.selectOne(new QueryWrapper<>(groupPower));
    }

    @Override
    public void update(GroupPower groupPower) {
        groupPowerDao.updateById(groupPower);
    }

    @Override
    public void insert(GroupPower groupPower) {
        groupPowerDao.insert(groupPower);
    }
}
