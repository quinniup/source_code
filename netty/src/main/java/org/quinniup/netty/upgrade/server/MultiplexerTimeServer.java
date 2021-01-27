package org.quinniup.netty.upgrade.server;

import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: MultiplexerTimeServer
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean flag;

    public MultiplexerTimeServer(int port) {
        try {
            this.selector = Selector.open();
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.bind(new InetSocketAddress(port), 1024);
            this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("The server is start in port: " + port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void stop() {
        this.flag = true;
    }

    @Override
    public void run() {
        while (!this.flag) {
            try {
                this.selector.select(1000);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                SelectionKey selectionKey;
                while (selectionKeyIterator.hasNext()) {
                    selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if (this.selector != null) {
            try {
                this.selector.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            System.out.println("selectionKey is valid");
            if (selectionKey.isAcceptable()) {
                // 获取服务器通道
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                // 执行阻塞方法，等待客户端资源
                SocketChannel socketChannel = serverSocketChannel.accept();
                // 使用非阻塞方式
                socketChannel.configureBlocking(false);
                socketChannel.register(this.selector, SelectionKey.OP_READ);
                System.out.println("connect is: " + socketChannel.getRemoteAddress());
            }
            if (selectionKey.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int length = socketChannel.read(buffer);
                if (length > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8).trim();
                    System.out.println("receive msg: " + body);
                    String currTime = LocalTime.now().toString();
                    doWrite(socketChannel, currTime);
                } else if (length < 0) {
                    selectionKey.cancel();
                    socketChannel.close();
                    System.out.println("close...");
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String currTime) throws IOException {
        if (!StringUtil.isNullOrEmpty(currTime)) {
            System.out.println(currTime);
            byte[] bytes = currTime.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            socketChannel.write(buffer);
        }
    }
}
