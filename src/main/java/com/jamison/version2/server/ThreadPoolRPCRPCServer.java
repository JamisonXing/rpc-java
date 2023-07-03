package com.jamison.version2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//提供线程池版本的实现
public class ThreadPoolRPCRPCServer implements RPCServer{
    private final ThreadPoolExecutor threadPool;
    private Map<String, Object> serviceProvide;
    public ThreadPoolRPCRPCServer(Map<String, Object> serviceProvide, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue) {
        threadPool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvide = serviceProvide;
    }
    @Override
    public void start(int port) {
        System.out.println("服务器端启动了");
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new WorkThread(socket, serviceProvide));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
