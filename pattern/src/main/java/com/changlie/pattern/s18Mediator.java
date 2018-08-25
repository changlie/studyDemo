package com.changlie.pattern;

import org.junit.Test;

import java.util.Date;

public class s18Mediator {

    class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(String name){
            this.name  = name;
        }

        public void sendMessage(String message){
            ChatRoom.showMessage(this,message);
        }
    }

    static class ChatRoom {
        public static void showMessage(User user, String message){
            System.out.println(new Date().toString()  + " [" + user.getName() +"] : " + message);
        }
    }

    @Test
    public void test() throws Exception{
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");
    }
}
