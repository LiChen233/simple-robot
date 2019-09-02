package com.forte.demo.service;

import com.forte.demo.bean.Person;
import com.forte.demo.mapper.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonDao personDao;


    public Person getPerson(String qq){
        return personDao.getPerson(qq);
    }

    public void addStar(Person person){
        personDao.addStar(person);
    }

    public void reduceStar(Person person){
        personDao.reduceStar(person);
    }

    public void addPerson(Person person){
        personDao.addPerson(person);
    }

    public Integer countSignin(){
        return personDao.countSignin();
    }

    public Integer getStar(String qq){
        return personDao.getStar(qq);
    }

    public void setDraw(Person person){
        personDao.setDraw(person);
    }
}
