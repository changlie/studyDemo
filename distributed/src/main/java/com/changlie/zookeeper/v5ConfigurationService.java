package com.changlie.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class v5ConfigurationService {

    private static final int SESSION_TIMEOUT = 10000;
    private static final Charset CHARSET = Charset.forName("UTF-8");

    CountDownLatch connectedSignal = new CountDownLatch(1);

    protected ZooKeeper zk;

    public void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnectionWatcher(connectedSignal));
        connectedSignal.await();
    }


    public void write(String path, String value) throws InterruptedException,
            KeeperException {
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watcher) throws InterruptedException,
            KeeperException {
        long sessionId = zk.getSessionId();
        System.out.println("zooKeeperId: "+sessionId);
        byte[] data = zk.getData(path, watcher, null/*stat*/);
        return new String(data, CHARSET);
    }


    public void close() throws InterruptedException {
        zk.close();
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";

        new Thread(() -> {
            try {
                ConfigUpdater configUpdater = new ConfigUpdater(host);
                configUpdater.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                ConfigWatcher configWatcher = new ConfigWatcher(host);
                configWatcher.displayConfig();

                // stay alive until process is killed or thread is interrupted
                Thread.sleep(Long.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

}


class ConfigUpdater {

    public static final String PATH = "/config";

    private v5ConfigurationService store;
    private Random random = new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new v5ConfigurationService();
        store.connect(hosts);
    }

    public void run() throws InterruptedException, KeeperException {
        while (true) {
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n", PATH, value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

}

class ConfigWatcher implements Watcher {

    private v5ConfigurationService store;

    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new v5ConfigurationService();
        store.connect(hosts);
    }

    public void displayConfig() throws InterruptedException, KeeperException {
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n", ConfigUpdater.PATH, value);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                displayConfig();
            } catch (InterruptedException e) {
                System.err.println("Interrupted. Exiting.");
                Thread.currentThread().interrupt();
            } catch (KeeperException e) {
                System.err.printf("KeeperException: %s. Exiting.\n", e);
            }
        }
    }

}