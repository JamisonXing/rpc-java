package com.jamison.version0.server;

import com.jamison.version0.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                //开启一个线程去处理
                new Thread(() -> {
                    try{
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        //读取客户端传过来的id
                        Integer id = ois.readInt();
                        User userByUserId = userService.getUserByUserId(id);
                        //写入User对象给客户端
                        oos.writeObject(userByUserId);
                        oos.flush();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
