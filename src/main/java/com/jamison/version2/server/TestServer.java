package com.jamison.version2.server;

import com.jamison.version0.server.RPCServer;
import com.jamison.version2.common.Blog;
import com.jamison.version2.service.BlogService;

import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

public class TestServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
        Map<String, Object> serviceProvide = new HashMap<>();
        //暴露两个服务接口
        serviceProvide.put("com.jamison.version2.service.UserService", userService);
        serviceProvide.put("com.jamison.version2.service.BlogService", blogService);

        SimpleRPCServer simpleRPCServer = new SimpleRPCServer(serviceProvide);
        simpleRPCServer.start(8899);
    }
}
