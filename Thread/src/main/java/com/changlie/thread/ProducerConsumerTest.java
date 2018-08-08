package com.changlie.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerTest {

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        new Producer(queue).start();
        TimeUnit.SECONDS.sleep(1);
        new Consumer(queue).start();
    }
}

class Producer extends Thread{
    static final int MAX_QUEUE_SIZE = 5;
    final Queue<Integer> sharedQueue;

    Producer(Queue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {

            synchronized (sharedQueue){
                validateWait();

                sharedQueue.add(i);
                System.out.println("进行生产 >　"+i);
                sharedQueue.notify();
            }
        }
    }

    private void validateWait() {
        try {
            if (sharedQueue.size() >= MAX_QUEUE_SIZE){
                System.out.println("队列已満，等待消费");
                sharedQueue.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread{
    final Queue<Integer> sharedQueue;

    Consumer(Queue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        int count = 0;
        while (true){
            synchronized (sharedQueue){
                System.out.println("-------------------------- consume before vw index:"+count+"------------------------------");
                validateWait();
                System.out.println("-------------------------- consume after vw index:"+count+"------------------------------");

                int number = sharedQueue.poll();
                System.out.println("进行消费 <<<　"+number);
                sharedQueue.notify();

                if(number==99){
                    System.out.println("消费完成！！！！");
                    break;
                }
            }
            System.out.println("loop: "+(count++));
        }
    }

    private void validateWait() {
        try {
            if (sharedQueue.size() == 0){
                System.out.println("队列空了，等待生产。。。");
                sharedQueue.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
