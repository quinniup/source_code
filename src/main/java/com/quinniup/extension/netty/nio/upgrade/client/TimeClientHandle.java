package com.quinniup.extension.netty.nio.upgrade.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: TimeClientHandle
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/5
 */
public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean flag;

    public TimeClientHandle(String host, int port) {
        //host若为空，则设置为指定ip
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            //打开多路复用器
            selector = Selector.open();
            //打开管道
            socketChannel = SocketChannel.open();
            //设置管道为非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        while (!this.flag) {
            try {
                this.selector.select(1000);
                Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (IOException e) {
                        if (selectionKey!=null){
                            selectionKey.cancel();
                            if (selectionKey.channel()!=null){
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if (this.selector!=null){
            try {
                this.selector.close();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        //若该selectorkey可用
        if (selectionKey.isValid()) {
            //将key转型为SocketChannel
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            //判断是否连接成功
            if (selectionKey.isConnectable()) {
                //若已经建立连接
                if (sc.finishConnect()) {
                    //向多路复用器注册可读事件
                    sc.register(selector, SelectionKey.OP_READ);
                    //向管道写数据
                    doWrite(sc);
                }else {
                    //连接失败 进程退出
                    System.exit(1);
                }
            }

            //若是可读的事件
            if (selectionKey.isReadable()) {
                //创建一个缓冲区
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                System.out.println("before  :  "+readBuffer);
                //从管道中读取数据然后写入缓冲区中
                int readBytes = sc.read(readBuffer);
                System.out.println("after :  "+readBuffer);
                //若有数据
                if (readBytes > 0) {
                    //反转缓冲区
                    readBuffer.flip();
                    System.out.println(readBuffer);

                    byte[] bytes = new byte[readBuffer.remaining()];
                    //获取缓冲区并写入字节数组中
                    readBuffer.get(bytes);
                    //将字节数组转换为String类型
                    String body = new String(bytes);
                    System.out.println(body.length());
                    System.out.println("Now is : " + body + "!");
                    this.flag = true;
                } else if (readBytes < 0) {
                    selectionKey.cancel();
                    sc.close();
                } else {
                    sc.register(selector, SelectionKey.OP_READ);
                }
            }
        }
    }

    private void doConnect() throws IOException {
        //通过ip和端口号连接到服务器
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            //向多路复用器注册可读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            //向管道写数据
            doWrite(socketChannel);
        } else {
            //若连接服务器失败,则向多路复用器注册连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        //要写的内容
        byte[] req = "    -    QUERY TIME ORDER     -   ".getBytes();
        //为字节缓冲区分配指定字节大小的容量
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        //将内容写入缓冲区
        writeBuffer.put(req);
        //反转缓冲区
        writeBuffer.flip();
        //输出打印缓冲区的可读大小
        System.out.println(writeBuffer.remaining());
        //将内容写入管道中
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            //若缓冲区中无可读字节，则说明成功发送给服务器消息
            System.out.println("Send order 2 server succeed.");
        }
    }
}
