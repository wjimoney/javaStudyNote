package com.itheima;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLockApplication.class);
    }

    @Bean
    public RedissonClient redisson() {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.200.130:6379");
//        config.useClusterServers()
//                 //use "rediss://" for SSL connection
//                .addNodeAddress("redis://127.0.0.1:6379", "redis://127.0.0.1:7181");

        //锁默认过期时间：30s
        config.setLockWatchdogTimeout(30 * 1000);
        return Redisson.create(config);
    }
}
