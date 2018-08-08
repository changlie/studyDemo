package com.changlie.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Curd {

    public static void main(String[] args) {
//        create();
//        update();
//        updateMany();
        delete();
        read();
    }

    private static void delete() {
        MongoDatabase dbConn = getDbConn();
        MongoCollection<Document> user = dbConn.getCollection("user");

        user.deleteOne(Filters.eq("name", "Mario"));
    }

    private static void read() {
        MongoDatabase dbConn = getDbConn();
        MongoCollection<Document> user = dbConn.getCollection("user");
        List<Document> result = new ArrayList<>();

        List<Document> documents = user.find().into(result);
        for (Document d: documents) {
            System.out.println(d.toJson());
        }
    }

    /**
     * 更新 多个
     */
    private static void updateMany() {
        MongoDatabase dbConn = getDbConn();
        MongoCollection<Document> user = dbConn.getCollection("user");

        Bson eq = Filters.eq("name", "Mattio");

        FindIterable<Document> documents = user.find(eq);
        Document u1 = documents.first();
        System.out.println("id: "+u1.remove("_id"));
//        System.out.println("name: "+u1.remove("name"));
        u1.put("age", 28);

        user.updateMany(eq, new Document("$set", u1));
    }

    /**
     * 更新 单个
     */
    private static void update() {
        MongoDatabase dbConn = getDbConn();
        MongoCollection<Document> user = dbConn.getCollection("user");

        Document document = new Document("name", "Mattio")
                .append("addr", "Italy")
                .append("age", 998);

        user.replaceOne(Filters.eq("name", "Mattio"), document);
    }

    private static void create() {
        MongoDatabase dbConn = getDbConn();
        MongoCollection<Document> user = dbConn.getCollection("user");

        Document document = new Document("name", "Mattio");
        document.append("addr", "Italy");
        document.append("age", 998);
        user.insertOne(document);
    }


    /**
     * 获取 数据库连接
     * @return
     */
    public  static MongoDatabase getDbConn(){
        String host = "192.168.0.102";
        int port = 27017;
        String username = "changlie";
        String pwd = "root";
        String database = "dbt";


        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(100);
        build.threadsAllowedToBlockForConnectionMultiplier(20);
        build.connectTimeout(5000);
        build.maxWaitTime(15000);
        MongoClientOptions options = build.build();


        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, pwd.toCharArray());

        MongoClient mongoClient = new MongoClient(serverAddress, credential, options);
        MongoDatabase db = mongoClient.getDatabase(database);
        return db;
    }
}
