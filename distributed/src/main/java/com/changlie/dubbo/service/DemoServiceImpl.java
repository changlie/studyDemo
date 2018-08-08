package com.changlie.dubbo.service;

import java.util.LinkedList;
import java.util.List;

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
}
