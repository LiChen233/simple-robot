package com.forte.demo.dao;

import com.forte.demo.bean.QqGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface QqGroupDao {

    //获取所有群号
    @Select("SELECT groupid FROM qqgroup")
    ArrayList<QqGroup> getAllGroup();

    @Update("update qqgroup SET signin_count= signin_count + 1 where groupid=#{groupid}")
    void updateGroup(String groupid);


    @Select("select * from qqgroup where groupid=#{groupid}")
    QqGroup selectGroup(String groupid);


    @Insert("insert into qqgroup values(#{groupid},#{signinCount})")
    void addGroup(QqGroup qqGroup);

    @Select("select signin_count from qqgroup where groupid = #{groupid}")
    Integer getSigninCount(String groupid);

    //每日重置签到名次
    @Update("UPDATE qqgroup SET signin_count = 0")
    void resetSigninCount();

}
