package com.zaiym.rpc.server;

public class HelloServiceImpl implements IHelloService{

    @Override
    public String sayHello(String name) {
        return "Hello :" + name;
    }

    @Override
    public String sayHello() {
        return "这是一个测试....";
    }
}