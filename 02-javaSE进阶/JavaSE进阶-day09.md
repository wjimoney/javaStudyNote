## JavaSE进阶-day09

### 1.File类

#### 1.1 概述

```java
概念:   
	在Java中用来表示"文件或文件夹"的类.
    File封装的文件或文件夹,也可以是不存在的路径
构造:
	public File(String pathname) 			//根据"路径名字符串"创建File
    public File(String parent, String child)//根据"父路径名字符串"和"子路径名字符串"创建File
	public File(File parent, String child) 	//根据"父路径名File"和"子路径名字符串"创建File      
```

#### 1.2 常用方法

```java
创建功能
    public boolean createNewFile();//创建空文件
    public boolean mkdir();		//创建单级文件夹   
    public boolean mkdirs();	//创建多级文件夹
删除功能
	public boolean delete() 	//删除当前路径代表的文件或文件夹.可以理解为是自杀. 注意,删除文件夹时,只能删除空文件夹.  
判断功能
    public boolean isDirectory()//判断是否为文件夹。如果是则返回true, 如果不是或不存在,则返回false  
	public boolean isFile() 	//判断是否为普通文件。如果是则返回true, 如果不是或不存在,则返回false    
    public boolean exists() 	//判断是否存在。  
获取功能
    public String getAbsolutePath()    //获取绝对路径
    public String getParent()    //获取父级路径
    public String getName()     //获取文件或文件夹的名字
高级获取
    public File[] listFiles()    //获取当前文件夹中的所有文件和文件夹
    public static File[] listRoots()    //获取硬盘上的所有盘符
```

#### 3.3 路径问题

```properties
相关概念:
	绝对路径: 永远不发生变化的路径. 绝对路径一般是由盘符作为开始. 例如: D://aaaa//b.txt
	相对路径: 根据参照物对比出来的路径. 相对路径不是以盘符作为开始.  a.txt
注意事项:
	1.Java项目中的相对路径参照物都是"当前项目".
	2.Java项目任何书写路径的地方,都可以书写"绝对路径",也可以书写"相对路径".
	3.相对路径和绝对路径的概念,仅限于当前Java项目.(在JavaWeb阶段,参照物会发生变化)
```



### 2.IO流

#### 2.1 概述

```properties
概述: I: Input输入. O: Output输出. 所以IO流又叫做"输入输出流". 
分类:
	根据流向分:
		输入流: 指的是"把硬盘上的数据读取到内存中".
		输出流: 指的是"把内存中的数据写出到硬盘上".
	根据单位分:
		字节流: 每次读写操作的都是"字节".字节流能操作所有类型的文件.
		字符流: 每次读写操作的都是"字符".字符流只能操作文本类文件(用记事本打开能正常阅读的文件).
```



#### 2.2 基本字节流

##### ① 字节输出流

语法

```java
概述: 所谓的字节输出流,就是以字节的形式,把数据写道硬盘(文件)上
构造: 
	public FileOutputStream(String file);  //指定要书写的位置,如果文件不存在就创建,如果文件存在就覆盖.
	public FileOutputStream(String file,boolean append);  //append表示是否追加. true就是追加(不覆盖源文件)
方法:
	public void write(int by);  	//写出一个字节
	public void write(byte[] bys);	//写出一个字节数组(把数组中的所有字节都写出去)
	public void write(byte[] bys,int off,int len); //写出字节数组的一部分(从off索引处开始,写len个)
	public void close();	//关闭流(释放资源)
```

案例

```java
//字节输出流 -> 把数据写道文件上
public class TestDemo {
    public static void main(String[] args) throws IOException {
        //1.写哪?
        FileOutputStream fos = new FileOutputStream("MyTest\\a.txt");
        //2.写啥?
        fos.write(97);
        //3.写完了
        fos.close();
    }
}
```



##### ② 字节输入流

语法

```java
概述: 所谓的字节输入流,就是以字节的形式,把硬盘(文件)上的数据,读取出来
构造: 
	public FileInputStream(String file);  //指定要读取的位置,如果文件不存在就报错
方法:
	public int read();	//读取一个字节,返回字节对应的编码. 如果读取不到,则返回-1
	public int read(byte[] bys); //读取一个字节数组. 返回读取到的字节的个数. 如果读取不到,则返回-1
	public void close();	//关闭流(释放资源)
```

案例

```java
public class TestDemo {
    public static void main(String[] args) throws IOException {
        //1.读哪?
        FileInputStream fis = new FileInputStream("MyTest\\a.txt");
        //2.读啥?
        int read = fis.read();
        System.out.println(read);
        //3.读完了
        fis.close();
    }
}
```



#### 2.3 文件拷贝

##### ① 单字节拷贝

```java
//1.定义字节输入流,指定要拷贝的文件
FileInputStream fis = new FileInputStream("MyTest\\6B.jpeg");
//2.定义字节输出流,指定要拷贝到的位置(也是一个文件)
FileOutputStream fos = new FileOutputStream("MyTest\\a.jpeg");
//3.定义变量,用来存储每次拷贝的字节
int by;
//4.输入流读取字节,判断是否读到末尾(读到-1则表示读到末尾)
while((by = fis.read()) != -1){
    //5.输出流写
    fos.write(by);
}
//6.关闭输入流
fis.close();
//7.关闭输出流
fos.close();
```

##### ② 字节数组拷贝

```java
//1.定义字节输入流,指定要拷贝的文件
FileInputStream fis = new FileInputStream("MyTest\\TestDemo.java");
//2.定义字节输出流,指定要拷贝到的位置(也是一个文件)
FileOutputStream fos = new FileOutputStream("MyTest\\Demo.java");
//3.定义数组,用来存储每次拷贝的字节
byte[] bys = new byte[1024 * 8];
//4.定义变量,用来存储每次拷贝到的字节的个数
int len;
//5.输入流读取多个字节,存入数组中,判断是否读到末尾(读到-1则表示读到末尾)
while((len = fis.read(bys)) != -1){
    //6.输出流写
    fos.write(bys,0,len);
}
//7.关闭输入流
fis.close();
//8.关闭输出流
fos.close();
```





### 3.try-catch-finally

#### 3.1 语法格式

```java
try {
    //可能出现异常的代码
    //如果不出现异常,则正常执行
    //如果出现异常,则立即跳转到catch中执行,try中后续代码不再执行.
} catch(异常类型 变量) {
    //如果try中出现异常,则执行catch中代码
    //如果try中不出现异常,则不执行catch中的代码
} finally {
    //不论try中是否出现异常,最终都要执行finally中的代码
    //finally一般用来"释放资源".
}
```

#### 3.2 IO流标准写法(了解)

```java
//1.创建输出流对象,并给予初始化值
FileOutputStream fos = null;
try {
    //2.指定要操作的文件
    fos = new FileOutputStream("MyTest\\a.txt");
    //3.向文件中书写内容
    fos.write(97);
    fos.write(98);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    //4.判断输出流是否创建成功(如果没有创建成功,则就没有必要关闭了)
    if (fos != null) {
        try {
            //5.释放资源
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```












