package com.quinniup.spi.java.service.impl;

import com.quinniup.spi.java.service.LoginService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * @ClassName: SpringLoginService
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/21
 */
public class SpringLoginService implements LoginService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShiroLoginService.class);

    @Override
    public void login(String userName, String password) {
        LOGGER.warn("This is SpringLoginService. UserName is " + userName + ", Password is " + password);
    }
}
