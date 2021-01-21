package com.quinniup.extension.netty.nio.upgrade.server;

/**
 * @ClassName: NioServer
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class NioServer {
    public static void main(String[] args) {
        int port = 8889;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                port = 8889;
            }
        }
        new Thread( new MultiplexerTimeServer(port),"NIO-MultiplexerTimeServer-001").start();
    }
}
