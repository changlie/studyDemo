package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 多人聊天室
 */
public class v3clientTcp {

    private Socket client;
    /**
     * 与服务器连接，并输入发送消息
     */
    public v3clientTcp(String host, int port) throws Exception {
        client = new Socket(host, port);
    }

    public void start() throws Exception {
        try(BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter outor = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))){

            //启动一个 线程用来接收服务器传回的信息
            new ReceiveMsgThread(in).start();

            while (true) {
                String input = userInput.readLine();
                outor.println(input);

                if("bye".equals(input)){
                    break;
                }
            }

        }finally {
            client.close();
        }

        System.out.println("client finish close!");
    }



    /**
     * 用于监听服务器端向客户端发送消息线程类
     */
    static class ReceiveMsgThread extends Thread {


        private BufferedReader in;

        public ReceiveMsgThread(BufferedReader in) throws IOException {
           this.in = in;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String result = in.readLine();
                    if (result==null
                            || "".equals(result)
                            || "byeClient".equals(result)) {//客户端申请退出，服务端返回确认退出
                        break;
                    }

                    System.out.println("$"+result);
                }

                System.out.println("正在退出 。。。");
            } catch (Exception e) {
                System.out.println(e.getMessage()+" <= exception occur");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String host = getHost(args);
        int port = getPort(args);
        System.out.println("target server: " + host + ":" + port + "...");

        new v3clientTcp(host, port).start();//启动客户端

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
