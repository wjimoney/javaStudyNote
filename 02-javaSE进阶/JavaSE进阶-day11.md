## JavaSE进阶-day11

### 1.转换流

#### 1.1 概述

```java
概述:
	转换流就是可以指定编码的字符流.
作用:
	1.进行读写操作时,可以指定编码.
	2.可以把"字节流"转换为"字符流".
```

#### 1.2 语法

##### ① 转换输入流

```java
概述: InputStreamReader就是可以指定编码的FileReader. JDK11之后FileReader也可以指定编码.
构造:
	public InputStreamReader(字节输入流);	//使用"默认编码表"读取文件(其实就是FileReader)
	public InputStreamReader(字节输入流,"编码表名称");	//使用"指定编码表"读取文件
```

##### ② 转换输出流

```java
概述: OutputStreamWriter就是可以指定编码的FileWriter. JDK11之后FileWriter也可以指定编码.
构造:
	public OutputStreamWriter(OutputStream out);//使用"默认编码表"写出文件(其实就是FileWriter)
	public InputStreamReader(OutputStream out,"编码表名称");	//使用"指定编码表"写出文件
```



### 2. 对象流

#### 2.1 语法

##### ① 对象输出流 -> 序列化流

```java
概述: 把一个"对象"直接写到文件上,这种行为也称之为"序列化".
构造:	public ObjectOutputStream(OutputStream os);
方法:	public void writeObject(对象);
```

##### ② 对象输入流 ->反序列化流

```java
概述: 从文件上,直接读取出来一个"对象",这种行为也称之为"反序列化".
构造: public ObjectInputStream(InputStream in);
方法:	public Object readObject();
```

#### 2.2 核心

##### ① 注意事项(掌握)

```java
1.如果想把一个对象写到文件上,则该对象所在的类必须实现Serializable接口
2.该类需要提供一个序列化版本号,防止在使用过程中类被升级或修改,从而导致的读取失败问题.
	private static final long serialVersionUID = 1L;
```

##### ② Serializable接口

```properties
概念:	Serializable接口是一个"标记接口"
特点:
	1.标记接口没有抽象方法,不需要重写.
	2.标记接口仅作为一个标记,不具有任何实际意义(例如:一个类实现了Serializable,仅表示该类可以被序列化到文件上).
```



### 3.Properties类

```java
概述: Properties是Map集合的一个子类.Properties的键是String,值也是String.
构造:
	public Properties();
方法:
	public void setProperty(键,值);
	public 值 getProperty(键);
	public void load(Reader r); //把字符流所指向的"配置文件"中的内容,读取到Properties集合中
	public void store(Writer w,String comments); //把Properties集合中的内容,写到输出流指向的文件中
```







### 4.多线程

#### 4.1 相关概念(了解)

```properties
基本概念:
    并发: 多个目标交替执行.
    并行: 多个目标同时执行.
    进程: 正在运行的程序.
    线程: 程序的运行路线.
注意事项:
	计算机中的程序,天然是"并发执行".每个线程必须是完全独立.
```



#### 4.2 实现多线程-继承Thread类(掌握)

##### ① 步骤

```properties
1:定义一个类,继承Thread类
2:重写run方法,把需要执行的代码写入run方法中.
3:创建自定义类的对象.
4:调用自定义对象的start方法,启动线程
```

##### ② 代码

```java
//1:定义一个类,继承Thread类
public class MyThread extends Thread{
    @Override
    public void run() {
        //2:重写run方法,把需要执行的代码写入run方法中.
    }
}

//测试类
public class TestDemo {
    public static void main(String[] args) {
        //3:创建自定义类的对象.
        MyThread mt = new MyThread();
        //4:调用自定义对象的start方法,启动线程
        mt.start();
    }
}
```



#### 4.3 实现多线程-实现Runnable接口(掌握)	

##### ① 步骤

```properties
1:自定义一个类,该类实现Runnable接口
2:重写run方法,把需要执行的代码写入run方法中.
3:创建自定义类的对象
4:创建Thread类的对象,把"自定义类的对象"作为构造方法的参数
5:调用Thread类的对象start方法,启动线程
```

##### ② 代码

```java
//1:自定义一个类,该类实现Runnable接口
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        //2:重写run方法,把需要执行的代码写入run方法中.
    }
}

//测试类
public class TestDemo {
    public static void main(String[] args) {
        //3:创建自定义类的对象.
        MyRunnable mr = new MyRunnable();
        //4:创建Thread类的对象,把"自定义类的对象"作为构造方法的参数
        Thread t = new Thread(mr);
        //5:调用Thread类的对象start方法,启动线程
        t.start();
    }
}
```



#### 4.4 实现多线程-实现Callable接口(了解)

##### ① 步骤

```properties
1:自定义一个类,该类实现Callable接口
2:重写call方法,把需要执行的代码写入call方法中.
3:创建自定义类的对象
4:创建FutureTask类的对象,把"自定义类的对象"作为构造方法的参数
5:创建Thread类的对象,把"FutureTask类的对象"作为构造方法的参数
6:调用Thread类的对象start方法,启动线程
7:再调"FutureTask类的对象"的get方法,获取线程结束之后的结果。
```

##### ② 代码

```java
//1:自定义一个类,该类实现Callable接口,Callable接口的泛型,表示的是"该线程执行之后的结果的数据类型"
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
       //2:重写call方法,把需要执行的代码写入call方法中.
    }
}

//测试类
public class TestDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //3:创建自定义类的对象
        MyCallable mc = new MyCallable();
        //4:创建FutureTask类的对象,把"自定义类的对象"作为构造方法的参数
        FutureTask<String> ft = new FutureTask<>(mc);
        //5:创建Thread类的对象,把"FutureTask类的对象"作为构造方法的参数
        Thread t = new Thread(ft);
        //6:调用Thread类的对象start方法,启动线程
        t.start();
        //7:再调"FutureTask类的对象"的get方法,获取线程结束之后的结果。
        String s = ft.get();
        System.out.println(s);
    }
}
```



