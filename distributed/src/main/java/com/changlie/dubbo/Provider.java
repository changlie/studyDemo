package com.changlie.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("dubbo/provider.xml");
        System.out.println(context.getDisplayName() + ": here");
        context.start();
        System.out.println("服务已经启动...");

        Thread.sleep(Long.MAX_VALUE);
    }
}
