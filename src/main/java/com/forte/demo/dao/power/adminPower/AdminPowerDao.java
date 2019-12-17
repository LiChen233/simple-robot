package com.forte.demo.dao.power.adminPower;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.power.adminPower.AdminPower;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminPowerDao extends BaseMapper<AdminPower> {
}
