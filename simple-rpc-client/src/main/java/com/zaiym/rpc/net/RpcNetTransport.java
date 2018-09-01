package com.zaiym.rpc.net;

import com.zaiym.rpc.domain.RPCRequest;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcNetTransport {

    String host;

    int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket getSocket(){
        Socket socket;
        try {
            socket = new Socket(host, port);
            System.out.println("新建一个socket连接{host:"+host+",port:"+port+"}");
        } catch (IOException e) {
            throw new RuntimeException("创建socket连接异常{host:"+host+",port:"+port+"}",e);
        }
        return socket;
    }

    public Object transport(RPCRequest request){
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            socket = getSocket();
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        return null;
    }
}