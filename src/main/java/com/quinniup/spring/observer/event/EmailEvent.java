package com.quinniup.spring.observer.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName: EmailEvent
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/25
 */
public class EmailEvent extends ApplicationEvent {

    private static final long serialVersionUid = 1L;
    private String address;
    private String text;

    public static long getSerialVersionUid() {
        return serialVersionUid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EmailEvent(Object source) {
        super(source);
    }

    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public void print(){
        System.out.println("Hello, receive a spring event");
    }
}
