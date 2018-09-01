package com.zaiym.rpc.proxy;

import com.zaiym.rpc.handler.ProcessorHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServerProxy {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务已经启动，监听端口 ：" + port + " 等待请求进入...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("连接进入....");
                executorService.execute(new ProcessorHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(serverSocket);
            executorService.shutdown();
        }
    }

}