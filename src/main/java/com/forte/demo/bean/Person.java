package com.forte.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private String qq;
    private Integer star;
    private Integer signin;
    private Integer draw;
    private Date signTime;
}
