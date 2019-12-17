package com.forte.demo.bean.power.adminPower;

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
@TableName("admin_power")
public class AdminPower implements Serializable {
    private Integer id;
    private String qq;
    private Integer admin;
    private Integer status;
}
