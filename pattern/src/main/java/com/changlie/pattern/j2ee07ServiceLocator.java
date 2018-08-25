package com.changlie.pattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>步骤1 和步骤2，主要表现接口之间的多态性，指定行为方式。
 * <p>步骤3：也就是工厂模式的应用，通过类名来确定要实例化的对象。
 * <p>步骤4：用的是下面的传输对象模式。对实体类集合进行操作，主要是在集合中 获取/添加 实体类对象。
 * <p>步骤5定位器：使用步骤3来创建实例，使用步骤4来添加到集合，或者从集合中获取。（缓存中没有才会创建）
 * <p>步骤6:  调用步骤5得到实体类，并执行实体类的方法
 */
public class j2ee07ServiceLocator {
    interface Service {
        public String getName();

        public void execute();
    }

    static class Service1 implements Service {
        public void execute() {
            System.out.println("Executing Service1");
        }

        @Override
        public String getName() {
            return "Service1";
        }
    }

    static class Service2 implements Service {
        public void execute() {
            System.out.println("Executing Service2");
        }

        @Override
        public String getName() {
            return "Service2";
        }
    }

    static class InitialContext {
        public Object lookup(String jndiName) {
            if (jndiName.equalsIgnoreCase("SERVICE1")) {
                System.out.println("Looking up and creating a new Service1 object");
                return new Service1();
            } else if (jndiName.equalsIgnoreCase("SERVICE2")) {
                System.out.println("Looking up and creating a new Service2 object");
                return new Service2();
            }
            return null;
        }
    }

    static class Cache {
        private List<Service> services;

        public Cache() {
            services = new ArrayList<Service>();
        }

        public Service getService(String serviceName) {
            for (Service service : services) {
                if (service.getName().equalsIgnoreCase(serviceName)) {
                    System.out.println("Returning cached  " + serviceName + " object");
                    return service;
                }
            }
            return null;
        }

        public void addService(Service newService) {
            boolean exists = false;
            for (Service service : services) {
                if (service.getName().equalsIgnoreCase(newService.getName())) {
                    exists = true;
                }
            }
            if (!exists) {
                services.add(newService);
            }
        }
    }

    static class ServiceLocator {
        private static Cache cache;

        static {
            cache = new Cache();
        }

        public static Service getService(String jndiName) {

            Service service = cache.getService(jndiName);

            if (service != null) {
                return service;
            }

            InitialContext context = new InitialContext();
            Service service1 = (Service) context.lookup(jndiName);
            cache.addService(service1);
            return service1;
        }
    }

    @Test
    public void test() throws Exception {
        Service service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
        service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
    }

}
