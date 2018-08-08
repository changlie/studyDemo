package com.changlie.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;


/**
 * TODO  mongodb  远程，密码 连接
 * 服务器端：
 * mongod  -dbpath ./data --auth --bind_ip_all
 * 客户端：
 * mongo 192.168.1.200:27017/database  -u user  -p  password
 */
public class ConnectTest {


    public static void main(String[] args) {

        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(100);
        build.threadsAllowedToBlockForConnectionMultiplier(20);
        build.connectTimeout(5000);
        build.maxWaitTime(15000);
        MongoClientOptions options = build.build();

        String host = "192.168.0.102";
        int port = 27017;
        String username = "changlie";
        String pwd = "root";
        String database = "dbt";

        ServerAddress serverAddress = new ServerAddress(host, port);
//        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
//        addrs.add(serverAddress);

        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, pwd.toCharArray());
//        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//        credentials.add(credential);

        MongoClient mongoClient = new MongoClient(serverAddress, credential, options);
//        MongoClient mongoClient = new MongoClient(serverAddress, credential, options);
        MongoDatabase db = mongoClient.getDatabase(database);

        MongoIterable<String> collectionNames = db.listCollectionNames();
        MongoCursor<String> iterator = collectionNames.iterator();
        while (iterator.hasNext()){
            String colName = iterator.next();
            System.out.println("col name: "+colName);
        }

    }
}
