import com.itheima.RedisLockApplication;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = RedisLockApplication.class)
public class LockTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisLock() {
        //阶段一：setnx(set if not exists)
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", "111");
        //获取到锁，执行业务
        if (lock) {
            //设置过期时间
            stringRedisTemplate.expire("lock", 10, TimeUnit.SECONDS);
            //获取到锁，开始执行业务逻辑
            System.out.println("哈哈，我拿到锁了，开始干活");
            //执行完成后，释放锁
            //注意*****：如果在此之前报错或宕机会造成死锁
            stringRedisTemplate.delete("lock");
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
            testRedisLock();
        }
    }


    @Test
    public void testRedisAtomLock() {
        //加锁的同时设置过期时间，二者是原子性操作
        Boolean lock = stringRedisTemplate.opsForValue()
                .setIfAbsent("lock", "1111", 10, TimeUnit.SECONDS);
        if (lock) {
            //模拟超长的业务执行时间
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.delete("lock");
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testRedisAtomLock();
        }
    }

    @Test
    public void testRedisAtomUUIDLock() {
        String uuid = UUID.randomUUID().toString();
        //为当前锁设置唯一的uuid，只有当uuid相同时才会进行删除锁的操作
        Boolean lock = stringRedisTemplate.opsForValue()
                .setIfAbsent("lock", uuid, 5, TimeUnit.SECONDS);
        if (lock) {
            //获取到锁：执行业务逻辑

            System.out.println("任务执行完成，准备删除锁");
            String lockValue = stringRedisTemplate.opsForValue().get("lock");
            //if (uuid.equals(lockValue)) {
            if (Objects.equals(lockValue, uuid)) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stringRedisTemplate.delete("lock");
            } else {
                //不是自己的锁，不要乱动
            }
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testRedisAtomUUIDLock();
        }
    }

    @Test
    public void testRedisLockByLua() {
        String uuid = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //设置过期时间
        Boolean lock = ops.setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        if (lock) {
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
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testRedisLockByLua();
        }
    }

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testRedisson() {
        //1. 是不是自己添加的锁：uuid
        //2. 锁自动删除：超时时间
        //3. 原子性（添加锁+ 超时时间）
        //4. 原子性（判断是不是自己添加锁+删除锁）
        //5. 锁自动续期（业务逻辑执行时间超过了锁的超时时间）
        RLock rlock = redissonClient.getLock("redisson:rlock");
        try {
            //加锁
            rlock.lock(); //默认锁的过期时间: 10s
            //可以根据任务逻辑自行指定超时时间，指定之后就不会自动续期
            rlock.lock(20, TimeUnit.SECONDS);
            //执行业务逻辑: 10s中没执行完（自动给锁续期：watch dog）
            System.out.println("开始执行任务...");
            Thread.sleep(1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //判断是不是自己添加锁
            if (rlock.isHeldByCurrentThread()) {
                System.out.println("释放锁...");
                rlock.unlock(); //释放代码
            }
        }
    }

}
