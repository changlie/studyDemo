package com.changlie.redis;


import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;

public class ConnectTest1 {
    static ShardedJedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        //集群
        JedisShardInfo jedisShardInfo = new JedisShardInfo("192.168.0.103");
        jedisShardInfo.setPassword("root");
        List<JedisShardInfo> list = new LinkedList<>();
        list.add(jedisShardInfo);
        pool = new ShardedJedisPool(config, list);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        jedis.set("name", "tome");
        System.out.println(jedis.get("name"));
    }
}
