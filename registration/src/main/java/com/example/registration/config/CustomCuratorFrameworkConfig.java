package com.example.registration.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.zookeeper.ConditionalOnZookeeperEnabled;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@BootstrapConfiguration
//@ConditionalOnZookeeperEnabled
public class CustomCuratorFrameworkConfig {

    @Autowired(required = false)
    private EnsembleProvider ensembleProvider;

    @Bean
//    @ConditionalOnMissingBean
    public ZookeeperProperties zookeeperProperties() {
        return new ZookeeperProperties();
    }

    @Bean
//    @ConditionalOnMissingBean
    @Primary
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy, ZookeeperProperties properties) throws Exception{
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        if (this.ensembleProvider != null) {
            builder.ensembleProvider(this.ensembleProvider);
        } else {
            builder.connectString(properties.getConnectString());
        }

        builder.retryPolicy(retryPolicy);
        String digestString = "user:password";
        List<ACL> list = new ArrayList<>();
//         将明文账户密码通过api生成密文
        String digest = DigestAuthenticationProvider.generateDigest(digestString);
        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", digest));
        list.add(acl);

        builder.authorization("digest", digestString.getBytes())
                .aclProvider(new ACLProvider() {
                    @Override
                    public List<ACL> getDefaultAcl() {
                        return list;
                    }

                    @Override
                    public List<ACL> getAclForPath(String path) {
                        return list;
                    }
                });

        CuratorFramework curator =  builder.build();
        curator.start();
        curator.create().withACL(list).forPath("/test123");
        curator.blockUntilConnected(properties.getBlockUntilConnectedWait(), properties.getBlockUntilConnectedUnit());
        return curator;
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryPolicy exponentialBackoffRetry(ZookeeperProperties properties) {
        return new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries(), properties.getMaxSleepMs());
    }

}