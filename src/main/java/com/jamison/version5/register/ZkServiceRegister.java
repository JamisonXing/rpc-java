package com.jamison.version5.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

//zookeeper服务注册接口的实现类
public class ZkServiceRegister implements ServiceRegister{
    //curator提供的zookeeper客户端
    private CuratorFramework client;
    //zookeeper根路径节点
    private static final String ROOT_PATH = "MyRPC";

    //这里负责zookeeper客户端的初始化，并于zookeeper服务端建立连接
    public ZkServiceRegister() {
        //指数时间重试
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 3);
        //zookeeper的地址固定，不论是服务端还是消费端，都要与之建立连接
        //sessionTimeoutMs与zoo.cfg中的tickTime 有关系
        //zk还会根据minSessionTimeout与maxSessionTimeout两个参数重新调整最后的超时值,默认分别为tickTime 的2倍和20倍
        //使用心跳监听状态
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper连接成功");

    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try{
            //serviceName创建永久节点，服务提供者下线时，不删服务名，只删地址
            if(client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            //路径地址，一个/代表一个节点
            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            //临时节点，服务器下线就删除节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已经存在!");
        }
    }

    //地址 -> XXX.XXX.XXX.XXX:PORT 字符串
    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() +
                ":" +
                serverAddress.getPort();
    }

    //根据服务名返回地址
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try{
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            //这里默认用第一个后面加负载均衡
            String string = strings.get(0);
            return parseAddress(string);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //字符串解析为地址
    private InetSocketAddress parseAddress(String address) {
         String[] result = address.split(":");
         return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }
}
