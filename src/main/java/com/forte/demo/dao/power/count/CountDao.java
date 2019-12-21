package com.forte.demo.dao.power.count;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.power.count.Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CountDao extends BaseMapper<Count> {

    @Update("UPDATE `count` SET ${funName} = ${funName} + 1 WHERE id = #{id}")
    void increase(Count count);
}
