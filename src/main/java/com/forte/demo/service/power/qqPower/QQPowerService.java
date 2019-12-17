package com.forte.demo.service.power.qqPower;

import com.forte.demo.bean.power.groupPower.GroupPower;
import com.forte.demo.bean.power.qqPower.QQPower;

public interface QQPowerService {
    QQPower find(QQPower qqPower);

    void update(QQPower qqPower);

    void insert(QQPower qqPower);
}
