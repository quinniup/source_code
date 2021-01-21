package com.quinniup.spi.java;

import com.quinniup.spi.java.service.LoginService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName: JavaSpi
 * @Description: 启动类
 * @Author: CheneyIn
 * @Date: 2021/1/21
 */
public class JavaSpi {

    public static void main(String[] args) {
        // 1. 通过Java SPI了解 [JVM的 类加载机制]
        // 顺着进去看看源码
        ServiceLoader<LoginService> serviceLoader = ServiceLoader.load(LoginService.class);
        // 4. 获取刚才 ServiceLoader 中的 懒加载迭代器对象
        Iterator<LoginService> iterator = serviceLoader.iterator();
        // 5. 进入 懒加载迭代器的 hasNext() 方法
        while (iterator.hasNext()){
            LoginService loginService = iterator.next();
            loginService.login("Quinn","123456");
        }
    }
}
