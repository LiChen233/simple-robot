package com.forte.demo;

import com.forte.demo.bean.QqGroup;
import com.forte.demo.dao.QqGroupDao;
import com.forte.qqrobot.component.forhttpapi.HttpApp;
import com.forte.qqrobot.component.forhttpapi.HttpConfiguration;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

/**
 * 这个是simple-robot框架的启动器类
 * 使用HTTP API组件进行示例, 实现{@link HttpApp}接口并进行相应的配置
 * 由于需要整合Spring的依赖管理系统，所以需要Spring的依赖工厂来自定义依赖获取方式
 * 所以自定义依赖获取由构造方法进行提供
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class QQRunApplication implements HttpApp {

    /**
     * 自定义的依赖获取器
     */
    private DependGetter dependGetter;

    /**
     * 构造，需要提供一个自定义的依赖获取器
     */
    QQRunApplication(DependGetter dependGetter) {
        this.dependGetter = dependGetter;
    }


    @Override
    public void before(HttpConfiguration configuration) {
        //启动之前，进行配置
        configuration.setIp("127.0.0.1");
        configuration.setJavaPort(15514);
        configuration.setServerPath("/coolq");
        configuration.setServerPort(8877);
        //配置自定义的依赖获取器
        configuration.setDependGetter(dependGetter);

        //获取桌面路径
        //File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        //configuration.setCqPath(desktopDir.getAbsolutePath()+"\\酷Q Pro");
        /*
            关于配置的详细信息请查看普通的demo项目或者文档
         */
    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {
        QqGroupDao qqGroupDao = dependGetter.get(QqGroupDao.class);
        ArrayList<QqGroup> groups = qqGroupDao.getAllGroup();
        for (QqGroup group : groups) {
            //sender.SENDER.sendGroupMsg(group.getGroupid(),"我回来啦");
        }
    }
}
