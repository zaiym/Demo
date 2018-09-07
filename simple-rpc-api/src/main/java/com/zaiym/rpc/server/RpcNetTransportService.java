package com.zaiym.rpc.server;

/**
 * 传输服务接口
 */
public interface RpcNetTransportService<T> {

    Object transport(T object);
}
