package com.zaiym;

import com.zaiym.rpc.client.proxy.RemoteInvocationHandler;
import com.zaiym.rpc.client.proxy.RpcClientProxy;
import com.zaiym.rpc.domain.RPCRequest;
import com.zaiym.rpc.net.RpcNettyTransport;
import com.zaiym.rpc.server.IHelloService;
import com.zaiym.rpc.server.RpcNetTransportService;

import java.lang.reflect.InvocationHandler;

/**
 * Hello world!
 *
 */
public class Client {
    public static void main( String[] args ) {
        String host = "10.20.20.183";
        int port = 8090;

        RpcNetTransportService<RPCRequest> netTransportService = new RpcNettyTransport(host, port);

        InvocationHandler handler = new RemoteInvocationHandler(host,port, IHelloService.class, netTransportService);

        IHelloService service = RpcClientProxy.getProxy(IHelloService.class, handler);
        String result = service.sayHello("张三");
        System.out.println(result);
        System.out.println(service.sayHello());



    }
}
