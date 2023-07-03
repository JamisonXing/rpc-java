package com.jamison.version2.server;

//抽象RPCServer，开放封闭原则
//将RPCServer抽象为一个接口，以后的服务端实现这个接口即可
public interface RPCServer {
    void start(int port);
    void stop();
}
