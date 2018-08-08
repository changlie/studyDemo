package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class v1clientTCP {
    public static void main(String[] args) throws IOException {
        String host = getHost(args);
        int port = getPort(args);

        /** 创建Socket*/
        // 创建一个流套接字并将其连接到指定 IP 地址的指定端口号(本处是本机)
        Socket socket =new Socket(host,port);
        // 60s超时
        socket.setSoTimeout(60000);

        /** 发送客户端准备传输的信息 */
        // 由Socket对象得到输出流，并构造PrintWriter对象
        PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
        // 将输入读入的字符串输出到Server
        BufferedReader sysBuff =new BufferedReader(new InputStreamReader(System.in));
        String msg = sysBuff.readLine();
        printWriter.println(msg);
        // 刷新输出流，使Server马上收到该字符串
        printWriter.flush();

        /** 用于获取服务端传输来的信息 */
        // 由Socket对象得到输入流，并构造相应的BufferedReader对象
        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 输入读入一字符串
        String result = bufferedReader.readLine();
        System.out.println("Server say : " + result);

        /** 关闭Socket*/
        printWriter.close();
        bufferedReader.close();
        socket.close();
    }

    private static int getPort(String[] args) {
        try{
            if(args.length>1){
                String arg2 = args[1];
                return Integer.parseInt(arg2);
            }
            if(args.length>0){
                String arg1 = args[0];
                int index = arg1.indexOf(":");
                if(index>-1){
                    return Integer.parseInt(arg1.substring(index+1));
                }
            }
        }catch (Exception e){
            System.out.println("use default port: 2014");
        }
        return 2014;
    }


    private static String getHost(String[] args) {
        try{
            if(args.length>0){
                String arg1 = args[0];
                int index = arg1.indexOf(":");
                if(index>-1){
                    return arg1.substring(0, index);
                }

                return arg1;
            }
        }catch (Exception e){
            System.out.println("use default host: 127.0.0.1");
        }
        return "127.0.0.1";
    }
}
