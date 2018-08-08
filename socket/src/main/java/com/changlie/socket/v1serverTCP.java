package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class v1serverTCP {
    public static void main(String[] args) throws IOException {
        System.out.println("args: "+ Arrays.toString(args));
        int port = getPort(args);

        /** 创建ServerSocket*/
        // 创建一个ServerSocket在端口2013监听客户请求
        ServerSocket serverSocket =new ServerSocket(port);
        while (true) {
            // 侦听并接受到此Socket的连接,请求到来则产生一个Socket对象，并继续执行
            Socket socket = serverSocket.accept();

            /** 获取客户端传来的信息 */
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 获取从客户端读入的字符串
            String result = bufferedReader.readLine();
            System.out.println("Client say : " + result);

            /** 发送服务端准备传输的 */
            // 由Socket对象得到输出流，并构造PrintWriter对象
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.print("hello Client, I am Server! at "+new Date().toLocaleString());
            printWriter.flush();

            /** 关闭Socket*/
            printWriter.close();
            bufferedReader.close();
            socket.close();
        }
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
