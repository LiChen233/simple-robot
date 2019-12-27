package com.forte.demo.bean.power.count;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 各功能使用数量，每日记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("count")
public class Count implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String qqGroup;//QQ群号
    private String today; //日期，年月日
    private Integer middleOne;
    private Integer middleTen;
    private Integer specialOne;
    private Integer specialTen;
    private Integer customOne;
    private Integer customTen;
    private Integer highOne;
    private Integer highTen;
    private Integer seCount;
    private Integer aiCount;
    private Integer eqCount;
    private Integer aqCount;
    private Integer signCount;
    private Integer upCount;

    @TableField(exist = false)
    private String funName; //功能名称，sql用
}
