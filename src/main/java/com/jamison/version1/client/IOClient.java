package com.jamison.version1.client;

import com.jamison.version1.common.RPCRequest;
import com.jamison.version1.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    /**
     * 这里负责底层与服务端的通信，发送的Request，接受的是response对象
     * 客户端发送一次请求调用，socket建立连接，发起Request，得到响应response
     * 这里的Request是封装好的（上层进行封装），不同的service需要进行不同的封装，客户端只知道service接口，需要一层动态代理根据反射封装到不同的service
     */
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            return response;

        }catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }
    }
}
