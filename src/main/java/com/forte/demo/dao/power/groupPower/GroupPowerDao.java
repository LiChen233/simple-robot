package com.forte.demo.dao.power.groupPower;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.power.groupPower.GroupPower;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupPowerDao extends BaseMapper<GroupPower> {

}
