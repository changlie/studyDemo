package com.changlie.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *  这是基于UDP聊天终端， 包含 接收和发送信息的功能
 */
public class UdpEndPoint {


    public UdpEndPoint(String destHost, int destPort, int listenerPort) {
        try (DatagramSocket datagramSocket = new DatagramSocket(listenerPort);
             BufferedReader userInput =new BufferedReader(new InputStreamReader(System.in));) {

            //启动接收线程
            new ReceivedThread(datagramSocket).start();

            //开启发送循环监听
            DatagramPacket dp;
            while (true){
                String info = userInput.readLine();

                dp = new DatagramPacket(info.getBytes(),
                        info.length(), InetAddress.getByName(destHost), destPort);

                // 发出数据报
                datagramSocket.send(dp);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    static class ReceivedThread extends Thread{
        DatagramSocket datagramSocket;

        public ReceivedThread(DatagramSocket datagramSocket){
            this.datagramSocket = datagramSocket;
        }

        @Override
        public void run() {
            try{
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                while (true){
                    // 接收数据，放入数据报
                    datagramSocket.receive(dp);
                    // 从数据报中取出数据
                    String info = new String(dp.getData(), 0, dp.getLength());
                    System.out.println("接收到的信息是：" + info);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String destHost = "127.0.0.1";
        int destPort = 2012;
        int listenerPort = 2102;
        if(args.length==3){
            listenerPort = Integer.parseInt(args[2]);
        }
        if(args.length>1){
            destPort = Integer.parseInt(args[1]);
        }
        if(args.length>0){
            destHost = args[0];
        }
        System.out.println("发送目标IP/端口: "+destHost+":"+destPort+"  本地端口监听: "+listenerPort);

        new UdpEndPoint(destHost, destPort, listenerPort);
    }
}
