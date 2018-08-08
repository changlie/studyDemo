package com.changlie.socket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class v4clientTcp {

    private Socket client;
    private FileInputStream fis;
    private DataOutputStream dos;

    public v4clientTcp(String host, int port, String filePath) {

        try {
            client = new Socket(host, port);
            //向服务端传送文件
            File file = new File(filePath);
            fis = new FileInputStream(file);
            dos = new DataOutputStream(client.getOutputStream());

            //文件名和长度
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            //传输文件
            byte[] sendBytes = new byte[1024];
            int length = 0;
            while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                dos.write(sendBytes, 0, length);
                dos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (dos != null)
                    dos.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        if(args.length<4){
            System.out.println("用法示例: java v4clientTcp -d 192.168.0.2:9999 -f /home/changlie/demo.js");
            return;
        }
        String host ;
        int port ;
        String filePath ;
        String d;
        if("-d".equals(args[0])){
            d = args[1];
            filePath = args[3];
        }else {
            d = args[3];
            filePath = args[1];
        }

        int i = d.indexOf(":");
        host = d.substring(0, i);
        port = Integer.parseInt(d.substring(i+1));

//        String host = "127.0.0.1"; // test
//        int port = 2014;    // test
//        String filePath = "/home/changlie/clearAds.js";// test

        System.out.println("target server: " + host + ":" + port + ";  filePath: "+filePath+"...");
        new v4clientTcp(host, port, filePath);
    }


    static int getPort(String[] args) {
        try {
            if (args.length > 1) {
                String arg2 = args[1];
                return Integer.parseInt(arg2);
            }
            if (args.length > 0) {
                String arg1 = args[0];
                int index = arg1.indexOf(":");
                if (index > -1) {
                    return Integer.parseInt(arg1.substring(index + 1));
                }
            }
        } catch (Exception e) {
            System.out.println("use default port: 2014");
        }
        return 2014;
    }


    static String getHost(String[] args) {
        try {
            if (args.length > 0) {
                String arg1 = args[0];
                int index = arg1.indexOf(":");
                if (index > -1) {
                    return arg1.substring(0, index);
                }

                return arg1;
            }
        } catch (Exception e) {
            System.out.println("use default host: 127.0.0.1");
        }
        return "127.0.0.1";
    }
}
