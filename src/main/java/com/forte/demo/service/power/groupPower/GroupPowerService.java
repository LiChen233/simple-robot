package com.forte.demo.service.power.groupPower;

import com.forte.demo.bean.power.groupPower.GroupPower;

public interface GroupPowerService {
    GroupPower find(GroupPower groupPower);

    void update(GroupPower groupPower);

    void insert(GroupPower groupPower);
}
