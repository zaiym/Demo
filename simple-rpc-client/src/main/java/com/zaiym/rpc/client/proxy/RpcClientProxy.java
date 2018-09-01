package com.zaiym.rpc.client.proxy;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public static<T> T getProxy(Class<T> clazz, String host, int port){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},new RemoteInvocationHandler(host, port, clazz));
    }
}