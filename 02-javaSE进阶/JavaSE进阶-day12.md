## JavaSE进阶-day12

### 1.Thread方法

```java
public String getName();				//获取线程的名字
public void setName(String name);		//给线程设置名字
public static Thread currentThread();	//获取当前线程(这句代码放在哪,就获取哪的线程)
public static void sleep(long times);	//让当前线程休眠指定毫秒(这句代码放在哪,哪就休眠)
```



### 2.线程安全问题

#### 2.1 概述

```properties
概述: 在多线程环境下,多个线程共同操作同一个数据,从而导致"数据不准确"的现象,我们称之为"线程安全问题".
解决:
	1.同步代码块
	2.同步方法
	3.Look锁.
总结: 所谓的解决,本质上来讲,就是"加锁". 就是"我执行的时候,你别执行,你执行的时候,我不执行".
```

#### 2.2 同步代码块

```java
格式:
	synchronized(锁对象){
		//"需要加锁/需要同步/操作共享数据"的代码
	}
注意:
	1.锁对象可以是任意的引用数据类型对象.
    2.锁对象必须唯一.
    3.锁对象推荐使用"类名.class".  //"类名.class"代表类的字节码文件,每个类只能有一份.
```

#### 2.2 同步方法(了解)

```java
格式:
	public synchronized 返回值类型 方法名(参数..){  方法体... }
	public static synchronized 返回值类型 方法名(参数..){  方法体... }
注意:
	1.同步方法也是加锁,锁住的是"方法中的所有代码"
    2.普通同步方法锁对象默认是"this"
    3.静态同步方法锁对象默认是"类名.class"
```

#### 2.3 Lock锁(了解)

```java
格式:
	private ReentrantLock lo = new ReentrantLock();
	lo.lock();
		//需要加锁的代码
	lo.unlock();
注意:
	1.该方式的锁对象是"ReentrantLock对象lo"
    2."lo.unlock()"表示释放锁.一把都放在finally里边,表示无论如何都要释放锁.
```



### 3.线程补充(了解)

#### 3.1 死锁

```properties
概述: 多线程之间,彼此占用对方的资源,并且不释放,从而造成线程卡死现象,就称之为"死锁".
原因: 多线程中,有多把锁,而且锁交叉嵌套.
解决: 在实际开发中应该避免这种情况(不能让锁交叉嵌套).
```

#### 3.2 生产者消费者模式

```properties
概述: 线程之间彼此叫醒对方,让对方执行,从而出现"多线程之间交叉运行"的现象,称之为"生产者消费者模式",也叫"线程间通讯/等待唤醒机制".
方法:
	锁对象.wait();		//当前线程等待(线程在锁上等待)
	锁对象.notify();	//随机唤醒锁上等待的一个线程
	锁对象.notifyAll();//唤醒锁上所有等待的线程
```

#### 3.3 线程的状态

```properties
NEW:新建
RUNNABLE:可运行状态
BLOCKED:阻塞状态 
WAITING:无限等待状态 
TIMED_WAITING:计时等待 
TERMINATED:终止
```



### 4.线程池(熟悉)

#### 4.1 系统线程池

```java
概述: 线程池,就是存储线程的池子.线程池可以创建和回收线程.
创建:
	ExecutorService es = Executors.newCachedThreadPool(); //创建一个默认线程池.内部最多有int最大值个线程
	ExecutorService es = Executors.newFixedThreadPool(int num); //创建一个线程池.内部线程最大数量为num
使用:
	public Future submit(Runnable task);	//提交任务.把要执行的任务交给线程池,由线程池分配线程执行
    public void shutdown(); 				//关闭线程池
```

#### 4.2 自定义线程池

```java
概述: ThreadPoolExecutor可以用来自定义线程池.
构造: 
	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                              RejectedExecutionHandler handler){...}
参数:
	corePoolSize:	//核心线程数,不能小于0
	maximumPoolSize://最大线程数,不能小于等于0,且必须大于等于核心线程数
    keepAliveTime:	//空闲线程最大存活时间值(值),不能小于0 
    unit:			//空闲线程最大存活时间单位,一般使用TimeUtil中的选项
    workQueue:		//任务队列(阻塞队列),该参数用于确定排队的数量,不能为null. 语法:new ArrayBlockingQueue<>(数量);
    threadFactory:	//创建线程的方式,一般为"Executors.defaultThreadFactory()"默认方式,不能为null
    handler:		//任务的拒绝策略(多余任务的处理方式)
拒绝策略:
	new ThreadPoolExecutor.AbortPolicy();			//丢弃任务并抛出RejectedExecutionException异常。是默认的策略。
	new ThreadPoolExecutor.DiscardPolicy();			//丢弃任务，但是不抛出异常 这是不推荐的做法。
	new ThreadPoolExecutor.DiscardOldestPolicy();	//抛弃队列中等待最久的任务 然后把当前任务加入队列中。
	new ThreadPoolExecutor.CallerRunsPolicy();		//调用任务的run()方法绕过线程池直接执行。
```





