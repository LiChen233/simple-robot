package com.forte.demo.dao.power.count;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forte.demo.bean.power.count.Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CountDao extends BaseMapper<Count> {
    @Update("UPDATE `count` SET middle_one = middle_one + 1 WHERE id = #{id}")
    void middle_one_add(String id);
    @Update("UPDATE `count` SET middle_ten = middle_ten + 1 WHERE id = #{id}")
    void middle_ten_add(String id);
    @Update("UPDATE `count` SET special_one = special_one + 1 WHERE id = #{id}")
    void special_one_add(String id);
    @Update("UPDATE `count` SET special_ten = special_ten + 1 WHERE id = #{id}")
    void special_ten_add(String id);
    @Update("UPDATE `count` SET custom_one = custom_one + 1 WHERE id = #{id}")
    void custom_one_add(String id);
    @Update("UPDATE `count` SET custom_ten = custom_ten + 1 WHERE id = #{id}")
    void custom_ten_add(String id);
    @Update("UPDATE `count` SET high_one = high_one + 1 WHERE id = #{id}")
    void high_one_add(String id);
    @Update("UPDATE `count` SET high_ten = high_ten + 1 WHERE id = #{id}")
    void high_ten_add(String id);
    @Update("UPDATE `count` SET ai_count = ai_count + 1 WHERE id = #{id}")
    void ai_count_add(String id);
    @Update("UPDATE `count` SET eq_count = eq_count + 1 WHERE id = #{id}")
    void eq_count_add(String id);
    @Update("UPDATE `count` SET aq_count = aq_count + 1 WHERE id = #{id}")
    void aq_count_add(String id);
    @Update("UPDATE `count` SET se_count = se_count + 1 WHERE id = #{id}")
    void se_count_add(String id);
}
