package com.changlie.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectTest2 {
    static  JedisPool jedisPool ;
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(10);
            config.setMaxTotal(20);
            config.setMaxWaitMillis(9000);

            jedisPool = new JedisPool(config, "192.168.0.103", 6379, 5000, "root");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = jedisPool.getResource();
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }
}
