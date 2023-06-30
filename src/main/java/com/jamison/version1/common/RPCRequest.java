package com.jamison.version1.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 在上个例子中，Request仅仅只发送了一个id参数过去这显然是不合理的，
 * 因为服务端不会只有一个服务一个方法，因此值传递参数服务端不知道调用哪个方法
 * 因此一个RPC请求中，client发送应该是需要调用的service接口名，方法名，参数，参数类型
 * 这样服务端就能根据这些信息根据反射调用相应的方法
 * 使用Java自带的序列化方式（实现接口）
 */
@Data
@Builder
public class RPCRequest implements Serializable {
    //服务类名
    private String interfaceName;
    //方法名
    private String methodName;
    //参数列表
    private Object[] params;
    //参数类型
    private Class<?>[] paramsType;
}
