package com.changlie.rmi;

import com.changlie.rmi.service.PersonService;
import com.changlie.rmi.service.PersonServiceImpl;
import com.changlie.rmi.service.UserService;
import com.changlie.rmi.service.UserServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            PersonService personService = new PersonServiceImpl();
            UserService userService = new UserServiceImpl();
            //注册通讯端口
            LocateRegistry.createRegistry(6600);
            //注册通讯路径
            Naming.rebind("rmi://127.0.0.1:6600/PersonService", personService);
            System.out.println("Service Start!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
