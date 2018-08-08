package com.changlie.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;



//此为远程对象调用的接口，必须继承Remote类, 所有方法需要抛出RemoteException
public interface PersonService extends Remote {
    List<Person> GetList() throws RemoteException;
}