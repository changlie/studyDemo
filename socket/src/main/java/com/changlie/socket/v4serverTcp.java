package com.changlie.socket;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class v4serverTcp {


    private ServerSocket server;

    public v4serverTcp(String path, int port) throws Exception {
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();
                receiveFile(client, path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveFile(Socket client, String path) {
        DataInputStream dis=null;
        FileOutputStream fos=null;
        try {
            dis = new DataInputStream(client.getInputStream());
            //文件名和长度
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();

            fos = new FileOutputStream(new File(path+ System.currentTimeMillis() + "-" + fileName));

            byte[] sendBytes = new byte[1024];
            int transLen = 0;
            System.out.println("----开始接收文件<" + fileName + ">,文件大小为<" + fileLength + ">----");
            while (true) {
                int read = 0;
                read = dis.read(sendBytes);
                if (read == -1)
                    break;
                transLen += read;
                System.out.println("接收文件进度" + 100 * transLen / fileLength + "%...");
                fos.write(sendBytes, 0, read);
                fos.flush();
            }
            System.out.println("----接收文件<" + fileName + ">成功-------");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(dis!=null) dis.close();
                if(fos!=null) fos.close();
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        URL resource = v4serverTcp.class.getResource("");
        String path = resource.getPath();
//        path = "/home/changlie/ws/"; // test
        System.out.println("save path: "+path);

        int port = getPort(args);
        System.out.println("server listen port: "+port+"...");

        new v4serverTcp(path, port);
    }


    private static int getPort(String[] args) {
        try{
            if(args.length>0){
                return Integer.parseInt(args[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 2014;
    }
}
