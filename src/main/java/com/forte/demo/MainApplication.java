package com.forte.demo;

import com.forte.demo.mapper.MessageMapper;
import com.forte.qqrobot.component.forhttpapi.HttpApplication;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.log.QQLog;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 *
 * main函数所在的主程序
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MainApplication {

    public static void main(String[] args) {
        //首先，启动Spring
        ConfigurableApplicationContext spring = SpringApplication.run(SpringRunApplication.class, args);
        //获取Spring的依赖工厂
        ConfigurableListableBeanFactory beanFactory = spring.getBeanFactory();
        //通过这个依赖工厂，创建一个自定义的依赖获取器，使得其优先获取Spring的依赖，其次再获取原本的依赖。
        //这里直接使用内部匿名类的形式来实现DependGetter接口
        DependGetter dependGetter = new DependGetter() {
            /**
             * 通过Spring的beanFactory获取实例对象，如果获取不到返回NULL即可
             * 由于Spring的beanFactory在获取不到依赖实例的时候会直接抛出异常，所以需要使用try-catch
             */
            @Override
            public <T> T get(Class<T> clazz) {
                try {
                    return beanFactory.getBean(clazz);
                }catch (Exception e){
                    return null;
                }
            }
            /**
             * 通过Spring的beanFactory获取实例对象，如果获取不到返回NULL即可
             * 由于Spring的beanFactory在获取不到依赖实例的时候会直接抛出异常，所以需要使用try-catch
             */
            @Override
            public <T> T get(String name, Class<T> type) {
                try {
                    return beanFactory.getBean(name, type);
                }catch (Exception e){
                    return null;
                }
            }
            /**
             * 通过Spring的beanFactory获取实例对象，如果获取不到返回NULL即可
             * 由于Spring的beanFactory在获取不到依赖实例的时候会直接抛出异常，所以需要使用try-catch
             */
            @Override
            public Object get(String name) {
                try {
                    return beanFactory.getBean(name);
                }catch (Exception e){
                    return null;
                }
            }
            /**
             * 获取一个常量值，也就是获取配置信息，通过Spring的Environment获取
             */
            @Override
            public <T> T constant(String name, Class<T> type) {
                try {
                    return beanFactory.getBean(Environment.class).getProperty(name, type);
                }catch (Exception e){
                    return null;
                }
            }
            /**
             * 获取一个常量值，也就是获取配置信息，通过Spring的Environment获取
             */
            @Override
            public Object constant(String name) {
                try {
                    return beanFactory.getBean(Environment.class).getProperty(name);
                }catch (Exception e){
                    return null;
                }
            }
        };

        //启动QQRobot
        //获取自定义的启动器
        QQRunApplication qqRunApplication = new QQRunApplication(dependGetter);
        //获取HttpApi组件的启动器
        HttpApplication httpApplication = new HttpApplication();
        //启动组件服务
        httpApplication.run(qqRunApplication);
    }


}
