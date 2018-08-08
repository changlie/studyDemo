package com.changlie.rmi;

import com.changlie.rmi.service.UserService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class v1Client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 6666);

        UserService userService = (UserService) registry.lookup("user");

        String addr = userService.getAddr("chanlie");
        int age = userService.getAge("changlie");
        System.out.printf("changlie age:%d, addr:%s \n", age, addr);
    }
}
