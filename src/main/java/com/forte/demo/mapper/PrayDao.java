package com.forte.demo.mapper;

import com.forte.demo.bean.Pray;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PrayDao {

    //根据QQ获取祈愿信息
    @Select("SELECT * FROM pray WHERE qq = #{qq}")
    Pray getPray(String qq);

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
}
