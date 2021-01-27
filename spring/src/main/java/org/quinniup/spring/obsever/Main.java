package org.quinniup.spring.obsever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: Main
 * @Description: 关于spring的观察者模式的小demo
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
@SpringBootApplication
public class Main {
    /**
     * 1. 创建事件类 EmailEvent，继承于 ApplicationEvent父类
     * 2. 创建监听类 EmailListener，实现 ApplicationListener接口
     * 3. 创建发布类 PublishEvent， 实现 ApplicationContextAware接口，主要用于获取ApplicationContext参数
     * 4. 配置applicationContext.xml配置文件，用于配置bean注入
     * 5. 从Spring容器ApplicationContext中获取到 PublishEvent的Bean，进行publish()调用
     */
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        // 从IOC容器中获取被AOP代理的publishEvent代理对象
//        PublishEvent publishEvent = (PublishEvent)context.getBean("publishEvent");
//        publishEvent.publish("Quinn","Publish a msg to U.");


        // SpringBoot启动入口
        // 为了查看SpringBoot加载配置文件的源码流程
        SpringApplication.run(Main.class, args);
    }
}
