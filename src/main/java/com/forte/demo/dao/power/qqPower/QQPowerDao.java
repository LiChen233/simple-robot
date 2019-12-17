package com.forte.demo.dao.power.qqPower;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.power.qqPower.QQPower;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QQPowerDao extends BaseMapper<QQPower> {
}
