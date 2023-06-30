package com.jamison.version1.server;

import com.jamison.version1.common.RPCRequest;
import com.jamison.version1.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动启动了");
            //BIO的方式监听Socket
            while(true) {
                Socket socket = serverSocket.accept();
                //开启一个线程去处理，这个类负责的功能太复杂，以后代码重构中，这部分代码功能要分离出来
                new Thread(() -> {
                    try{
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        //读取客户端传过来的request
                        RPCRequest rpcRequest = (RPCRequest) ois.readObject();
                        //反射调用对应的方法
                        Method method = userService.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsType());
                        Object invoke = method.invoke(userService, rpcRequest.getParams());
                        //封装，写入response对象
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();
                    }catch (IOException | ClassNotFoundException | IllegalAccessException | InvocationTargetException |
                            NoSuchMethodException e) {
                        e.printStackTrace();
                        System.out.println("从IO中读取数据失败");
                    }
                }).start();
            }
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
