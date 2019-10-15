package com.forte.demo.mapper;

import com.forte.demo.bean.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PersonDao {


    //初始化用户
    @Insert("INSERT INTO person VALUES(#{qq},#{star},#{signin},#{draw})")
    void addPerson(Person person);

    //根据QQ号获取个人信息
    @Select("SELECT * FROM person WHERE qq = #{qq}")
    Person getPerson(String qq);

    //积分增加
    @Update("UPDATE person SET star = #{star} WHERE qq = #{qq}")
    void  addStar(Person person);

    //积分减少
    @Update("UPDATE person SET star = star - #{star} WHERE qq = #{qq}")
    void  reduceStar(Person person);

    //总签到数量
    @Select("select count(signin) from person where signin = 1")
    Integer countSignin();

    //通过qq获取积分
    @Select("SELECT star FROM person WHERE qq = #{qq}")
    Integer getStar(String qq);

    //设置抽签
    @Update("UPDATE person SET draw = #{draw} WHERE qq = #{qq}")
    void setDraw(Person person);

    //设置签到
    @Update("UPDATE person SET signin = #{signin} WHERE qq = #{qq}")
    void setSignin(Person person);

    //重置所有人的签到记录
    @Update("UPDATE person SET signin = 0")
    void resetAllSign();

    //重置签到
    @Update("UPDATE person SET draw = 0")
    void resetDraw();
}
