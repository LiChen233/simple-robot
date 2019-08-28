package com.forte.demo.mapper;

import com.forte.demo.bean.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PersonDao {
    //根据QQ号获取个人信息
    @Select("SELECT * FROM person WHERE qq = #{qq};")
    Person getPerson(String qq);

    //积分增加
    @Update("UPDATE person SET star = star + #{star} WHERE qq = #{qq}")
    void  addStar(String qq,Integer star);

    //积分减少
    @Update("UPDATE person SET star = star - #{star} WHERE qq = #{qq}")
    void  reduceStar(String qq,Integer star);
}
