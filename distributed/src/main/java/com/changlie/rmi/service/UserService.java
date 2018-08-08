package com.changlie.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;


//此为远程对象调用的接口，必须继承Remote类, 所有方法需要抛出RemoteException
public interface UserService extends Remote {
    int getAge(String username) throws RemoteException;

    String getAddr(String username) throws RemoteException;
}
