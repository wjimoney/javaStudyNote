## JavaSE进阶-day13

### 1.网络编程

#### 1.1 概述

```properties
概念: 
	网络编程指的是通过代码的手段,实现网络间的通讯.
	简单来说,就是电脑与电脑之间收发消息
	网络编程又叫做"Socket编程"
特点:
	网络编程有三大要素: IP地址,端口号,网络传输协议
```



#### 1.2 要素

##### ① IP地址

```properties
概念:网络设备在网络中的唯一标识.
命令:
	ipconfig		查看本机IP地址
	ping IP地址	   测试是否和指定网络联通			
注意:
	127.0.0.1 --> 本机
	*.*.*.255 --> 广播地址
```

##### ② 端口号

```properties
概念:应用程序在设备中的唯一标识.
注意:
	1:取值范围0-65535.
	2:一个端口在同一时间之内只能由一个程序占用
	3:0-1024一般为系统占用的端口号.
	4:推荐个人使用10000以上的端口.
```

##### ③ 网络传输协议	

```properties
概念: 数据在网络中传输的规则.
分类:
    UDP: 1.面向无连接 2.速度快,不安全,容易丢失数据. 3.每次最多发送64K.
    TCP: 1.面向稳定链接 2.速度慢,安全. 3.需要经历三次握手,建立稳定联接
```



#### 1.3 InetAddress

```java
概述: InetAddress就是在java中用来表示IP地址的类. 
方法:
	public static InetAddress getByName("IP地址"); 	//把IP地址字符串转换为InetAddress对象
	public static InetAddress getLocalHost();	//获取本机的IP地址 
	public String getHostAddress();	//获取InetAddress对象中的IP地址
```





### 2.UDP协议-单播/广播

#### 2.1 UDP协议-发送端

##### ① 操作步骤

```properties
1.创建发送端Socket对象(DatagramSocket),因为是发送端的Socket，所以不需要指定端口号
2.准备数据,并且打包。DatagramPacket
3.发送数据
4.释放资源
```

##### ② 案例代码

```java
//1.创建发送端Socket对象(DatagramSocket)。因为是发送端的Socket，所以不需要指定端口号
DatagramSocket ds = new DatagramSocket();
//2.准备数据,并且打包。DatagramPacket
//准备数据
byte[] bys = "hello udp,im comming!".getBytes();
//指定接收方(收件人)的ip地址
InetAddress address = InetAddress.getByName("127.0.0.1");
//端口号
int port = 8888;
DatagramPacket dp = new DatagramPacket(bys,bys.length,address,port);
//3.发送数据
ds.send(dp);
//4.释放资源
ds.close();
```

##### ③ 注意事项

```properties
如果要发送的目标的IP地址是"广播地址", 则发送的就是广播
```



#### 2.2 UDP协议-接收端

##### ① 操作步骤

```properties
1.创建接收端的Socket对象(DatagramSocket)。因为是接收端，所以要明确自己是谁(端口)
2.准备容器。DatagramPacket
3.接收数据。签收
4.解析
5.释放资源
```

##### ② 案例代码	

```java
//1.创建接收端的Socket对象(DatagramSocket)。因为是接收端，所以要明确自己是谁(端口),该端口号必须和发送端的完全一致。
DatagramSocket ds = new DatagramSocket(8888);
//2.准备容器。DatagramPacket
//byte[] bys = new byte[1024];
//DatagramPacket dp = new DatagramPacket(bys,bys.length);
DatagramPacket dp = new DatagramPacket(new byte[1024],1024);
//3.接收数据。签收。如果没有人发送数据，则程序在这里一直等待，直到有人发送数据。
ds.receive(dp);

//4.解析。当数据被接收之后，都放在了DatagramPacket中，所以，所谓的解析数据，其实就是解析DatagramPacket
InetAddress address = dp.getAddress();   //获取发送方的ip地址
System.out.println("sender ---> " + address.getHostAddress());
int port = dp.getPort();  //获取发送方的端口号
byte[] bys = dp.getData();  //获取发送的数据		
int length = dp.getLength();  //获取具体收到数据的长度
System.out.println(new String(bys,0,length));
System.out.println(port);

//5.释放资源
ds.close();
```



### 3.UDP协议-组播

#### 3.1 UDP协议-发送端

##### ① 操作步骤

```properties
1.基本操作和单播类似
2.在指定接收方的IP地址时, 指定的是组名
	组名使用IP来表示
	IP范围是(224.0.0.0 - 239.255.255.255)
3.正常发送数据
```

##### ② 案例代码

```java
DatagramSocket ds = new DatagramSocket();
byte[] bys = "hello 组播!".getBytes();

//指定要发送的组的名字
//组名使用IP来表示,IP范围是(224.0.0.0 - 239.255.255.255)
//组名(224.0.0.0-224.0.0.255)是预留地址,所以一般自定义组名从224.0.1.0开始
InetAddress address = InetAddress.getByName("224.0.1.0");

int port = 8888;
DatagramPacket dp = new DatagramPacket(bys,bys.length,address,port);
ds.send(dp);
ds.close();
```



#### 3.2 UDP协议-接收端

##### ① 操作步骤

```properties
1.使用MulticastSocket作为接收方的Socket
2.把当前计算机,加入到指定小组中
	组名任然使用IP来表示
	组名必须和发送方指定的组名保持一致
3.正常接收数据
```

##### ② 案例代码	

```java
//1.组播时,接收方使用MulticastSocket
MulticastSocket ms = new MulticastSocket(8888);

//2.把当前计算机,加入到指定小组中
ms.joinGroup(InetAddress.getByName("224.0.1.0"));

//3.正常接收数据(该怎么接收怎么接收,该怎么解析怎么解析)
DatagramPacket dp = new DatagramPacket(new byte[1024],1024);
ds.receive(dp);
byte[] data = dp.getData();  	
int length = dp.getLength(); 
System.out.println(new String(bys,0,length));
System.out.println(port);
ms.close();
```





### 4.TCP协议

#### 4.1 TCP协议-客户端

##### ① 操作步骤

```properties
1.创建Socket对象。指定“服务端”的ip地址和端口号
2.传输数据
    如果给“服务端”发消息，用输出流写。
    如果接收“服务端”发送的数据，则用输入流读。
3.释放资源
```

##### ② 案例代码			

```java
//1.创建Socket对象。指定“服务端”的ip地址和端口号
Socket s = new Socket("127.0.0.1", 10010);
    // 给服务端发消息
    OutputStream os = s.getOutputStream();
    os.write("hello!!!".getBytes());
 	os.flush();
    s.shutdownOutput();  //告诉服务器,我这边写完了

    // 接收“服务端”发送过来的数据
    InputStream is = s.getInputStream();
    byte[] bys = new byte[1024];
    int len = is.read(bys);
    System.out.println(new String(bys, 0, len));

//3.释放资源
s.close();
```

#### 4.2 TCP协议-服务端

##### ① 操作步骤

```properties
1.创建ServerSocket对象，指定端口号
2.调用accept()方法获取Socket对象。如果有客户端访问，则该方法执行，得到一个Socket。如果没有客户端访问，就一直等待。
3.传输数据
    如果给“客户端”发消息，用输出流写。
    如果接收“客户端”发送的数据，则用输入流读。
4.释放资源
```

##### ② 案例代码				

```java
//1.创建ServerSocket对象，指定端口号
ServerSocket ss = new ServerSocket(10010);
//2.调用accept()方法获取Socket对象。如果有客户端访问，则该方法执行，得到一个Socket。如果没有客户端访问，就一直等待。
Socket s = ss.accept();		
    //接收“客户端”发送过来的数据
    InputStream is = s.getInputStream();
    byte[] bys = new byte[1024];
    int len = is.read(bys);
    System.out.println(new String(bys,0,len));

    //向“客户端”发送数据	
    OutputStream os = s.getOutputStream();
    os.write("hello!!!".getBytes());

//3.释放资源
os.close();
s.close();

```

#### 4.3 TCP协议-补充

```java
概述:
	因为TCP协议的双端建立的有稳定联接,所以,当一方的输出流结束的时候,另外一方是不知道的.
    所以,在输出流结束的时候,需要告诉对方,我们的输出流结束了.
方法:
	public void shutdownOutput(); //该方法是Socket类的方法,表示当前Socket的输出流已经结束
```

