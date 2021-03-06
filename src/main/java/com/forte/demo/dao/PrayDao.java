package com.forte.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.Pray;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PrayDao extends BaseMapper<Pray> {

    //根据QQ获取祈愿信息
    @Select("SELECT * FROM pray WHERE qq = #{qq}")
    Pray getPray(String qq);

    //新增一条用户数据
    @Insert("INSERT INTO pray VALUES(#{qq},10,0,7,0,10,1,10,0,10,0)")
    void addPray(String qq);

    //获取某一区间
    @Select("SELECT ${qujian} FROM pray WHERE qq = ${qq}")
    Integer getQujian (@Param("qujian") String qujian,@Param("qq") String qq);

    //某一区间-1
    @Update("UPDATE pray SET ${qujian} = ${qujian} - 1 WHERE qq = ${qq}")
    void reduceQujian (@Param("qujian") String qujian,@Param("qq") String qq);

    //重置某一区间
    @Update("UPDATE pray SET ${qujian} = ${qujianshu} WHERE qq = ${qq}")
    void resetQujian (@Param("qujian") String qujian,@Param("qujianshu") Integer qujianshu,@Param("qq") String qq);

    //设置某一保底状态取反
    @Update("UPDATE pray SET ${baodi} = ABS(${baodi}-1) WHERE qq = ${qq}")
    void resetBaodi(@Param("baodi") String baodi,@Param("qq") String qq);

    //获取某一保底
    @Select("SELECT ${baodi} FROM pray WHERE qq = ${qq}")
    Integer getBaodi (@Param("baodi") String baodi,@Param("qq") String qq);

    //查询保底还有多少
    @Select("SELECT ${highqujian} FROM pray WHERE ${highbaodi} = 0 AND qq = ${qq}")
    Integer getBaodiNum(@Param("highqujian")String highqujian,@Param("highbaodi") String highbaodi,@Param("qq")String qq);

    //重置魔法少女保底
    @Update("UPDATE pray SET customqujian = 7,custombaodi = 0")
    void resetCustom();

    //重置魔女保底
    @Update("UPDATE pray SET specialqujian = 10,specialbaodi = 0")
    void resetSpecial();

    //重置梦想保底
    @Update("UPDATE pray SET festivalqujian = 10,festivalbaodi = 0")
    void resetFestival();
}
