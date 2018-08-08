package com.changlie.thread;

public class WaitNotify {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new Lock();

        new ThreadOne(lock).start();

        Thread.sleep(700);

        new ThreadTwo(lock).start();
        new ThreadThree(lock).start();
    }
}


class Lock{
    String name = "dk"+System.currentTimeMillis();
}

class ThreadOne extends Thread{
    Lock share;

    public ThreadOne(Lock share) {
        this.share = share;
    }

    @Override
    public void run() {
        System.out.println("Thread1 start!");

        synchronized (share){
            try {
                share.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread1 finish!");
    }
}


class ThreadTwo extends Thread{
    Lock share;

    public ThreadTwo(Lock share) {
        this.share = share;
    }

    @Override
    public void run() {
        System.out.println("Thread22 start!");

        synchronized (share){
            try {
                share.notify();
                System.out.println("notify Thread1.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread22 finish!");
    }
}


class ThreadThree extends Thread{
    Lock share;

    public ThreadThree(Lock share) {
        this.share = share;
    }

    @Override
    public void run() {
        System.out.println("Thread333 start!");

        synchronized (share){
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread3333 finish!");
    }
}
