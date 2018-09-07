package com.zaiym.rpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public static<T> T getProxy(Class<T> clazz, InvocationHandler handler){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},handler);
    }
}