package com.quinniup.interview.netty.nio.upgrade.client;

/**
 * @ClassName: NioClient
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class NioClient {
    public static void main(String[] args) {
        int port = 8889;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                port = 8080;
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "Time-Client-001").start();
    }
}
