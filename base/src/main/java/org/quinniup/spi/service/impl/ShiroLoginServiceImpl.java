package org.quinniup.spi.service.impl;

import org.quinniup.spi.service.LoginService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * @ClassName: LoginServiceImpl
 * @Description: LoginServer 的一种实现方式
 * @Author: CheneyIn
 * @Date: 2021/1/21
 */
public class ShiroLoginServiceImpl implements LoginService {

    private final Logger log = LoggerFactory.getLogger(ShiroLoginServiceImpl.class);

    @Override
    public void login(String userName, String password) {
        log.warn("This is ShiroLoginService. UserName is " + userName + ", Password is " + password);
    }
}
