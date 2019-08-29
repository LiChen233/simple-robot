package com.forte.demo.service;

import com.forte.demo.mapper.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonService {

    @Autowired
    PersonDao personDao;

    public void addStar(String qq,Integer star){
        personDao.addStar(qq,star);
    }

    public void reduceStar(String qq,Integer star){
        personDao.reduceStar(qq,star);
    }
}
