package com.zaiym.rpc.client.proxy;


import com.zaiym.rpc.domain.RPCRequest;
import com.zaiym.rpc.net.RpcNetTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler{

    private String host;

    private int port;

    Class<?> proxyClass;

    public RemoteInvocationHandler(String host, int port, Class<?> proxyClass) {
        this.host = host;
        this.port = port;
        this.proxyClass = proxyClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setClassName(proxyClass.getName());
        request.setMethod(method.getName());
        request.setParameters(args);
        RpcNetTransport netTransport = new RpcNetTransport(host,port);
        return netTransport.transport(request);
    }
}