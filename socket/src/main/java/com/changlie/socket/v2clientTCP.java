package com.changlie.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class v2clientTCP {

    public static void main(String[] args) throws IOException {
        String host = getHost(args);
        int port = getPort(args);
        System.out.println("target server: "+host+":"+port+"...");

        Socket socket =new Socket(host, port);
        socket.setSoTimeout(60000);

        PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String result ="";
        do{
            BufferedReader sysBuff =new BufferedReader(new InputStreamReader(System.in));
            printWriter.println(sysBuff.readLine());
            printWriter.flush();

            result = bufferedReader.readLine();
            System.out.println("Server say : " + result);
            if(result.contains("bye")){
                result = bufferedReader.readLine();
                System.out.println(result);
            }
        }while(result.indexOf("=>bye") == -1);


        printWriter.close();
        bufferedReader.close();
        socket.close();
    }


    static int getPort(String[] args) {
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


    static String getHost(String[] args) {
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
