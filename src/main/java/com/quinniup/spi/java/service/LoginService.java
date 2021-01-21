package com.quinniup.spi.java.service;

/**
 * @ClassName: LoginService
 * @Description: Java SPI Learning
 * @Author: CheneyIn
 * @Date: 2021/1/21
 */
public interface LoginService {

    /**
     * verify the interface
     * @param userName  username
     * @param password  password
     */
    void login(String userName, String password);
}
