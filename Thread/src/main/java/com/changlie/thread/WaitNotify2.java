package com.changlie.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class WaitNotify2 {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        new WaitThread(lock, "WAT1").start();
        new WaitThread(lock, "WAT2").start();
        new WaitThread(lock, "WAT3").start();
        new WaitThread(lock, "WAT4").start();
        new WaitThread(lock, "WAT5").start();

        TimeUnit.SECONDS.sleep(1);


        new NotifyThread(lock, 5).start();
    }

    static class WaitThread extends Thread{
        Object shareLock;

        public WaitThread(Object shareLock, String name) {
            this.shareLock = shareLock;
            this.setName(name);
        }

        @Override
        public void run() {
            synchronized (shareLock){
                System.out.println(this.getName()+" enter wait...");
                try {
                    shareLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(this.getName()+" action over...");
        }
    }


    static  class NotifyThread extends Thread{
        Object shareLock;
        int count;

        public NotifyThread(Object shareLock, int count) {
            this.shareLock = shareLock;
            this.count = count;
        }


        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                synchronized (shareLock){
                    shareLock.notify();
                    System.out.println("notify thread number "+i+" times!");
                }

                ReentrantLock reentrantLock = new ReentrantLock();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("notify finish!");
        }
    }
}


