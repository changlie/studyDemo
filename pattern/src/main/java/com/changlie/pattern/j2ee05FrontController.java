package com.changlie.pattern;

import org.junit.Test;

public class j2ee05FrontController {
    class HomeView {
        public void show(){
            System.out.println("Displaying Home Page");
        }
    }

    class StudentView {
        public void show(){
            System.out.println("Displaying Student Page");
        }
    }

    class Dispatcher {
        private StudentView studentView;
        private HomeView homeView;
        public Dispatcher(){
            studentView = new StudentView();
            homeView = new HomeView();
        }

        public void dispatch(String request){
            if(request.equalsIgnoreCase("STUDENT")){
                studentView.show();
            }else{
                homeView.show();
            }
        }
    }

    class FrontController {

        private Dispatcher dispatcher;

        public FrontController(){
            dispatcher = new Dispatcher();
        }

        private boolean isAuthenticUser(){
            System.out.println("User is authenticated successfully.");
            return true;
        }

        private void trackRequest(String request){
            System.out.println("Page requested: " + request);
        }

        public void dispatchRequest(String request){
            //记录每一个请求
            trackRequest(request);
            //对用户进行身份验证
            if(isAuthenticUser()){
                dispatcher.dispatch(request);
            }
            System.out.println("=====================> request finished.");
        }
    }

    @Test
    public void test() throws Exception{
        FrontController frontController = new FrontController();
        frontController.dispatchRequest("HOME");
        frontController.dispatchRequest("STUDENT");
    }
}
