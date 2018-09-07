package com.zaiym.rpc.client.proxy;


import com.zaiym.rpc.domain.RPCRequest;
import com.zaiym.rpc.server.RpcNetTransportService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler{

    private String host;

    private int port;

    Class<?> proxyClass;

    private RpcNetTransportService<RPCRequest> netTransportService;

    public RemoteInvocationHandler(String host, int port, Class<?> proxyClass, RpcNetTransportService<RPCRequest> netTransportService) {
        this.host = host;
        this.port = port;
        this.proxyClass = proxyClass;
        this.netTransportService = netTransportService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setClassName(proxyClass.getName());
        request.setMethod(method.getName());
        request.setParameters(args);
        return netTransportService.transport(request);
    }
}