package com.example.registration.config;

import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Configuration
public class ZookeeperConfig implements InitializingBean {

    private final CuratorFramework curatorFramework;

    public ZookeeperConfig(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CuratorCache curatorCache = CuratorCache.build(curatorFramework,
                "/services");
        curatorCache.start();
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forInitialized(() -> {
                    log.info("-----初始化监听器");
                })
                .forCreates((node) -> {
                    String[] path = node.getPath().split("/");
                    String data = new String(node.getData());
                    if (path.length >= 3 && !"".equals(data)) {
                        log.info("-----{}服务上线,具体信息:{}", path[2], data);
                    }
                })
                .forDeletes((node) -> {
                    String[] path = node.getPath().split("/");
                    String data = new String(node.getData());
                    if (path.length >= 3) {
                        log.info("-----{}服务下线,具体信息:{}", path[2], data);
                    }
                })
                .build();

        curatorCache.listenable().addListener(listener);

        Mono.never().doOnTerminate(() -> {
            curatorCache.close();
            curatorFramework.close();
        }).subscribeOn(Schedulers.boundedElastic()).block();
    }
}
