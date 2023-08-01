## JavaSE进阶-day11-详细版

### 1.转换流

#### 1.1 概述

- InputStreamReader：可以指定编码的FileReader

- OutputStreamWriter：可以指定编码的FileWriter


#### 2.2 构造方法

| 方法名                                              | 说明                                         |
| --------------------------------------------------- | -------------------------------------------- |
| InputStreamReader(InputStream in)                   | 使用默认字符编码创建InputStreamReader对象    |
| InputStreamReader(InputStream in,String chatset)    | 使用指定的字符编码创建InputStreamReader对象  |
| OutputStreamWriter(OutputStream out)                | 使用默认字符编码创建OutputStreamWriter对象   |
| OutputStreamWriter(OutputStream out,String charset) | 使用指定的字符编码创建OutputStreamWriter对象 |

#### 2.3 代码演示

```java
public class ConversionStreamDemo {
    public static void main(String[] args) throws IOException {
       //OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("myCharStream\\osw.txt"));
       OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("myCharStream\\osw.txt"),"GBK");
       osw.write("中国");
       osw.close();

       //InputStreamReader isr = new InputStreamReader(new FileInputStream("myCharStream\\osw.txt"));
       InputStreamReader isr = new InputStreamReader(new FileInputStream("myCharStream\\osw.txt"),"GBK");
       //一次读取一个字符数据
       int ch;
       while ((ch=isr.read())!=-1) {
           System.out.print((char)ch);
       }
        isr.close();
    }
}
```





### 2.对象操作流

#### 2.1 对象序列化介绍

- 对象序列化：就是将对象保存到磁盘中，或者在网络中传输对象
- 这种机制就是使用一个字节序列表示一个对象，该字节序列包含：对象的类型、对象的数据和对象中存储的属性等信息
- 字节序列写到文件之后，相当于文件中持久保存了一个对象的信息
- 反之，该字节序列还可以从文件中读取回来，重构对象，对它进行反序列化

#### 2.2 对象序列化流： ObjectOutputStream

- 相关概念

  将Java对象的原始数据类型和图形写入OutputStream。 可以使用ObjectInputStream读取（重构）对象。 可以通过使用流的文件来实现对象的持久存储。 如果流是网络套接字流，则可以在另一个主机上或另一个进程中重构对象 

- 构造方法

  | 方法名                               | 说明                                               |
  | ------------------------------------ | -------------------------------------------------- |
  | ObjectOutputStream(OutputStream out) | 创建一个写入指定的OutputStream的ObjectOutputStream |

- 序列化对象的方法

  | 方法名                       | 说明                               |
  | ---------------------------- | ---------------------------------- |
  | void writeObject(Object obj) | 将指定的对象写入ObjectOutputStream |

- 示例代码

  学生类

  ```java
  public class Student implements Serializable {
      private String name;
      private int age;
  
      public Student() {
      }
  
      public Student(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
      @Override
      public String toString() {
          return "Student{" +
                  "name='" + name + '\'' +
                  ", age=" + age +
                  '}';
      }
  }
  ```

  测试类

  ```java
  public class ObjectOutputStreamDemo {
      public static void main(String[] args) throws IOException {
          //ObjectOutputStream(OutputStream out)：创建一个写入指定的OutputStream的ObjectOutputStream
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("myOtherStream\\oos.txt"));
  
          //创建对象
          Student s = new Student("佟丽娅",30);
  
          //void writeObject(Object obj)：将指定的对象写入ObjectOutputStream
          oos.writeObject(s);
  
          //释放资源
          oos.close();
      }
  }
  ```

- 注意事项

  - 一个对象要想被序列化，该对象所属的类必须必须实现Serializable 接口
  - Serializable是一个标记接口，实现该接口，不需要重写任何方法



#### 2.3 对象反序列化流： ObjectInputStream

- 构造方法

  | 方法名                            | 说明                                           |
  | --------------------------------- | ---------------------------------------------- |
  | ObjectInputStream(InputStream in) | 创建从指定的InputStream读取的ObjectInputStream |

- 反序列化对象的方法

  | 方法名              | 说明                            |
  | ------------------- | ------------------------------- |
  | Object readObject() | 从ObjectInputStream读取一个对象 |

- 示例代码

  ```java
  public class ObjectInputStreamDemo {
      public static void main(String[] args) throws IOException, ClassNotFoundException {
          //ObjectInputStream(InputStream in)：创建从指定的InputStream读取的ObjectInputStream
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream("myOtherStream\\oos.txt"));
  
          //Object readObject()：从ObjectInputStream读取一个对象
          Object obj = ois.readObject();
  
          Student s = (Student) obj;
          System.out.println(s.getName() + "," + s.getAge());
  
          ois.close();
      }
  }
  ```



#### 2.4 serialVersionUID&transient

- serialVersionUID

  - 用对象序列化流序列化了一个对象后，假如我们修改了对象所属的类文件，读取数据会不会出问题呢？
    - 会出问题，会抛出InvalidClassException异常
  - 如果出问题了，如何解决呢？
    - 重新序列化
    - 给对象所属的类加一个serialVersionUID 
      - private static final long serialVersionUID = 42L;

- transient

  - 如果一个对象中的某个成员变量的值不想被序列化，又该如何实现呢？
    - 给该成员变量加transient关键字修饰，该关键字标记的成员变量不参与序列化过程

- 示例代码

  学生类

  ```java
  public class Student implements Serializable {
      private static final long serialVersionUID = 42L;
      private String name;
  //    private int age;
      private transient int age;
  
      public Student() {
      }
  
      public Student(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
  //    @Override
  //    public String toString() {
  //        return "Student{" +
  //                "name='" + name + '\'' +
  //                ", age=" + age +
  //                '}';
  //    }
  }
  ```

  测试类

  ```java
  public class ObjectStreamDemo {
      public static void main(String[] args) throws IOException, ClassNotFoundException {
  //        write();
          read();
      }
  
      //反序列化
      private static void read() throws IOException, ClassNotFoundException {
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream("myOtherStream\\oos.txt"));
          Object obj = ois.readObject();
          Student s = (Student) obj;
          System.out.println(s.getName() + "," + s.getAge());
          ois.close();
      }
  
      //序列化
      private static void write() throws IOException {
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("myOtherStream\\oos.txt"));
          Student s = new Student("佟丽娅", 30);
          oos.writeObject(s);
          oos.close();
      }
  }
  ```



#### 2.5 操作流练习

- 案例需求

  创建多个学生类对象写到文件中,再次读取到内存中

- 实现步骤

  - 创建序列化流对象
  - 创建多个学生对象
  - 将学生对象添加到集合中
  - 将集合对象序列化到文件中
  - 创建反序列化流对象
  - 将文件中的对象数据,读取到内存中

- 代码实现

  学生类

  ```java
  public class Student implements Serializable{
      
      private static final long serialVersionUID = 2L;
  
      private String name;
      private int age;
  
      public Student() {
      }
  
      public Student(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  }
  ```

  测试类

  ```java
  public class Demo03 {
      /**
       *  read():
       *      读取到文件末尾返回值是 -1
       *  readLine():
       *      读取到文件的末尾返回值 null
       *  readObject():
       *      读取到文件的末尾 直接抛出异常
       *  如果要序列化的对象有多个,不建议直接将多个对象序列化到文件中,因为反序列化时容易出异常
       *      建议: 将要序列化的多个对象存储到集合中,然后将集合序列化到文件中
       */
      public static void main(String[] args) throws Exception {
          /*// 序列化
          //1.创建序列化流对象
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("myCode\\oos.txt"));
          ArrayList<Student> arrayList = new ArrayList<>();
          //2.创建多个学生对象
          Student s = new Student("佟丽娅",30);
          Student s01 = new Student("佟丽娅",30);
          //3.将学生对象添加到集合中
          arrayList.add(s);
          arrayList.add(s01);
          //4.将集合对象序列化到文件中
          oos.writeObject(arrayList);
          oos.close();*/
  
          // 反序列化
        	//5.创建反序列化流对象
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream("myCode\\oos.txt"));
        	//6.将文件中的对象数据,读取到内存中
          Object obj = ois.readObject();
          ArrayList<Student> arrayList = (ArrayList<Student>)obj;
          ois.close();
          for (Student s : arrayList) {
              System.out.println(s.getName() + "," + s.getAge());
          }
      }
  }
  ```



### 3.Properties集合

#### 3.1 Properties介绍

- 是一个Map体系的集合类
- Properties可以保存到流中或从流中加载
- 属性列表中的每个键及其对应的值都是一个字符串

#### 3.2 特有方法(作为集合)

- 特有方法

  | 方法名                                         | 说明                                                         |
  | ---------------------------------------------- | ------------------------------------------------------------ |
  | Object   setProperty(String key, String value) | 设置集合的键和值，都是String类型，底层调用   Hashtable方法 put |
  | String   getProperty(String key)               | 使用此属性列表中指定的键搜索属性                             |
  | Set<String>   stringPropertyNames()            | 从该属性列表中返回一个不可修改的键集，其中键及其对应的值是字符串 |

- 示例代码

  ```java
  public class PropertiesDemo02 {
      public static void main(String[] args) {
          //创建集合对象
          Properties prop = new Properties();
  
          //Object setProperty(String key, String value)：设置集合的键和值，都是String类型
          prop.setProperty("itheima001", "佟丽娅");
          prop.setProperty("itheima002", "赵丽颖");
          prop.setProperty("itheima003", "刘诗诗");
  
          //String getProperty(String key)：使用此属性列表中指定的键搜索属性
  //        System.out.println(prop.getProperty("itheima001"));
  //        System.out.println(prop.getProperty("itheima0011"));
  
  //        System.out.println(prop);
  
          //Set<String> stringPropertyNames()：从该属性列表中返回一个不可修改的键集，其中键及其对应的值是字符串
          Set<String> names = prop.stringPropertyNames();
          for (String key : names) {
  //            System.out.println(key);
              String value = prop.getProperty(key);
              System.out.println(key + "," + value);
          }
      }
  }
  ```

#### 3.3 IO流相关方法【重要】

- IO流相关方法

  | 方法名                                       | 说明                                                         |
  | -------------------------------------------- | ------------------------------------------------------------ |
  | void   load(Reader reader)                   | 从输入字符流读取属性列表（键和元素对）                       |
  | void   store(Writer writer, String comments) | 将此属性列表（键和元素对）写入此   Properties表中，以适合使用   load(Reader)方法的格式写入输出字符流 |

- 示例代码

  ```java
  public class PropertiesDemo03 {
      public static void main(String[] args) throws IOException {
          //把集合中的数据保存到文件
  //        myStore();
  
          //把文件中的数据加载到集合
          myLoad();
  
      }
  
      private static void myLoad() throws IOException {
          Properties prop = new Properties();
  
          //void load(Reader reader)：
          FileReader fr = new FileReader("myOtherStream\\fw.txt");
          prop.load(fr);
          fr.close();
  
          System.out.println(prop);
      }
  
      private static void myStore() throws IOException {
          Properties prop = new Properties();
  
          prop.setProperty("itheima001","佟丽娅");
          prop.setProperty("itheima002","赵丽颖");
          prop.setProperty("itheima003","刘诗诗");
  
          //void store(Writer writer, String comments)：
          FileWriter fw = new FileWriter("myOtherStream\\fw.txt");
          prop.store(fw,null);
          fw.close();
      }
  }
  ```

#### 3.4 Properties集合练习

- 案例需求

  在Properties文件中手动写上姓名和年龄,读取到集合中,将该数据封装成学生对象,写到本地文件

- 实现步骤

  - 创建Properties集合,将本地文件中的数据加载到集合中
  - 获取集合中的键值对数据,封装到学生对象中
  - 创建序列化流对象,将学生对象序列化到本地文件中

- 代码实现

  学生类

  ```java
  public class Student implements Serializable {
      private static final long serialVersionUID = 1L;
  
      private String name;
      private int age;
  
      public Student() {
      }
  
      public Student(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
      @Override
      public String toString() {
          return "Student{" +
                  "name='" + name + '\'' +
                  ", age=" + age +
                  '}';
      }
  }
  ```

  测试类

  ```java
  public class Test {
  
      public static void main(String[] args) throws IOException {
        	//1.创建Properties集合,将本地文件中的数据加载到集合中
          Properties prop = new Properties();
          FileReader fr = new FileReader("prop.properties");
          prop.load(fr);
          fr.close();
  		//2.获取集合中的键值对数据,封装到学生对象中
          String name = prop.getProperty("name");
          int age = Integer.parseInt(prop.getProperty("age"));
          Student s = new Student(name,age);
  		//3.创建序列化流对象,将学生对象序列化到本地文件中
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("a.txt"));
          oos.writeObject(s);
          oos.close();
      }
  }
  ```



### 4.多线程概述

#### 4.1 简单了解多线程

是指从软件或者硬件上实现多个线程并发执行的技术。
具有多线程能力的计算机因有硬件支持而能够在同一时间执行多个线程，提升性能。

![01_简单了解多线程](assets/01_简单了解多线程.png)

#### 4.2 并发和并行

+ 并行：在同一时刻，有多个指令在多个CPU上同时执行。

  ![02_并行](assets/02_并行.png)

+ 并发：在同一时刻，有多个指令在单个CPU上交替执行。

  ![03_并发](assets/03_并发.png)

#### 4.3 进程和线程

- 进程：是正在运行的程序

  独立性：进程是一个能独立运行的基本单位，同时也是系统分配资源和调度的独立单位
  动态性：进程的实质是程序的一次执行过程，进程是动态产生，动态消亡的
  并发性：任何进程都可以同其他进程一起并发执行

- 线程：是进程中的单个顺序控制流，是一条执行路径

  ​	单线程：一个进程如果只有一条执行路径，则称为单线程程序

  ​	多线程：一个进程如果有多条执行路径，则称为多线程程序

  ​	![04_多线程示例](assets/04_多线程示例.png)



### 5.实现多线程

#### 5.1 实现多线程方式一：继承Thread类

- 方法介绍

  | 方法名       | 说明                                        |
  | ------------ | ------------------------------------------- |
  | void run()   | 在线程开启后，此方法将被调用执行            |
  | void start() | 使此线程开始执行，Java虚拟机会调用run方法() |

- 实现步骤

  - 定义一个类MyThread继承Thread类
  - 在MyThread类中重写run()方法
  - 创建MyThread类的对象
  - 启动线程

- 代码演示

  ```java
  public class MyThread extends Thread {
      @Override
      public void run() {
          for(int i=0; i<100; i++) {
              System.out.println(i);
          }
      }
  }
  public class MyThreadDemo {
      public static void main(String[] args) {
          MyThread my1 = new MyThread();
          MyThread my2 = new MyThread();
  
  //        my1.run();
  //        my2.run();
  
          //void start() 导致此线程开始执行; Java虚拟机调用此线程的run方法
          my1.start();
          my2.start();
      }
  }
  ```

- 两个小问题

  - 为什么要重写run()方法？

    因为run()是用来封装被线程执行的代码

  - run()方法和start()方法的区别？

    run()：封装线程执行的代码，直接调用，相当于普通方法的调用

    start()：启动线程；然后由JVM调用此线程的run()方法



#### 5.2 实现多线程方式二：实现Runnable接口

- Thread构造方法

  | 方法名                               | 说明                   |
  | ------------------------------------ | ---------------------- |
  | Thread(Runnable target)              | 分配一个新的Thread对象 |
  | Thread(Runnable target, String name) | 分配一个新的Thread对象 |

- 实现步骤

  - 定义一个类MyRunnable实现Runnable接口
  - 在MyRunnable类中重写run()方法
  - 创建MyRunnable类的对象
  - 创建Thread类的对象，把MyRunnable对象作为构造方法的参数
  - 启动线程

- 代码演示

  ```java
  public class MyRunnable implements Runnable {
      @Override
      public void run() {
          for(int i=0; i<100; i++) {
              System.out.println(Thread.currentThread().getName()+":"+i);
          }
      }
  }
  public class MyRunnableDemo {
      public static void main(String[] args) {
          //创建MyRunnable类的对象
          MyRunnable my = new MyRunnable();
  
          //创建Thread类的对象，把MyRunnable对象作为构造方法的参数
          //Thread(Runnable target)
  //        Thread t1 = new Thread(my);
  //        Thread t2 = new Thread(my);
          //Thread(Runnable target, String name)
          Thread t1 = new Thread(my,"坦克");
          Thread t2 = new Thread(my,"飞机");
  
          //启动线程
          t1.start();
          t2.start();
      }
  }
  ```



#### 5.3 实现多线程方式三: 实现Callable接口【了解】

+ 方法介绍

  | 方法名                           | 说明                                               |
  | -------------------------------- | -------------------------------------------------- |
  | V call()                         | 计算结果，如果无法计算结果，则抛出一个异常         |
  | FutureTask(Callable<V> callable) | 创建一个 FutureTask，一旦运行就执行给定的 Callable |
  | V get()                          | 如有必要，等待计算完成，然后获取其结果             |

+ 实现步骤

  + 定义一个类MyCallable实现Callable接口
  + 在MyCallable类中重写call()方法
  + 创建MyCallable类的对象
  + 创建Future的实现类FutureTask对象，把MyCallable对象作为构造方法的参数
  + 创建Thread类的对象，把FutureTask对象作为构造方法的参数
  + 启动线程
  + 再调用get方法，就可以获取线程结束之后的结果。

+ 代码演示

  ```java
  public class MyCallable implements Callable<String> {
      @Override
      public String call() throws Exception {
          for (int i = 0; i < 100; i++) {
              System.out.println("跟女孩表白" + i);
          }
          //返回值就表示线程运行完毕之后的结果
          return "答应";
      }
  }
  public class Demo {
      public static void main(String[] args) throws ExecutionException, InterruptedException {
          //线程开启之后需要执行里面的call方法
          MyCallable mc = new MyCallable();
  
          //Thread t1 = new Thread(mc);
  
          //可以获取线程执行完毕之后的结果.也可以作为参数传递给Thread对象
          FutureTask<String> ft = new FutureTask<>(mc);
  
          //创建线程对象
          Thread t1 = new Thread(ft);
  
          String s = ft.get();
          //开启线程
          t1.start();
  
          //String s = ft.get();
          System.out.println(s);
      }
  }
  ```

+ 三种实现方式的对比

  + 实现Runnable、Callable接口
    + 好处: 扩展性强，实现该接口的同时还可以继承其他的类
    + 缺点: 编程相对复杂，不能直接使用Thread类中的方法
  + 继承Thread类
    + 好处: 编程比较简单，可以直接使用Thread类中的方法
    + 缺点: 可以扩展性较差，不能再继承其他的类
