package com.changlie.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class s4DeleteGroup {

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


    /**
     * 遍历 子列表。
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
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
                System.out.println("cversion: "+stat.getCversion());
                System.out.println(childPath+" : "+ (data==null?null: new String(data))+ "  meta info:"+stat);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    /**
     * 级联删除
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void cascadeDelete(String groupName) throws KeeperException,
            InterruptedException {
        String path = "/" + groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }

            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    void delete(String path) throws KeeperException, InterruptedException {
        zk.delete(path, -1);
        System.out.println("finish delete node : "+path);
    }

    void setData(String path, String data, int version) throws KeeperException, InterruptedException {
        byte[] bytes = data.getBytes();
        zk.setData(path, bytes, version);
    }

    String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, false, null);
        if(data == null) return null;

        return new String(data);
    }

    int getVersion(String path) throws KeeperException, InterruptedException {
        Stat stat =  new Stat();
        byte[] data = zk.getData(path, false, stat);
        System.out.println("get znode "+path+" version: "+stat.getVersion());

        return stat.getVersion();
    }


    public static void main(String[] args) throws Exception {
        String host = "localhost";
        String groupName = "tempnode";

        String path = "/zoo";

        s4DeleteGroup obj = new s4DeleteGroup();
        obj.connect(host);

        int version = obj.getVersion(path);
        path.split("abc");
        System.out.println("before: " + obj.getData(path));
        obj.setData(path, "this's zoo in China!", version);
        System.out.println("after: " + obj.getData(path));


//        obj.delete("/zoo/temp_node");
//        obj.cascadeDelete(groupName);

        obj.close();

    }
}




