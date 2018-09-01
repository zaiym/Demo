package com.zaiym.rpc.handler;

import com.zaiym.rpc.bean.BeanFactory;
import com.zaiym.rpc.domain.RPCRequest;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler implements Runnable{

    Socket socket;

    public ProcessorHandler( Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) in.readObject();
            Object result = invoke(request);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    private Object invoke(RPCRequest request){
        if (request.getClassName() == null) {
            throw new NullPointerException("the className can not be null");
        }
        if (request.getMethod() == null) {
            throw new NullPointerException("the method can not be null");
        }
        try {
            String className = request.getClassName();
            Object serverImpl = BeanFactory.BEANS.get(className);
            if (serverImpl == null) {
                throw new RuntimeException("the interface " + className + " Implementation class can not be null");
            }
            Object[] params = request.getParameters();
            //参数类型
            Class[] types = new Class[params == null ? 0 : params.length];
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    types[i] = params[i].getClass();
                }
            }
            Method method = serverImpl.getClass().getMethod(request.getMethod(),types);
            return method.invoke(serverImpl, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}