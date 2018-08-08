package com.changlie.service;

import java.util.List;

public interface DemoService {

    List<String> getUsers(int qty);

    int add(int num1, int num2);

    void sayHello(String name);

    String getInfo();
}
