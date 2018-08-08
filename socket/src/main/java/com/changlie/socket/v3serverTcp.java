package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class v3serverTcp {
    private static final ConcurrentHashMap<String, ServerThread> userSockets = new ConcurrentHashMap<>();

    /**
     * 创建服务端Socket,创建向客户端发送消息线程,监听客户端请求并处理
     */
    public v3serverTcp(int port)throws IOException {

        try(ServerSocket server = new ServerSocket(port)) {
            while(true){//监听客户端请求，启个线程处理
                Socket socket = server.accept();
                new ServerThread(socket).start();
            }
        }
    }

    private void broadcastMsg(String msg){
        System.out.println(msg);
        for (ServerThread  st : userSockets.values()) {
            st.sendMessage(msg);
        }
    }


    /**
     * 服务器线程类
     */
    class ServerThread extends Thread{
        private Socket client;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

         ServerThread(Socket s)throws IOException{
            client = s;
            out =new PrintWriter(client.getOutputStream(),true);
            in =new BufferedReader(new InputStreamReader(client.getInputStream()));
            sendMessage("成功连上聊天室,请输入你的名字：");
        }

        @Override
        public void run() {
            try {
                int flag =0;
                String line;


                do{
                    line = in.readLine();
                    if(line==null){
                        break;
                    }
                    if("".equals(line)) continue;
                    System.out.println("----------------"+line+"-------------------");

                    //查看在线用户列表
                    if ("users".equals(line)) {
                        sendMessage(this.listOnlineUsers());
                        continue;
                    }
                    //第一次进入，保存名字
                    if(flag++ ==0){
                        name = line;
                        userSockets.put(name, this);

                        sendMessage(name +"你好,可以开始聊天了...");
                        broadcastMsg("Client<" + name +">进入聊天室...");
                        continue;
                    }

                    broadcastMsg("Client<" + name +"> say : " + line);

                }while(!"bye".equals(line));

                sendMessage("byeClient");
            }catch (Exception e) {
                e.printStackTrace();
            }finally{//用户退出聊天室
                try {
                    in.close();
                    out.close();
                    client.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                userSockets.remove(name);
                broadcastMsg("Client<" + name +">退出了聊天室");
            }
        }

        //向客户端发送一条消息
         void sendMessage(String msg){
            out.println(msg);
        }

        //统计在线用户列表
        private String listOnlineUsers() {
            StringBuilder s = new StringBuilder();
            s.append("--- 在线用户列表 ---\015\012");
            for (String userName: userSockets.keySet()) {
                s.append("[").append(userName).append("]\015\012");
            }
            s.append("--------------------");
            return s.toString();
        }
    }

    public static void main(String[] args)throws IOException {
        int port = getPort(args);
        System.out.println("server listen port: "+port+"...");
        new v3serverTcp(port);//启动服务端
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
