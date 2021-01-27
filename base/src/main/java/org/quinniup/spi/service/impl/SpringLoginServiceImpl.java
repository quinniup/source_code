package org.quinniup.spi.service.impl;

import org.quinniup.spi.service.LoginService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * @ClassName: SpringLoginService
 * @Author: CheneyIn
 * @Date: 2021/1/21
 */
public class SpringLoginServiceImpl implements LoginService {

    private final Logger log = LoggerFactory.getLogger(SpringLoginServiceImpl.class);

    @Override
    public void login(String userName, String password) {
        log.warn("This is SpringLoginService. UserName is " + userName + ", Password is " + password);
    }
}
