package com.changlie.rmi;

import com.changlie.rmi.service.Person;
import com.changlie.rmi.service.PersonService;
import com.changlie.rmi.service.PersonServiceImpl;
import com.changlie.rmi.service.UserService;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        try {
            //调用远程  对象，注意RMI路径与接口必须与服务器配置一致
            PersonService personService = (PersonService) Naming.lookup("rmi://127.0.0.1:6600/PersonService");
            List<Person> personList = personService.GetList();
            for (Person person : personList) {
                System.out.println("ID:" + person.getId() + " Age:" + person.getAge() + " Name:" + person.getName());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
