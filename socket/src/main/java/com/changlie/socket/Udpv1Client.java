package com.changlie.socket;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Udpv1Client {


    public static void main(String[] args) throws Exception {

        // 创建 用于发送和接收数据报的 收发器
        DatagramSocket ds = new DatagramSocket();

        // 将数据打包-->打成数据报
        String info = "hello world!ddd";

        String host = "localhost";
        int port = 3333;

        DatagramPacket dp = new DatagramPacket(info.getBytes(), info.length(), InetAddress.getByName(host),port);

        // 发出数据报
        ds.send(dp);
    }
}
