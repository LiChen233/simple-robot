package com.forte.demo.mapper;

import com.forte.demo.bean.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PersonDao {


    @Insert("INSERT INTO person VALUES(#{qq},#{star},#{signin},#{siginTime})")
    void addPerson(Person person);

    //根据QQ号获取个人信息
    @Select("SELECT * FROM person WHERE qq = #{qq}")
    Person getPerson(String qq);

    //积分增加
    @Update("UPDATE person SET star = #{star},signin=#{signin} WHERE qq = #{qq}")
    void  addStar(Person person);

    //积分减少
    @Update("UPDATE person SET star = star - #{star} WHERE qq = #{qq}")
    void  reduceStar(Person person);

    @Select("select count(signin) from person where signin = 1")
    Integer countSignin();
}
