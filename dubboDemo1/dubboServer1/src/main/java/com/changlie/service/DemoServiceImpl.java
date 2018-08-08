package com.changlie.service;

import com.alibaba.dubbo.config.annotation.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public List<String> getUsers(int qty) {
        List<String> users  = new LinkedList<>();
        for (int i = 0; i < qty; i++) {
            users.add("user-"+i*i);
        }

        return users;
    }

    @Override
    public int add(int num1, int num2) {
        return num1+num2;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("system: Hello "+name+" at "+new Date().toLocaleString());
    }

    @Override
    public String getInfo() {
        return "in post:24444 at "+new Date().toLocaleString();
    }
}
