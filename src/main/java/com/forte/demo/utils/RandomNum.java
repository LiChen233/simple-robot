package com.forte.demo.utils;

import java.util.Random;

public class RandomNum {

    public static Integer randomNumber(Integer min,Integer max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
