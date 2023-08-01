package com.itheima.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
public class StockController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 第一种实现，进程内就存在线程安全问题
     * 可以只启动一个进程测试
     */
    //http://localhost:8080/deduct_stock1
    @RequestMapping("/deduct_stock1")
    public void deductStock1() {

        String stock = stringRedisTemplate.opsForValue().get("stock");
        if (StringUtils.isEmpty(stock) || stock.equals("0")) {
            System.out.println("售罄了……");
            return;
        }
        int stockNum = Integer.parseInt(stock);
        if (stockNum > 0) {
            //设置库存减1
            int realStock = stockNum - 1;
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
            System.out.println("设置库存" + realStock);
        } else {
            System.out.println("库存不足");
        }
    }

    /**
     * 第二种实现，使用synchronized加锁
     * 可以只启动一个进程测试
     */
    @RequestMapping("/deduct_stock2")
    public void deductStock2() {

        synchronized (this) {
            String stock = stringRedisTemplate.opsForValue().get("stock");
            int stockNum = Integer.parseInt(stock);
            if (stockNum > 0) {
                //设置库存减1
                int realStock = stockNum - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("设置库存" + realStock);
            } else {
                System.out.println("库存不足");
            }
        }

    }

    /**
     * 第三种实现，使用redis中的setIfAbsent(setnx命令)实现分布式锁
     */
    @RequestMapping("/deduct_stock3")
    public void deductStock3() {

        //在获取到锁的时候，给锁分配一个id
        String opId = UUID.randomUUID().toString();
        Boolean stockLock = stringRedisTemplate
                .opsForValue().setIfAbsent("stockLock", opId, Duration.ofSeconds(30));

        if (stockLock) {

            try {
                String stock = stringRedisTemplate.opsForValue().get("stock");
                int stockNum = Integer.parseInt(stock);
                if (stockNum > 0) {
                    //设置库存减1
                    int realStock = stockNum - 1;
                    stringRedisTemplate.opsForValue().set("stock", realStock + "");
                    System.out.println("设置库存" + realStock);
                } else {
                    System.out.println("库存不足");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opId.equals(stringRedisTemplate
                        .opsForValue().get("stockLock"))) {
                    stringRedisTemplate.delete("stockLock");
                }
            }

        }

    }


    @Autowired
    private RedissonClient redissonClient;

    /**
     * 第四种实现，使用redisson实现
     */
    @RequestMapping("/deduct_stock4")
    public void deductStock4() {

        RLock lock = redissonClient.getLock("redisson:stockLock");
        try {
            //加锁
            lock.lock();
            String stock = stringRedisTemplate.opsForValue().get("stock");
            int stockNum = Integer.parseInt(stock);
            if (stockNum > 0) {
                //设置库存减1
                int realStock = stockNum - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("设置库存" + realStock);
            } else {
                System.out.println("库存不足");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
