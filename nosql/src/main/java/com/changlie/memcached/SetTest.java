package com.changlie.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;

public class SetTest {
    private static MemCachedClient mcc = new MemCachedClient();

    static {
        String[] servers = {"192.168.0.102:2017"};
        //创建一个连接池
        SockIOPool pool = SockIOPool.getInstance();
        //设置缓存服务器
        pool.setServers(servers);
        //设置初始化连接数，最小连接数，最大连接数以及最大处理时间
        pool.setInitConn(50);
        pool.setMinConn(50);
        pool.setMaxConn(500);
        pool.setMaxIdle(1000 * 60 * 60);
        //设置主线程睡眠时间，每3秒苏醒一次，维持连接池大小
        //maintSleep 千万不要设置成30，访问量一大就出问题，单位是毫秒，推荐30000毫秒。
        pool.setMaintSleep(3000);
        //关闭套接字缓存
        pool.setNagle(false);
        //连接建立后的超时时间
        pool.setSocketTO(3000);
        //连接建立时的超时时间
        pool.setSocketConnectTO(0);
        //初始化连接池
        pool.initialize();
    }

    public static void main(String[] args) {

        try{
            // 连接本地的 Memcached 服务
            System.out.println("Connection to server sucessful.");

            // 存储数据
            mcc.set("cache1", "golang");

            // 输出值
            System.out.println("runoob value in cache - " + mcc.get("cache1"));

            // 保存 一个 对象
            mcc.add("infoObj", new Info());

            Info info = (Info) mcc.get("infoObj");
            if(info==null){
                System.out.println("save info obj is failed!");
            }else
                info.print("dog!");


        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }
}

class Info implements Serializable {
    void print(String msg){
        System.out.println("=> "+msg+" <= opt");
    }
}
