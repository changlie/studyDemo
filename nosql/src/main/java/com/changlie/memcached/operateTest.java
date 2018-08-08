package com.changlie.memcached;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Date;

public class operateTest {

    public static void main(String[] args) {
//        set();
//        add();
//        replace();
//        CAS();
//        append();
//        prepend();
//        get();
        gets();
//        delete();
//        incrAndDecr();
    }

    /**
     * 使用 incr 或 decr时， 保存 的key 应该为 （TODO 字符串）
     */
    private static void incrAndDecr() {
        String key = "incrAndDecrKEY";

        mcc.set(key, "99");

        System.out.println("-----: "+mcc.incr(key));
        System.out.println("after incr 1: "+mcc.get(key));

        System.out.println("-----: "+mcc.incr(key, 25));
        System.out.println("after incr 25: "+mcc.get(key));

        System.out.println("-----: "+mcc.decr(key));
        System.out.println("after decr 1: "+mcc.get(key));

        System.out.println("-----: "+mcc.decr(key, 14));
        System.out.println("after decr 14: "+mcc.get(key));

    }

    private static void delete() {
        String key = "deleteKey";

        mcc.add(key, "object!");
        System.out.println("before delete: "+mcc.get(key));

        mcc.delete(key);
        System.out.println("after delete: "+mcc.get(key));
    }

    private static void gets() {
        String key = "getssssKey";
        mcc.set(key, "forGets1");
        mcc.set(key, "forGets2");

        MemcachedItem item = mcc.gets(key);
        System.out.println("value: "+item.value+ "  casUnique: "+item.casUnique);
        System.out.println("value: "+item.getValue()+ "  casUnique: "+item.getCasUnique());
    }

    private static void get() {
        String key = "getKey";
        mcc.add(key, "forGetttttt");
        System.out.println("reshow: "+mcc.get(key));
    }

    private static void prepend() {
        String key = "prependKey";

        mcc.set(key, "init");

        System.out.println("before prepend: "+ mcc.get(key));

        mcc.prepend(key,  "prexxx");
        System.out.println("after prepend: "+ mcc.get(key));
    }

    private static void append() {
        String key = "appendKey";

        mcc.set(key, "init");

        System.out.println("before append: "+ mcc.get(key));

        mcc.append(key,  "apxxx");
        System.out.println("after append: "+ mcc.get(key));
    }

    /**
     * 使用cas 命令时，todo 要使用cas 命令获取上一次的casUinque
     */
    private static void CAS() {
        String key = "casKey";

        mcc.set(key, "init");
        System.out.println("before cas: "+ mcc.get(key));

        mcc.cas(key, "xxxfinal in CAS", mcc.gets(key).casUnique);
        System.out.println("after cas: "+ mcc.get(key));

    }

    private static void replace() {
        String key = "testReplace";
        mcc.set(key, "Hell");
        System.out.println("before replace >> "+mcc.get(key));

        mcc.replace(key, "change at "+new Date().toLocaleString());
        System.out.println("after replace >> "+mcc.get(key));

    }

    /**
     * 新增 一个 键值对;
     * 如果 add 的 key 已经存在，则不会更新数据(过期的 key 会更新)，之前的值将仍然保持相同，并且您将获得响应 NOT_STORED。
     */
    private static void add() {
        mcc.add("byAdd", "helloWorld", new Date(System.currentTimeMillis()+1000));

        System.out.println("step1: "+mcc.get("byAdd"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final: "+mcc.get("byAdd"));
    }

    /**
     * 新增 一个 key-value , 若 key 已存在，则覆盖 旧的键值对
     */
    private static void set() {
        mcc.set("TCP", "i don't know!");

        System.out.println(mcc.get("TCP")+ " <--");
    }


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
}
