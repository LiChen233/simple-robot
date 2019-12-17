package com.forte.demo.service.power.adminPower;

import com.forte.demo.bean.power.adminPower.AdminPower;
import com.forte.demo.bean.power.qqPower.QQPower;

public interface AdminPowerService {
    AdminPower find(AdminPower adminPower);

    void update(AdminPower adminPower);

    void insert(AdminPower adminPower);
}
