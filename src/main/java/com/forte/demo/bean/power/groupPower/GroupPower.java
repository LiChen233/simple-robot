package com.forte.demo.bean.power.groupPower;

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
@TableName("group_power")
public class GroupPower implements Serializable {
    private Integer id;
    private String qq_group;
    private Integer fun_id;
    private Integer status;
}
