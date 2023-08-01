## JavaSE进阶-day10-详细版

### 1.字节缓冲流

#### 1.1 字节缓冲流概述

- 字节缓冲流介绍

  - BufferOutputStream = FileOutputStream + 8K数组
  - BufferedInputStream = FileInputStream + 8K数组

- 构造方法：

  | 方法名                                 | 说明                   |
  | -------------------------------------- | ---------------------- |
  | BufferedOutputStream(OutputStream out) | 创建字节缓冲输出流对象 |
  | BufferedInputStream(InputStream in)    | 创建字节缓冲输入流对象 |

- 示例代码

  ```java
  public class BufferStreamDemo {
      public static void main(String[] args) throws IOException {
          //字节缓冲输出流：BufferedOutputStream(OutputStream out)
   
          BufferedOutputStream bos = new BufferedOutputStream(new 				                                       FileOutputStream("myByteStream\\bos.txt"));
          //写数据
          bos.write("hello\r\n".getBytes());
          bos.write("world\r\n".getBytes());
          //释放资源
          bos.close();
      
  
          //字节缓冲输入流：BufferedInputStream(InputStream in)
          BufferedInputStream bis = new BufferedInputStream(new                                                          FileInputStream("myByteStream\\bos.txt"));
  
          //一次读取一个字节数据
  //        int by;
  //        while ((by=bis.read())!=-1) {
  //            System.out.print((char)by);
  //        }
  
          //一次读取一个字节数组数据
          byte[] bys = new byte[1024];
          int len;
          while ((len=bis.read(bys))!=-1) {
              System.out.print(new String(bys,0,len));
          }
  
          //释放资源
          bis.close();
      }
  }
  ```

#### 2.2 缓冲流复制

- 案例需求

  把“E:\\itcast\\字节流复制图片.avi”复制到模块目录下的“字节流复制图片.avi”

- 实现步骤

  - 根据数据源创建字节输入流对象
  - 根据目的地创建字节输出流对象
  - 读写数据，复制视频
  - 释放资源

- 代码实现

  ```java
  public class CopyAviDemo {
      public static void main(String[] args) throws IOException {
  
          //复制视频
  //        method1();
        	 method2();
  
      }
  
      //字节缓冲流一次读写一个字节数组
      public static void method2() throws IOException {
          BufferedInputStream bis = new BufferedInputStream(new FileInputStream("E:\\itcast\\字节流复制图片.avi"));
          BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("myByteStream\\字节流复制图片.avi"));
  
          byte[] bys = new byte[1024];
          int len;
          while ((len=bis.read(bys))!=-1) {
              bos.write(bys,0,len);
          }
  
          bos.close();
          bis.close();
      }
  
      //字节缓冲流一次读写一个字节
      public static void method1() throws IOException {
          BufferedInputStream bis = new BufferedInputStream(new FileInputStream("E:\\itcast\\字节流复制图片.avi"));
          BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("myByteStream\\字节流复制图片.avi"));
  
          int by;
          while ((by=bis.read())!=-1) {
              bos.write(by);
          }
  
          bos.close();
          bis.close();
      }
  
  }
  ```



### 2.编码相关

#### 2.1 为什么会出现字符流

- 字符流的介绍

  由于字节流操作中文不是特别的方便，所以Java就提供字符流

  字符流 = 字节流 + 编码表

- 中文的字节存储方式

  用字节流复制文本文件时，文本文件也会有中文，但是没有问题，原因是最终底层操作会自动进行字节拼接成中文，如何识别是中文的呢？

  汉字在存储的时候，无论选择哪种编码存储，第一个字节都是负数

#### 2.2 编码表

- 什么是字符集

  是一个系统支持的所有字符的集合，包括各国家文字、标点符号、图形符号、数字等

  l计算机要准确的存储和识别各种字符集符号，就需要进行字符编码，一套字符集必然至少有一套字符编码。常见字符集有ASCII字符集、GBXXX字符集、Unicode字符集等

- 常见的字符集

  - ASCII字符集：

    lASCII：是基于拉丁字母的一套电脑编码系统，用于显示现代英语，主要包括控制字符(回车键、退格、换行键等)和可显示字符(英文大小写字符、阿拉伯数字和西文符号) 

    基本的ASCII字符集，使用7位表示一个字符，共128字符。ASCII的扩展字符集使用8位表示一个字符，共256字符，方便支持欧洲常用字符。是一个系统支持的所有字符的集合，包括各国家文字、标点符号、图形符号、数字等

  - GBXXX字符集：

    GBK：最常用的中文码表。是在GB2312标准基础上的扩展规范，使用了双字节编码方案，共收录了21003个汉字，完全兼容GB2312标准，同时支持繁体汉字以及日韩汉字等

  - Unicode字符集：

    UTF-8编码：可以用来表示Unicode标准中任意字符，它是电子邮件、网页及其他存储或传送文字的应用 中，优先采用的编码。互联网工程工作小组（IETF）要求所有互联网协议都必须支持UTF-8编码。它使用一至四个字节为每个字符编码

    编码规则： 

      128个US-ASCII字符，只需一个字节编码

      拉丁文等字符，需要二个字节编码

      大部分常用字（含中文），使用三个字节编码

      其他极少使用的Unicode辅助字符，使用四字节编码

#### 2.3 编码解码

- 相关方法

  | 方法名                                   | 说明                                               |
  | ---------------------------------------- | -------------------------------------------------- |
  | byte[] getBytes()                        | 使用平台的默认字符集将该 String编码为一系列字节    |
  | byte[] getBytes(String charsetName)      | 使用指定的字符集将该 String编码为一系列字节        |
  | String(byte[] bytes)                     | 使用平台的默认字符集解码指定的字节数组来创建字符串 |
  | String(byte[] bytes, String charsetName) | 通过指定的字符集解码指定的字节数组来创建字符串     |

- 代码演示

  ```java
  public class StringDemo {
      public static void main(String[] args) throws UnsupportedEncodingException {
          //定义一个字符串
          String s = "中国";
  
          //byte[] bys = s.getBytes(); //[-28, -72, -83, -27, -101, -67]
          //byte[] bys = s.getBytes("UTF-8"); //[-28, -72, -83, -27, -101, -67]
          byte[] bys = s.getBytes("GBK"); //[-42, -48, -71, -6]
          System.out.println(Arrays.toString(bys));
  
          //String ss = new String(bys);
          //String ss = new String(bys,"UTF-8");
          String ss = new String(bys,"GBK");
          System.out.println(ss);
      }
  }
  ```



### 3.字符流

#### 3.1 字符流输出流

- 介绍

  Writer: 用于写入字符流的抽象父类

  FileWriter: 用于写入字符流的常用子类


- 构造方法

  | 方法名                                      | 说明                                                         |
  | ------------------------------------------- | ------------------------------------------------------------ |
  | FileWriter(File file)                       | 根据给定的 File 对象构造一个 FileWriter 对象                 |
  | FileWriter(File file, boolean append)       | 根据给定的 File 对象构造一个 FileWriter 对象                 |
  | FileWriter(String fileName)                 | 根据给定的文件名构造一个 FileWriter 对象                     |
  | FileWriter(String fileName, boolean append) | 根据给定的文件名以及指示是否附加写入数据的 boolean 值来构造 FileWriter 对象 |


- 成员方法

  | 方法名                                    | 说明                 |
  | ----------------------------------------- | -------------------- |
  | void   write(int c)                       | 写一个字符           |
  | void   write(char[] cbuf)                 | 写入一个字符数组     |
  | void write(char[] cbuf, int off, int len) | 写入字符数组的一部分 |
  | void write(String str)                    | 写一个字符串         |
  | void write(String str, int off, int len)  | 写一个字符串的一部分 |

- 刷新和关闭的方法

  | 方法名  | 说明                                                         |
  | ------- | ------------------------------------------------------------ |
  | flush() | 刷新流，之后还可以继续写数据                                 |
  | close() | 关闭流，释放资源，但是在关闭之前会先刷新流。一旦关闭，就不能再写数据 |

- 代码演示

  ```java
  public class OutputStreamWriterDemo {
      public static void main(String[] args) throws IOException {
          FileWriter fw = new FileWriter("myCharStream\\a.txt");
  
          //void write(int c)：写一个字符
  //        fw.write(97);
  //        fw.write(98);
  //        fw.write(99);
  
          //void writ(char[] cbuf)：写入一个字符数组
          char[] chs = {'a', 'b', 'c', 'd', 'e'};
  //        fw.write(chs);
  
          //void write(char[] cbuf, int off, int len)：写入字符数组的一部分
  //        fw.write(chs, 0, chs.length);
  //        fw.write(chs, 1, 3);
  
          //void write(String str)：写一个字符串
  //        fw.write("abcde");
  
          //void write(String str, int off, int len)：写一个字符串的一部分
  //        fw.write("abcde", 0, "abcde".length());
          fw.write("abcde", 1, 3);
  
          //释放资源
          fw.close();
      }
  }
  ```

#### 4.2 字符流输入流

+ 介绍

  Reader: 用于读取字符流的抽象父类

  FileReader: 用于读取字符流的常用子类


+ 构造方法

  | 方法名                      | 说明                                                    |
  | --------------------------- | ------------------------------------------------------- |
  | FileReader(File file)       | 在给定从中读取数据的 File 的情况下创建一个新 FileReader |
  | FileReader(String fileName) | 在给定从中读取数据的文件名的情况下创建一个新 FileReader |

- 成员方法

  | 方法名                | 说明                   |
  | --------------------- | ---------------------- |
  | int read()            | 一次读一个字符数据     |
  | int read(char[] cbuf) | 一次读一个字符数组数据 |

- 代码演示

  ```java
  public class InputStreamReaderDemo {
      public static void main(String[] args) throws IOException {
     
          FileReader fr = new FileReader("myCharStream\\b.txt");
  
          //int read()：一次读一个字符数据
  //        int ch;
  //        while ((ch=fr.read())!=-1) {
  //            System.out.print((char)ch);
  //        }
  
          //int read(char[] cbuf)：一次读一个字符数组数据
          char[] chs = new char[1024];
          int len;
          while ((len = fr.read(chs)) != -1) {
              System.out.print(new String(chs, 0, len));
          }
  
          //释放资源
          fr.close();
      }
  }
  ```

#### 3.3 用户注册案例

- 案例需求

  将键盘录入的用户名和密码保存到本地实现永久化存储

- 实现步骤

  - 获取用户输入的用户名和密码
  - 将用户输入的用户名和密码写入到本地文件中
  - 关流,释放资源

- 代码实现

  ```java
  public class CharStreamDemo8 {
      public static void main(String[] args) throws IOException {
          //需求: 将键盘录入的用户名和密码保存到本地实现永久化存储
          //要求：用户名独占一行，密码独占一行
  
          //分析：
          //1，实现键盘录入，把用户名和密码录入进来
          Scanner sc = new Scanner(System.in);
          System.out.println("请录入用户名");
          String username = sc.next();
          System.out.println("请录入密码");
          String password = sc.next();
  
          //2.分别把用户名和密码写到本地文件。
          FileWriter fw = new FileWriter("charstream\\a.txt");
          //将用户名和密码写到文件中
          fw.write(username);
          //表示写出一个回车换行符 windows \r\n  MacOS \r  Linux \n
          fw.write("\r\n");
          fw.write(password);
          //刷新流
          fw.flush();
          //3.关流,释放资源
          fw.close();
      }
  }
  ```



### 4.字符缓冲流

#### 4.1 缓冲流概述

- 字符缓冲流介绍

  - BufferedWriter = FileWriter + 缓冲区

  - BufferedReader = FileReader + 缓冲区

- 构造方法

  | 方法名                     | 说明                   |
  | -------------------------- | ---------------------- |
  | BufferedWriter(Writer out) | 创建字符缓冲输出流对象 |
  | BufferedReader(Reader in)  | 创建字符缓冲输入流对象 |

- 代码演示

  ```java
  public class BufferedStreamDemo01 {
      public static void main(String[] args) throws IOException {
          //BufferedWriter(Writer out)
          BufferedWriter bw = new BufferedWriter(new                                                            FileWriter("myCharStream\\bw.txt"));
          bw.write("hello\r\n");
          bw.write("world\r\n");
          bw.close();
  
          //BufferedReader(Reader in)
          BufferedReader br = new BufferedReader(new                                                           FileReader("myCharStream\\bw.txt"));
  
          //一次读取一个字符数据
  //        int ch;
  //        while ((ch=br.read())!=-1) {
  //            System.out.print((char)ch);
  //        }
  
          //一次读取一个字符数组数据
          char[] chs = new char[1024];
          int len;
          while ((len=br.read(chs))!=-1) {
              System.out.print(new String(chs,0,len));
          }
  
          br.close();
      }
  }
  ```

#### 4.2 特有功能【重要】

- 方法介绍

  BufferedWriter：

  | 方法名         | 说明                                         |
  | -------------- | -------------------------------------------- |
  | void newLine() | 写一行行分隔符，行分隔符字符串由系统属性定义 |

  BufferedReader:

  | 方法名            | 说明                                                         |
  | ----------------- | ------------------------------------------------------------ |
  | String readLine() | 读一行文字。 结果包含行的内容的字符串，不包括任何行终止字符如果流的结尾已经到达，则为null |

- 代码演示

  ```java
  public class BufferedStreamDemo02 {
      public static void main(String[] args) throws IOException {
  
          //创建字符缓冲输出流
          BufferedWriter bw = new BufferedWriter(new                                                          FileWriter("myCharStream\\bw.txt"));
  
          //写数据
          for (int i = 0; i < 10; i++) {
              bw.write("hello" + i);
              //bw.write("\r\n");
              bw.newLine();
              bw.flush();
          }
  
          //释放资源
          bw.close();
  
          //创建字符缓冲输入流
          BufferedReader br = new BufferedReader(new                                                          FileReader("myCharStream\\bw.txt"));
  
          String line;
          while ((line=br.readLine())!=null) {
              System.out.println(line);
          }
  
          br.close();
      }
  }
  ```

#### 4.3  数据排序案例

- 案例需求

  使用字符缓冲流读取文件中的数据，排序后再次写到本地文件

- 实现步骤

  - 将文件中的数据读取到程序中
  - 对读取到的数据进行处理
  - 将处理后的数据添加到集合中
  - 对集合中的数据进行排序
  - 将排序后的集合中的数据写入到文件中

- 代码实现

  ```java
  public class CharStreamDemo14 {
      public static void main(String[] args) throws IOException {
          //需求：读取文件中的数据，排序后再次写到本地文件
          //分析：
          //1.要把文件中的数据读取进来。
          BufferedReader br = new BufferedReader(new FileReader("charstream\\sort.txt"));
          //输出流一定不能写在这里，因为会清空文件中的内容
          //BufferedWriter bw = new BufferedWriter(new FileWriter("charstream\\sort.txt"));
  
          String line = br.readLine();
          System.out.println("读取到的数据为" + line);
          br.close();
  
          //2.按照空格进行切割
          String[] split = line.split(" ");//9 1 2 5 3 10 4 6 7 8
          //3.把字符串类型的数组变成int类型
          int [] arr = new int[split.length];
          //遍历split数组，可以进行类型转换。
          for (int i = 0; i < split.length; i++) {
              String smallStr = split[i];
              //类型转换
              int number = Integer.parseInt(smallStr);
              //把转换后的结果存入到arr中
              arr[i] = number;
          }
          //4.排序
          Arrays.sort(arr);
          System.out.println(Arrays.toString(arr));
  
          //5.把排序之后结果写回到本地 1 2 3 4...
          BufferedWriter bw = new BufferedWriter(new FileWriter("charstream\\sort.txt"));
          //写出
          for (int i = 0; i < arr.length; i++) {
              bw.write(arr[i] + " ");
              bw.flush();
          }
          //释放资源
          bw.close();
  
      }
  }
  ```

#### 4.3 流小结【理解】

+ IO流小结

  ![01_IO流小结](assets/01_IO流小结.png)



## 
