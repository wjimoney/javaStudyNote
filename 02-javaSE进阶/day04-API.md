## 1.API

### 1.1 API概述【理解】

- 什么是API

  ​	API (Application Programming Interface) ：应用程序编程接口

- java中的API

  ​	指的就是 JDK 中提供的各种功能的 Java类，这些类将底层的实现封装了起来，我们不需要关心这些类是如何实现的，只需要学习这些类如何使用即可，我们可以通过帮助文档来学习这些API如何使用。

### 1.2 如何使用API帮助文档【应用】

- 打开帮助文档

![01](img/01.png)

- 找到索引选项卡中的输入框

![02](img/02.png)

- 在输入框中输入Random

![03](img/03.png)

- 看类在哪个包下

![04](img/04.png)

- 看类的描述

![05](img/05.png)

- 看构造方法

![06](img/06.png)

- 看成员方法

![07](img/07.png)

## 2.常用API

### 2.1 Math（应用）

- 1、Math类概述

  - Math 包含执行基本数字运算的方法

- 2、Math中方法的调用方式

  - Math类中无构造方法，但内部的方法都是静态的，则可以通过   **类名.进行调用**

- 3、Math类的常用方法

  | 方法名    方法名                               | 说明                                           |
  | ---------------------------------------------- | ---------------------------------------------- |
  | public static int   abs(int a)                 | 返回参数的绝对值                               |
  | public static double ceil(double a)            | 返回大于或等于参数的最小double值，等于一个整数 |
  | public static double floor(double a)           | 返回小于或等于参数的最大double值，等于一个整数 |
  | public   static int round(float a)             | 按照四舍五入返回最接近参数的int                |
  | public static int   max(int a,int b)           | 返回两个int值中的较大值                        |
  | public   static int min(int a,int b)           | 返回两个int值中的较小值                        |
  | public   static double pow (double a,double b) | 返回a的b次幂的值                               |
  | public   static double random()                | 返回值为double的正值，[0.0,1.0)                |

### 2.2 System（应用）

- System类的常用方法 

  | 方法名                                   | 说明                                             |
  | ---------------------------------------- | ------------------------------------------------ |
  | public   static void exit(int status)    | 终止当前运行的   Java   虚拟机，非零表示异常终止 |
  | public   static long currentTimeMillis() | 返回当前时间(以毫秒为单位)                       |

- 示例代码

  - 需求：在控制台输出1-10000，计算这段代码执行了多少毫秒 

  ```java
  public class SystemDemo {
      public static void main(String[] args) {
          // 获取开始的时间节点
          long start = System.currentTimeMillis();
          for (int i = 1; i <= 10000; i++) {
              System.out.println(i);
          }
          // 获取代码运行结束后的时间节点
          long end = System.currentTimeMillis();
          System.out.println("共耗时：" + (end - start) + "毫秒");
      }
  }
  ```

### 2.3 Object类的toString方法（应用）

- Object类概述

  - Object 是类层次结构的根，每个类都可以将 Object 作为超类。所有类都直接或者间接的继承自该类，换句话说，该类所具备的方法，所有类都会有一份

- 查看方法源码的方式

  - 选中方法，按下Ctrl + B

- 重写toString方法的方式

  - 1. Alt + Insert 选择toString
  - 1. 在类的空白区域，右键 -> Generate -> 选择toString

- toString方法的作用：

  - 以良好的格式，更方便的展示对象中的属性值

- 示例代码：

  ```java
  class Student extends Object {
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
  public class ObjectDemo {
      public static void main(String[] args) {
          Student s = new Student();
          s.setName("林青霞");
          s.setAge(30);
          System.out.println(s); 
          System.out.println(s.toString()); 
      }
  }
  ```

- 运行结果：

  ```java
  Student{name='林青霞', age=30}
  Student{name='林青霞', age=30}
  ```

### 2.4 Object类的equals方法（应用）

- equals方法的作用

  - 用于对象之间的比较，返回true和false的结果
  - 举例：s1.equals(s2);    s1和s2是两个对象

- 重写equals方法的场景

  - 不希望比较对象的地址值，想要结合对象属性进行比较的时候。

- 重写equals方法的方式

  - 1. alt + insert  选择equals() and hashCode()，IntelliJ Default，一路next，finish即可
  - 1. 在类的空白区域，右键 -> Generate -> 选择equals() and hashCode()，后面的同上。

- 示例代码：

  ```java
  class Student {
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
      public boolean equals(Object o) {
          //this -- s1
          //o -- s2
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
  
          Student student = (Student) o; //student -- s2
  
          if (age != student.age) return false;
          return name != null ? name.equals(student.name) : student.name == null;
      }
  }
  public class ObjectDemo {
      public static void main(String[] args) {
          Student s1 = new Student();
          s1.setName("林青霞");
          s1.setAge(30);
  
          Student s2 = new Student();
          s2.setName("林青霞");
          s2.setAge(30);
  
          //需求：比较两个对象的内容是否相同
          System.out.println(s1.equals(s2));
      }
  }
  
  ```

- 面试题

  ```java
  // 看程序,分析结果
  String s = “abc”;
  StringBuilder sb = new StringBuilder(“abc”);
  s.equals(sb); 
  sb.equals(s); 
  
  public class InterviewTest {
      public static void main(String[] args) {
          String s1 = "abc";
          StringBuilder sb = new StringBuilder("abc");
          //1.此时调用的是String类中的equals方法.
          //保证参数也是字符串,否则不会比较属性值而直接返回false
          //System.out.println(s1.equals(sb)); // false
  
          //StringBuilder类中是没有重写equals方法,用的就是Object类中的.
          System.out.println(sb.equals(s1)); // false
      }
  }
  ```

### 2.5 Objects (应用)

- 常用方法

  | 方法名                                          | 说明                             |
  | ----------------------------------------------- | -------------------------------- |
  | public static String toString(对象)             | 返回参数中对象的字符串表示形式。 |
  | public static String toString(对象, 默认字符串) | 返回对象的字符串表示形式。       |
  | public static Boolean isNull(对象)              | 判断对象是否为空                 |
  | public static Boolean nonNull(对象)             | 判断对象是否不为空               |

- 示例代码

  学生类

  ```java
  class Student {
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
  public class MyObjectsDemo {
            public static void main(String[] args) {
        //        public static String toString(对象): 返回参数中对象的字符串表示形式。
        //        Student s = new Student("小罗同学",50);
        //        String result = Objects.toString(s);
        //        System.out.println(result);
        //        System.out.println(s);
  
        //        public static String toString(对象, 默认字符串): 返回对象的字符串表示形式。如果对象为空,那么返回第二个参数.
                //Student s = new Student("小花同学",23);
        //        Student s = null;
        //        String result = Objects.toString(s, "随便写一个");
        //        System.out.println(result);
        
        //        public static Boolean isNull(对象): 判断对象是否为空
                //Student s = null;
        //        Student s = new Student();
        //        boolean result = Objects.isNull(s);
        //        System.out.println(result);
  
        //        public static Boolean nonNull(对象): 判断对象是否不为空
                //Student s = new Student();
                Student s = null;
                boolean result = Objects.nonNull(s);
                System.out.println(result);
            }
    }
  ```

### 2.6 BigDecimal (应用)

- 作用

  可以用来进行精确计算

- 构造方法

  | 方法名                 | 说明         |
  | ---------------------- | ------------ |
  | BigDecimal(double val) | 参数为double |
  | BigDecimal(String val) | 参数为String |

- 常用方法

  | 方法名                                                       | 说明 |
  | ------------------------------------------------------------ | ---- |
  | public BigDecimal add(另一个BigDecimal对象)                  | 加法 |
  | public BigDecimal subtract (另一个BigDecimal对象)            | 减法 |
  | public BigDecimal multiply (另一个BigDecimal对象)            | 乘法 |
  | public BigDecimal divide (另一个BigDecimal对象)              | 除法 |
  | public BigDecimal divide (另一个BigDecimal对象，精确几位，舍入模式) | 除法 |

- 总结

  1. BigDecimal是用来进行精确计算的
  2. 创建BigDecimal的对象，构造方法使用参数类型为字符串的。
  3. 四则运算中的除法，如果除不尽请使用divide的三个参数的方法。

  代码示例：

  ```java
  BigDecimal divide = bd1.divide(参与运算的对象,小数点后精确到多少位,舍入模式);
  参数1 ，表示参与运算的BigDecimal 对象。
  参数2 ，表示小数点后面精确到多少位
  参数3 ，舍入模式  
    BigDecimal.ROUND_UP  进一法
    BigDecimal.ROUND_FLOOR 去尾法
    BigDecimal.ROUND_HALF_UP 四舍五入
  ```



