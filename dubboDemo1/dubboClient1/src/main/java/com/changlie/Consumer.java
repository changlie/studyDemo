package com.changlie;

import com.alibaba.dubbo.config.annotation.Reference;
import com.changlie.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    public String name = "shark";

    @Reference
    static DemoService demoService;




    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");
        context.start();

        Consumer consumer = context.getBean(Consumer.class);

        System.out.println("this's in "+consumer.name);

//        DemoService demoService = context.getBean(DemoService.class);
        demoService.sayHello("Tom");

        System.out.println("calculate: "+ demoService.add(110,107));

        System.out.println(demoService.getUsers(5));

        for (int i = 0; i < 30; i++) {
            System.out.println(demoService.getInfo());
        }

        System.out.println("consumer finish!");
        System.exit(0);
    }
}
