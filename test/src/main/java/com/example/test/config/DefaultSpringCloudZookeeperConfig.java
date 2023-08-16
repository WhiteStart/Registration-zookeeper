//package com.example.test.config;//package com.example.test.com.example.registration.config;
//
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.CuratorCache;
//import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;
//import org.apache.zookeeper.data.Id;
//import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@Log4j2
//public class DefaultSpringCloudZookeeperConfig {
//    @Resource
//    private MyZookeeperProperties springCloudZookeeperProperties;
//
//    private CuratorCache curatorCache;
//
//    @Bean(destroyMethod = "close")
//    @ConditionalOnMissingBean
//    public CuratorFramework curatorFramework(RetryPolicy retryPolicy, org.springframework.cloud.zookeeper.ZookeeperProperties properties) throws Exception {
//        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
//        builder.connectString(properties.getConnectString());
//        if (StringUtils.isNotEmpty(springCloudZookeeperProperties.getUsername())
//                && StringUtils.isNotEmpty(springCloudZookeeperProperties.getPassword())) {
//            builder.authorization("digest", (springCloudZookeeperProperties.getUsername() + ":" + springCloudZookeeperProperties.getPassword()).getBytes());
//        }
//        CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
//        curator.start();
//
//        if (curator.checkExists().forPath("/services") != null) {
//            curator.delete().deletingChildrenIfNeeded().forPath("/services");
//        }
//
//        String digestString = "user:password";
//        List<ACL> list = new ArrayList<>();
////         将明文账户密码通过api生成密文
//        String digest = DigestAuthenticationProvider.generateDigest(digestString);
//        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", digest));
//        list.add(acl);
//
////        curatorCache = CuratorCache.build(curator, "/");
////        CuratorCacheListener listener = CuratorCacheListener.builder()
////                .forInitialized(() -> {
////                    log.info("-----初始化监听器");
////                })
////                .forChanges((pre, cur) -> {
////                    String prePath = pre.getPath();
////                    String curPath = cur.getPath();
////                    log.info("-----更新节点,{}=>{}", prePath, curPath);
////                })
////                .forCreates((node) -> {
////                    String path = node.getPath();
////                    log.info("-----创建节点,{}", path);
////                    String data = new String(node.getData());
////                })
////                .forDeletes((node) -> {
////                    String path = node.getPath();
////                    log.info("-----删除节点,{}", path);
////                })
////                .build();
////        curatorCache.listenable().addListener(listener);
////        curatorCache.start();
//
//        curator.create().withACL(list).forPath("/services", "123456".getBytes(StandardCharsets.UTF_8));
//
//        curator.blockUntilConnected(properties.getBlockUntilConnectedWait(), properties.getBlockUntilConnectedUnit());
//        log.info("connected to zookeeper");
//        return curator;
//    }
//}
