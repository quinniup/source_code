package com.quinniup.interview.netty.nio.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NIOMain
 * @Description: 基于JDK的NIO实现方式
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class NioServerMain {

    public static void main(String[] args) {
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            // 绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(8889));
            // 设置为NIO模式
            serverSocketChannel.configureBlocking(false);
            //
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            for (; ; ) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.keys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = ssc.accept();
                            System.out.println("accept new conn: " + socketChannel.getRemoteAddress());
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            SocketChannel ssc = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int length = ssc.read(buffer);
                            if (length > 0) {
                                buffer.flip();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                String content = new String(bytes, StandardCharsets.UTF_8).replace("\r\n", "");
                                if (content.equalsIgnoreCase("quit")) {
                                    selectionKey.cancel();
                                    selector.close();
                                    serverSocketChannel.close();
                                } else {
                                    System.out.println("receive msg: " + content);
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
