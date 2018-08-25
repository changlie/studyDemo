package com.changlie.pattern;

public class s03Singleton {

    public static void main(String[] args) {
        Singleton5.init();
        Singleton5 instance = Singleton5.getInstance();
    }

}

/**
 * 一。
 是否 Lazy 初始化：是
 是否多线程安全：否
 实现难度：易
 */
class Singleton1{
    private static Singleton1 instance;
    private Singleton1 (){}

    public static Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }
}

/**
 * 二。
 是否 Lazy 初始化：是
 是否多线程安全：是
 实现难度：易
 */
class Singleton2{
    private static Singleton2 instance;
    private Singleton2 (){}
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 三。
 是否 Lazy 初始化：否
 是否多线程安全：是
 实现难度：易
 */
class Singleton3{
    private static Singleton3 instance = new Singleton3();
    private Singleton3 (){}
    public static Singleton3 getInstance() {
        return instance;
    }
}

/**
 * 四。
 是否 Lazy 初始化：是
 是否多线程安全：是
 实现难度：较复杂
 */
class Singleton4{
    private volatile static Singleton4 singleton;
    private Singleton4 (){}
    public static Singleton4 getSingleton() {
        if (singleton == null) {
            synchronized (Singleton4.class) {
                if (singleton == null) {
                    singleton = new Singleton4();
                }
            }
        }
        return singleton;
    }
}

/**
 * 五。
 是否 Lazy 初始化：是
 是否多线程安全：是
 实现难度：一般
 */
class Singleton5{
    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }
    private Singleton5 (){
        System.out.println("create Singleton5");
    }
    public static final Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public static void init(){
        System.out.println("init Singleton5");
    }
}

