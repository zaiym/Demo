package com.zaiym;

import com.zaiym.rpc.bean.BeanFactory;
import com.zaiym.rpc.proxy.RpcServerProxy;
import com.zaiym.rpc.server.HelloServiceImpl;
import com.zaiym.rpc.server.IHelloService;

/**
 * Hello world!
 *
 */
public class Server {
    public static void main( String[] args ) {
        BeanFactory.BEANS.put(IHelloService.class.getName(),new HelloServiceImpl());
        RpcServerProxy proxy = new RpcServerProxy();
        proxy.publisher(8090);
    }
}
