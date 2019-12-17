package com.forte.demo.service.power.adminPower;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forte.demo.bean.power.adminPower.AdminPower;
import com.forte.demo.dao.power.adminPower.AdminPowerDao;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminPowerServiceImpl implements AdminPowerService{
    @Autowired
    AdminPowerDao adminPowerDao;

    @Override
    public AdminPower find(AdminPower adminPower) {
        return adminPowerDao.selectOne(new QueryWrapper<>(adminPower));
    }

    @Override
    public void update(AdminPower adminPower) {
        adminPowerDao.updateById(adminPower);
    }

    @Override
    public void insert(AdminPower adminPower) {
        adminPowerDao.insert(adminPower);
    }
}
