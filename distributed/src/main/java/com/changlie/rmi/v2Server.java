package com.changlie.rmi;

import com.changlie.rmi.service.PersonService;
import com.changlie.rmi.service.PersonServiceImpl;
import com.changlie.rmi.service.UserService;
import com.changlie.rmi.service.UserServiceImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class v2Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(6666);

        PersonService personService = new PersonServiceImpl();
        registry.bind("personService", personService);

        UserService userService = new UserServiceImpl();
        registry.bind("user", userService);

        System.out.println("server start up!");
    }
}
