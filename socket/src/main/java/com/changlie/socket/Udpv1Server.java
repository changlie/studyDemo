package com.changlie.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Udpv1Server {


    public static void main(String[] args) throws Exception {

        int port = 3333;
        DatagramSocket ds = new DatagramSocket(port);


        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        while (true){
            // 接收数据，放入数据报
            ds.receive(dp);
            // 从数据报中取出数据
            String info = new String(dp.getData(), 0, dp.getLength());
            System.out.println("接收到的信息是：" + info);

        }

    }

}
