//package com.example.registration.config;
//
//import lombok.extern.log4j.Log4j2;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.CuratorCache;
//import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PreDestroy;
//
//@Log4j2
//@Configuration
//public class ZookeeperConfig {
//
//    private CuratorFramework curatorFramework;
//    private CuratorCache curatorCache;
//
//    @Bean
//    public CuratorFramework curatorFramework() throws Exception {
////        String digestString = "user:password";
////        List<ACL> list = new ArrayList<>();
////         将明文账户密码通过api生成密文
////        String digest = DigestAuthenticationProvider.generateDigest(digestString);
////        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", digest));
////        list.add(acl);
//        curatorFramework = CuratorFrameworkFactory.builder()
////                .authorization("digest", digestString.getBytes(StandardCharsets.UTF_8))
////                .aclProvider(new ACLProvider() {
////                    @Override
////                    public List<ACL> getDefaultAcl() {
////                        return list;
////                    }
////
////                    @Override
////                    public List<ACL> getAclForPath(String s) {
////                        return list;
////                    }
////                })
//                .connectString("47.99.68.19:2181") // ZooKeeper服务器地址
//                .sessionTimeoutMs(5000) // 会话超时时间
//                .connectionTimeoutMs(5000) // 连接超时时间
//                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 重试策略
//                .build();
//        curatorFramework.start();
//
////        if (curatorFramework.checkExists().forPath("/services") != null) {
////            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/services");
////        }
//
////        String node = curatorFramework.create().creatingParentsIfNeeded()
////                .withMode(CreateMode.PERSISTENT)
////                .withACL(list, false).forPath("/auth", "Hello".getBytes());
////
////        CuratorCacheListener listener=CuratorCacheListener
////                .builder()
////                .forAll((type, oldNode, newNode)->{
////                    System.out.println("事件类型："+type+"\n\r原节点："+oldNode+"\n\r新节点"+newNode);
////                }).forInitialized(()->{
////                    System.out.println("初始化");
////                }).build();
////        curatorCache.listenable().addListener(listener);
////        curatorCache.start();
//
//        String registrationPath = "/services"; // 要监听的ZooKeeper节点路径
//        curatorCache = CuratorCache.build(curatorFramework, registrationPath);
//
//        CuratorCacheListener listener = CuratorCacheListener.builder()
//                .forInitialized(() -> {
//                    log.info("-----初始化监听器");
//                })
//                .forChanges((pre, cur) -> {
//                    String prePath = pre.getPath();
//                    String curPath = cur.getPath();
//                    log.info("-----更新节点,{}=>{}", prePath, curPath);
//                })
//                .forCreates((node) -> {
//                    String path = node.getPath();
//                    log.info("-----创建节点,{}", path);
//                    String data = new String(node.getData());
//                })
//                .forDeletes((node) -> {
//                    String path = node.getPath();
//                    log.info("-----删除节点,{}", path);
//                })
//                .build();
//        curatorCache.listenable().addListener(listener);
//        curatorCache.start();
//
//        return curatorFramework;
//    }
//
////    private List<ACL> list;
////    private String digestString = "user:password";
////
////    @PostConstruct
////    public void setAclList() throws NoSuchAlgorithmException {
////        list = new ArrayList<>();
////        // 将明文账户密码通过api生成密文
////        String digest = DigestAuthenticationProvider.generateDigest(digestString);
////        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", digest));
////        list.add(acl);
////    }
//
////    @Bean
////    public CuratorFramework curatorFramework() throws Exception {
////        curatorFramework = CuratorFrameworkFactory.builder()
//////                 预设登录时的账户密码
////                .authorization("digest", digestString.getBytes(StandardCharsets.UTF_8))
////                .aclProvider(new ACLProvider() {
////                    @Override
////                    public List<ACL> getDefaultAcl() {
////                        return list;
////                    }
////
////                    @Override
////                    public List<ACL> getAclForPath(String s) {
////                        return list;
////                    }
////                })
//////              // 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
////                .connectString("47.99.68.19:2181")
////                .sessionTimeoutMs(5000) // 会话超时时间
////                .connectionTimeoutMs(5000) // 连接超时时间
////                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 重试策略
////                .build();
////        curatorFramework.start();
////
////        if (curatorFramework.checkExists().forPath("/services") != null) {
////            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/services");
////        }
////
////        return curatorFramework;
////    }
////
////    @Bean
////    public CuratorCache curatorCache() {
////        curatorCache = CuratorCache.build(curatorFramework,
////                "/services");
////
////        CuratorCacheListener listener = CuratorCacheListener.builder()
////                .forInitialized(() -> {
////                    log.info("-----初始化监听器");
////                })
////                .forChanges((pre, cur) -> {
////                    String prePath = pre.getPath();
////                    String curPath = cur.getPath();
////                    log.info("-----更新节点,{}=>{}", prePath, curPath);
//////                    mailSender.sendEmail(to, "更新节点",
//////                            "旧节点:" + prePath + ",新节点:" + curPath);
////                })
////                .forCreates((node) -> {
////                    String path = node.getPath();
////                    log.info("-----创建节点,{}", path);
//////                    if (!"/services".equals(path)) {
//////                        mailSender.sendEmail(to, "创建节点", path);
//////                    }
////                    String data = new String(node.getData());
////                })
////                .forDeletes((node) -> {
////                    String path = node.getPath();
////                    log.info("-----删除节点,{}", path);
//////                    if (!"/services".equals(path)) {
//////                        mailSender.sendEmail(to, "删除节点", path);
//////                    }
////                })
////                .build();
////
////        curatorCache.listenable().addListener(listener);
//////        curatorCache.listenable().addListener(curatorCacheListener());
////        curatorCache.start();
////        return curatorCache;
////    }
//
//    @PreDestroy
//    public void cleanUp() {
//        curatorCache.close();
//        curatorFramework.close();
//    }
//
//}
