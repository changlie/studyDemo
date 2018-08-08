package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class v2serverTCP {

    public v2serverTCP(int port)throws IOException {

        ServerSocket serverSocket =new ServerSocket(port);
        try {

            while (true) {
                Socket socket = serverSocket.accept();
                new CreateServerThread(socket).start();//当有请求时，启一个线程处理
            }

        }catch (IOException e) {

        }finally {

        }
    }

    //线程类
    class CreateServerThread extends Thread {
        private Socket client;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;

        public CreateServerThread(Socket s)throws IOException {
            client = s;
            bufferedReader =new BufferedReader(new InputStreamReader(client.getInputStream()));

            printWriter =new PrintWriter(client.getOutputStream(),true);
            System.out.println("Client(" + getName() +") come in...");

        }

        public void run() {
            try {
                String line;

                do{
                    line = bufferedReader.readLine();
                    if(line==null || "".equals(line)) continue;

                    System.out.println("Client(" + getName() +") say: " + line);
                    printWriter.println("received msg: "+line+",continue, Client(" + getName() +")!");
                    printWriter.flush();
                } while (!"bye".equals(line));

                System.out.println("------------system finish start!---------");
                printWriter.println("=>bye, Client(" + getName() +")!~~~~");
                printWriter.flush();
                System.out.println("------------system finish end!---------");

                System.out.println("Client(" + getName() +") exit!");
            }catch (IOException e) {
                System.out.println(e.getMessage()+" => unKown Error!");
            }finally {
                try {
                    printWriter.close();
                    bufferedReader.close();
                    client.close();
                }catch (Exception e){
                    System.out.println("exit fail!!!");
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = getPort(args);
        System.out.println("server listen port: "+port+"...");
        new v2serverTCP(port);
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
