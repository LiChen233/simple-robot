package com.forte.demo.bean.power.qqPower;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("qq_power")
public class QQPower implements Serializable {
    private Integer id;
    private String qq;
    private Integer fun_id;
    private Integer status;
}
