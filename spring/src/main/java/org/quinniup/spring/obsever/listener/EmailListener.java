package org.quinniup.spring.obsever.listener;

import org.quinniup.spring.obsever.event.EmailEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @ClassName: EmailListener
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
public class EmailListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof EmailEvent){
            EmailEvent event = (EmailEvent) applicationEvent;
            event.print();
            System.out.println(event.getSource());
            System.out.println(event.getAddress());
            System.out.println(event.getText());
        }
    }
}
