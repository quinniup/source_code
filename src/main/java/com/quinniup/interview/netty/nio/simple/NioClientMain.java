package com.quinniup.interview.netty.nio.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @ClassName: NioClientMain
 * @Description: 基于JDK的NIO Client
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class NioClientMain {
    public static void main(String[] args) {
        try (Selector selector = Selector.open(); SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            // connection
            if (socketChannel.connect(new InetSocketAddress(8889))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                doWrite(socketChannel);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }

            for (; ; ) {
                // 等待1s，超时则返回
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.stream().forEach(selectionKey -> {
                    try {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        if (selectionKey.isConnectable()) {
                            if (sc.finishConnect()) {
                                sc.register(selector, SelectionKey.OP_READ);
                                doWrite(sc);
                            }
                        } else {
                            System.exit(0);
                        }
                        if (selectionKey.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int readBytes = sc.read(byteBuffer);
                            if (readBytes > 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                String body = new String(bytes, StandardCharsets.UTF_8);
                                System.out.println(body);
                            } else if (readBytes < 0) {
                                selectionKey.cancel();
                                sc.close();
                            } else {
                                sc.register(selector, SelectionKey.OP_READ);
                            }
                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        selectionKey.cancel();
                        try {
                            socketChannel.close();
                            selector.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "    -    QUERY TIME ORDER    -    ".getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(req);
        buffer.flip();
        System.out.printf(String.valueOf(buffer.remaining()));
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()) {
            System.out.println("Send order 2 server succeed");
        }
    }
}
