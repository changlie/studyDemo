package com.changlie;

import java.nio.CharBuffer;

public class s02Buffer {

    public static void main(String[] args) {
        CharBuffer charBuffer = CharBuffer.allocate(1024);

        // todo step1
        charBuffer.put("this is nio world! 歡迎  welcome");
        charBuffer.append(" abc -> buffer. ");

        // 切换 到 读模式
        charBuffer.flip();
        char[] chars = new char[charBuffer.limit()];
        charBuffer.get(chars);
        System.out.println("result0: "+ new String(chars));


        // todo step2
        // 保存未读数据，切换到写模式
        charBuffer.compact();
        charBuffer.append(" destiny again!");
        charBuffer.append(" 餓 地是有 ");

        // 切换 到 读模式
        charBuffer.flip();
        // mark()/reset() 用于重复读
        for (int i = 0; i < 5; i++) {
            charBuffer.mark();
            char[] chars1 = new char[charBuffer.limit()];
            charBuffer.get(chars1);
            System.out.println("result1: "+ new String(chars1));
            charBuffer.reset();
        }


        // todo step3
        // 保存未读数据，切换到写模式
        charBuffer.compact();
        charBuffer.append(" final data piped 最后的最后! ");

        // 切换 到 读模式
        charBuffer.flip();

        char[] chars1 = new char[charBuffer.limit()];
        charBuffer.get(chars1);
        System.out.println("result2: "+ new String(chars1));
    }
}
