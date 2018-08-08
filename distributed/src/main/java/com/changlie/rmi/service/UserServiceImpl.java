package com.changlie.rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws RemoteException {
    }



    @Override
    public int getAge(String username) throws RemoteException{
        System.out.println("get age start...");
        switch (username){
            case "tom":
                return 8;
            case "changlie":
                return 23;
            case "lisi":
                return 60;
        }
        return 18;
    }

    @Override
    public String getAddr(String username) throws RemoteException{
        System.out.println("get addr start...");
        switch (username){
            case "mattio":
                return "Italy";
            case "changlie":
                return "china";
            case "carriage":
                return "shenzhen";
        }
        return "earth";
    }
}
