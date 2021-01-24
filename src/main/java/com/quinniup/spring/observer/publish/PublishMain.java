package com.quinniup.spring.observer.publish;

import com.quinniup.spring.observer.event.EmailEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: PublishMain
 * @Description: 关于spring的观察者模式的小demo
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
public class PublishMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmailEvent emailEvent = new EmailEvent("source","howzequinn@gmail.com","I'm a email event.");
        context.publishEvent(emailEvent);
    }
}
