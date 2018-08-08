package com.changlie.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class s3ListGroup {

    private static final int SESSION_TIMEOUT = 10000;

    CountDownLatch connectedSignal = new CountDownLatch(1);

    protected ZooKeeper zk;

    public void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnectionWatcher(connectedSignal));
        connectedSignal.await();
    }


    public void close() throws InterruptedException {
        zk.close();
    }

    //添加组成员。
    public void join(String groupName, String memberName) throws KeeperException,
            InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createdPath = zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("Created " + createdPath);
    }


    void list(String groupName) throws KeeperException,
            InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.printf("No members in group %s\n", groupName);
                System.exit(1);
            }
            for (String child : children) {
                String childPath = path + "/" + child;
                Stat stat = new Stat();
                byte[] data = zk.getData(childPath, false, stat);
                System.out.println(childPath+" : "+ (data==null?null: new String(data))+ "  meta info:"+stat);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        String groupName = "zoo";

        s3ListGroup obj = new s3ListGroup();
        obj.connect(host);

        obj.list(groupName);

        obj.close();

    }
}




