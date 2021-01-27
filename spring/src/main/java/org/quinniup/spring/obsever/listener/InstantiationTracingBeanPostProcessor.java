package org.quinniup.spring.obsever.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @ClassName: InstantiationTracingBeanPostProcessor
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null){
            // 等待ApplicationContext初始化完成所有Bean后触发此事件
        }
    }
}
