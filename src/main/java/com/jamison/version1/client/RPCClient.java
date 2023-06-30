package com.jamison.version1.client;

import com.azul.crs.client.Client;
import com.jamison.version1.common.User;
import com.jamison.version1.service.UserService;

public class RPCClient {
    //客户端调用不同的方法
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        //服务的方式1
        User userByUserId = proxy.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);
        //服务的方式2
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = proxy.insertUserId(user);
        System.out.println("向服务端插入数据：" + integer);
    }
}
