package com.forte.demo.mapper;

import com.forte.demo.bean.Pray;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PrayDao {

    //根据QQ获取祈愿信息
    @Select("SELECT * FROM pray WHERE qq = #{qq}")
    Pray getPray(String qq);

    //区间-1
    @Update("UPDATE pray SET qujian = qujian - 1 WHERE qq = #{qq}")
    void reduceQujian (String qq);

    //保底状态取反
    @Update("UPDATE pray SET baodi = ABS(baodi-1) WHERE qq = #{qq}")
    void resetBaodi(String qq);

}
