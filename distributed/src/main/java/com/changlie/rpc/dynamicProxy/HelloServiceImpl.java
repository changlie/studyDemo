package com.changlie.rpc.dynamicProxy;

public class HelloServiceImpl implements HelloService{

    @Override
    public String hello(String name) {
        return "Hello, " + name;
    }

    @Override
    public int add(int num1, int num2) {
        return num1 + num2;
    }

}
