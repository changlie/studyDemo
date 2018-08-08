package com.changlie.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class s2JoinGroup {

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

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        String groupName = "zoo";
        String memberName = "ducks";

        s2JoinGroup obj = new s2JoinGroup();
        obj.connect(host);
        obj.join(groupName, memberName);

        obj.close();

        // stay alive until process is killed or thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}

class ConnectionWatcher implements Watcher {



    private CountDownLatch connectedSignal;

    public ConnectionWatcher(CountDownLatch connectedSignal) {
        this.connectedSignal = connectedSignal;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("zookeeper state: "+event.getState());
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
        }
    }


}


