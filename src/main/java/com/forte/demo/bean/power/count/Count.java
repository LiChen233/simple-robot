package com.forte.demo.bean.power.count;

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
    private String id; //QQ群号
    private String day; //日期，年月日
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
    private Integer drawCount;
    private Integer upCount;
}
