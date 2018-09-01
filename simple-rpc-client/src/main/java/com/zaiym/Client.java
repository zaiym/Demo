package com.zaiym;

import com.zaiym.rpc.client.proxy.RpcClientProxy;
import com.zaiym.rpc.server.IHelloService;

/**
 * Hello world!
 *
 */
public class Client {
    public static void main( String[] args ) {
        String host = "10.20.20.183";
        int port = 8090;
        IHelloService service = RpcClientProxy.getProxy(IHelloService.class, host, port);
        String result = service.sayHello("张三");
        System.out.println(result);

        System.out.println(service.sayHello());
    }
}
