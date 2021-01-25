package com.quinniup.spring.observer.publish;


import com.quinniup.spring.observer.event.EmailEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: PublishMain
 * @Description:
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
public class PublishEvent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void publish(String userName, String text){
        EmailEvent emailEvent = new EmailEvent("emailPublic",userName,text);
        this.applicationContext.publishEvent(emailEvent);
    }
}
