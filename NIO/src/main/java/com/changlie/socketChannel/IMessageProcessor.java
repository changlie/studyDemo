package com.changlie.socketChannel;

public interface IMessageProcessor {

    public void process(Message message, WriteProxy writeProxy);

}