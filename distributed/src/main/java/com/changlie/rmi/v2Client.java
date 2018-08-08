package com.changlie.rmi;

import com.changlie.rmi.service.Person;
import com.changlie.rmi.service.PersonService;
import com.changlie.rmi.service.UserService;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class v2Client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 6666);

        PersonService personService = (PersonService) registry.lookup("personService");
        List<Person> personList = personService.GetList();
        for (Person person : personList) {
            System.out.println("ID:" + person.getId() + " Age:" + person.getAge() + " Name:" + person.getName());
        }

        System.out.println("-------------------------");

        UserService userService = (UserService) registry.lookup("user");
        String addr = userService.getAddr("chanlie");
        int age = userService.getAge("changlie");
        System.out.printf("changlie age:%d, addr:%s \n", age, addr);

        System.out.println("client finish request!!!");
    }
}
