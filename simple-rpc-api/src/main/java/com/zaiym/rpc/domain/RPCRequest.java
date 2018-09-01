package com.zaiym.rpc.domain;

import java.io.Serializable;

public class RPCRequest implements Serializable{

    private static final long serialVersionUID = -7027858051891192422L;

    private String className;

    private String method;

    private Object[] parameters;

    public RPCRequest() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}