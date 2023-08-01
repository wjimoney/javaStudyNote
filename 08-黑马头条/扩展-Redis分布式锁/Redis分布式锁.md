---
typora-root-url: img
typora-copy-images-to: img
---

## 基本原理

通俗的说就是有很多人同时去一个地方“占坑”，如果占到，就执行逻辑。否则就必须等待，直到有人释放锁。

“占坑”可以去redis，可以去数据库，可以去任何大家都能访问的地方。

## 阶段一

<img src="/image-20210828191141327.png" alt="image-20210828191141327" style="zoom: 33%;" /> 

```asciiarmor
问题：setnx获取到了锁，执行业务代码异常或者程序在执行过程中宕机，因此没有执行删除锁逻辑，这就造成了死锁

解决：设置锁的自动过期，即使没有删除，超时会自动删除
```

```java
@Autowired
private StringRedisTemplate stringRedisTemplate;

@Test
public void testRedisLock() {
    //阶段一：setnx(set if not exists)
    Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", "111");
    //获取到锁，执行业务
    if (lock) {
        //获取到锁，开始执行业务逻辑
        System.out.println("哈哈，我拿到锁了，开始干活");
        //执行完成后，释放锁
        stringRedisTemplate.delete("lock");  //注意*****：如果在此之前报错或宕机会造成死锁
        System.out.println("干完了，释放锁");
    } else {
        //没获取到锁，等待100ms重试
        try {
            System.out.println("谁占着坑呢，赶紧了，我歇一会儿");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //重新获取锁
        testRedisLock(); //递归调用
    }
}
```

## 阶段二

<img src="/image-20210828191240311.png" alt="image-20210828191240311" style="zoom: 50%;" />

<img src="/image-20211128194759018.png" alt="image-20211128194759018" style="zoom:33%;" /> 

```asciiarmor
问题： setnx设置好，正要去设置过期时间，宕机。又死锁了。
解决： 设置过期时间和占位必须是原子的（最小执行单元，不可分割的操作）。
redis支持使用setnx ex命令。
set name lisi EX 10 NX
```



## 阶段三

<img src="/image-20210828191352890.png" alt="image-20210828191352890" style="zoom: 40%;" /> 

```java
//加锁的同时设置过期时间，二者是原子性操作，同时进行
Boolean lock = stringRedisTemplate.opsForValue()
        .setIfAbsent("lock", "1111", 10, TimeUnit.SECONDS);
```

```asciiarmor
问题：　删除锁直接删除？？？
如果由于业务执行时间很长，自己的锁已经过期，此时删除锁，有可能把别人正在持有的锁删除

解决：占锁的时候，值指定为uuid，每个人匹配是自己的锁才删除。
```

## 阶段四

<img src="/image-20210828191510591.png" alt="image-20210828191510591" style="zoom: 40%;" /> 

```java
String uuid = UUID.randomUUID().toString();
//为当前锁设置唯一的uuid，只有当uuid相同时才会进行删除锁的操作
Boolean lock = stringRedisTemplate.opsForValue()
        .setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
if (lock) {
    //执行业务逻辑
    
    String lockValue = stringRedisTemplate.opsForValue().get("lock");
    if (uuid.equals(lockValue)) { //判断是否为自己添加的锁
        //判断后是自己创建的锁，但还没删除正好过了10s，锁已经过期
        stringRedisTemplate.delete("lock"); //再执行删除，就会把其他线程的锁删掉
    } else {
        //不是自己的锁，不要乱动
    }
}
```

```asciiarmor
问题： 如果正好判断是当前值，正要删除锁的时候，锁已经过期，别人已经设置到了新的值。那么我们删除的是别人的锁

解决： 判断锁+删除锁必须保证原子性。使用redis+Lua脚本完成
```



## 阶段五-最终形态

<img src="/image-20210828191631068.png" alt="image-20210828191631068" style="zoom:45%;" /> 

```java
@Test
public void testRedisLockByLua() {
    String uuid = UUID.randomUUID().toString();
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    //设置过期时间
    Boolean lock = ops.setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
    if (lock) {
        //执行业务逻辑
        String lockValue = ops.get("lock");
        //lua脚本：保证【判断+删除】的原子性
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        stringRedisTemplate.execute(
                new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList("lock"), lockValue);
    }else {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testRedisLockByLua();
    }
}
```

必须保证如下两点：

- 加锁【占位+过期时间】原子性
- 删除锁【判断+删除】的原子性

**问题：如果业务执行时间过长，超过锁的超时时间，如何让锁的自动续期呢？**



## 使用Redisson实现分布式锁

setnx虽好，但是实现起来毕竟太过麻烦，一不小心就可能陷入并发编程的陷阱中，那么有没有更加简单的实现方式呢？答案就是`redisson`。

> Redisson是架设在[Redis](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.oschina.net%2Fp%2Fredis)基础上的一个Java驻内存数据网格（In-Memory Data Grid）。【[Redis官方推荐](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.redis.io%2Fclients)】
> Redisson在基于NIO的[Netty](https://links.jianshu.com/go?to=http%3A%2F%2Fnetty.io%2F)框架上，充分的利用了Redis键值数据库提供的一系列优势，在Java实用工具包中常用接口的基础上，为使用者提供了一系列具有分布式特性的常用工具类。使得原本作为协调单机多线程并发程序的工具包获得了协调分布式多机多线程并发系统的能力，大大降低了设计和研发大规模分布式系统的难度。同时结合各富特色的分布式服务，更进一步简化了分布式环境中程序相互之间的协作。

总而言之，`redisson`提供了一系列较为完善的工具类，其中就包含了分布式锁。用`redisson`实现分布式锁的流程极为简单。

### 1.引入依赖

```xml
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.14.0</version>
</dependency>
```

### 2.创建Redisson实例

```java
@Bean
public RedissonClient redisson() {
    // 1. Create config object
    Config config = new Config();
    config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    //config.useClusterServers()
             //use "rediss://" for SSL connection
            //.addNodeAddress("redis://127.0.0.1:6379", "redis://127.0.0.1:7181");
    return Redisson.create(config);
}
```

### 3.编写分布式锁代码

```java
@Autowired
private RedissonClient redissonClient;

@Test
public void testRedisson() {
    RLock lock = redissonClient.getLock("redisson:lock");
    try {
        //加锁
        lock.lock();
        //可以根据任务逻辑自行指定超时时间，指定之后就不会自动续期
        //rlock.lock(20000, TimeUnit.SECONDS);
        //执行业务逻辑
        System.out.println("开始执行任务...");
		Thread.sleep(1000 * 10);
        
        //rlock.unlock(); //释放代码
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //判断是不是自己添加锁
        if (rlock.isHeldByCurrentThread()) {
            rlock.unlock(); //释放代码
        }
    }
}
```

```
//1. 是不是自己添加的锁：uuid
//2. 锁自动删除：超时时间
//3. 原子性（添加锁 + 设置超时时间）
//4. 原子性（判断是不是自己添加锁+删除锁）
//5. 锁自动续期（看够watchdog负责续期）（业务逻辑执行时间超过了锁的超时时间）

原子：不可分割，是一个整体
```



### 4.实现原理

`Redisson分布式锁`的主要原理非常简单，利用了lua脚本的原子性。

在默认单线程的Redis中运行，是不会产生并发问题的。源码如下：

 RedissonLock.tryLockInnerAsync()方法

```java
<T> RFuture<T> tryLockInnerAsync(long waitTime, long leaseTime, TimeUnit unit, long threadId, RedisStrictCommand<T> command) {
        internalLockLeaseTime = unit.toMillis(leaseTime);

        return evalWriteAsync(getName(), LongCodec.INSTANCE, command,
                "if (redis.call('exists', KEYS[1]) == 0) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "return redis.call('pttl', KEYS[1]);",
                Collections.singletonList(getName()), internalLockLeaseTime, getLockName(threadId));
    }
```

这一段源码中，`redisson`利用了lua脚本的原子性，校验key是否存在，如果不存在就创建key并利用incrby加一操作（这步操作主要是为了实现可重入性）。`redisson`实现的分布式锁具备如下特性:

- 锁失效
- 锁续命：执行时间长的锁快要到期时会自动续命

- 可重入
- 操作原子性