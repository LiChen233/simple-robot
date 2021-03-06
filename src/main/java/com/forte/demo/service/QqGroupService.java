package com.forte.demo.service;


import com.forte.demo.bean.QqGroup;
import com.forte.demo.dao.QqGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class QqGroupService {

    @Autowired
    QqGroupDao qqGroupDao;

    //获取所有群号
    public ArrayList<QqGroup> getAllGroup(){
        return qqGroupDao.getAllGroup();
    }

    public void updateGroup(String qqGroupid){
        qqGroupDao.updateGroup(qqGroupid);
    }

    public QqGroup selectGroup(String groupId){
        return qqGroupDao.selectGroup(groupId);
    }

    public void addGroup(QqGroup qqGroup){
        qqGroupDao.addGroup(qqGroup);
    }

    public Integer getSigninCount(String groupid){
        return qqGroupDao.getSigninCount(groupid);
    }
}
