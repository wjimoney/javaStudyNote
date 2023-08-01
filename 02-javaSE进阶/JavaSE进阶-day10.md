## JavaSE进阶-day10

### 1.字节缓冲流

#### 1.1 字节缓冲输入流

```java
概述: 字节缓冲输入流 = 字节输入流 + 8K数组(缓冲区).
构造:
	public BufferedInputStream(InputStream is); //根据一个字节流,生成一个字节缓冲流
	public BufferedInputStream(InputStream is, int size); //size指定缓冲区的大小
方法:	
	public int read() 	//从该输入流读取一个字节的数据。  
    public int read(byte[] b) //从该输入流读取最多"b.length"个字节的数据到一个字节数组。  
    public void close() //关闭此文件输入流并释放与流相关联的任何系统资源。  
```

#### 1.2 字节缓冲输出流

```java
概述: 字节缓冲输出流 = 字节输出流 + 8K数组(缓冲区).
构造:
    public BufferedOutputStream(OutputStream os); //根据一个字节流,生成一个字节缓冲流
	public BufferedOutputStream(OutputStream os, int size); //size指定缓冲区的大小
方法:	
	public void write(int b) //将指定的字节写入此文件输出流。
	public void write(byte[] b, int off, int len) //将b数组中,从off位置开始的"len个字节"写入此文件输出流。
	public void close() //关闭流
```



### 2.编码相关(了解)

#### 2.1 编码表

```properties
概述: 所谓的编码表就是"字母和数字之间的对应关系形成的表格".
常见:
	ASCII: 该编码表是一切编码表的基础.该编码表只包含"英文大小写字母,数字和英文标签符号".
	GBK: 中国自己制作的编码表,包含了中文.该编码表中,一个汉字占用2个字节.
	UTF-8: 国际码,包含了中文. 该编码表中,一个汉字占用3个字节.
	ISO-8859-1: 欧洲国家使用的编码表.
```

#### 2.2 编解码

```java
编码:
	byte[] bys = 字符串.getBytes();  //按照默认编码方式,把字符串转换为字节数组.
	byte[] bys = 字符串.getBytes(String charsetName);  //按照指定编码方式,把字符串转换为字节数组.
解码:
	String str = new String(字节数组);	//按照默认编码方式,把字节数组转换为字符串.
	String str = new String(字节数组,编码名称);	//按照指定编码方式,把字节数组转换为字符串.
```



### 3.普通字符流(了解)

#### 3.1 字符输出流

```java
构造:
	public FileWriter("文件路径"); //指定文件输出位置(如果文件不存在则创建,如果文件存在则覆盖)
	public FileWriter("文件路径",是否追加); //指定文件输出位置(如果第二个参数是true,则表示不覆盖,而是追加续写)
方法:
	public void write(int b);  		//写出一个字符
	public void write(char[] chs);  //把字符数组中的所有数据都写出.
	public void write(char[] chs,int off,int len);  //把字符数组中,从off位置开始写,写len个数据.
	public void write(String s);  	//写出一个字符串
	public void write(String s,int off,int len);  	//把字符中,从off位置开始写,写len个数据.
	public void flush();	//强制把缓冲区中的数据给写出去
	public void close();	//关闭IO流,释放资源(关闭之前会自动flush)
```

#### 3.2 字符输入流

```java
构造:
	public FileReader("文件路径");	//指定要读取的文件位置
方法:
	public int read();	//读取一个字符,如果读取不到,则返回-1
	public int read(char[] chs);//读取多个字符,并把读取到的数据放入chs数组中,返回读取到的字符的个数.如果读取不到,则返回-1
	public void close();//关闭IO流,释放资源
```



### 4.字符缓冲流(常用)

#### 4.1 字符缓冲输出流

```java
构造:
	public BufferedWriter(Writer w); 
方法:
	public void write(String str);//写出一个字符串
	public void newLine(); 	//输出一个换行(该换行可以根据操作系统自动切换不同的换行).
	public void close();	//关闭IO流,释放资源(关闭之前会自动flush)
```

#### 4.2 字符缓冲输入流

注意事项:每次输出流写完必须 bufferedWriter.flush();

不然可能会报错

```java
构造:
	public BufferedReader(Reader r);	
方法:
	public String readLine();	//一次读取一行.如果读取不到,返回null
	public void close();		//关闭IO流,释放资源

案例
     BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\Users\\Lenovo\\Desktop\\Java\\workspace\\IDEAProject\\Basic-test\\abc.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\Users\\Lenovo\\Desktop\\Java\\workspace\\IDEAProject\\Basic-test\\c.txt"));
        String a;
        while ((a=bufferedReader.readLine())!=null){
            System.out.println(a);
           bufferedWriter.write(a);
           bufferedWriter.flush();
        }
```
