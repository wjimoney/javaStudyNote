## JavaSE进阶-day05

### 1.递归

```properties
概述:递归指的是"方法自己调用自己的现象".
步骤:
	1.定义方法.
	2.在方法中书写规律.
	3.利用已完成的方法,修改代码的语法性错误.
注意:
	递归次数不能太多.否则就会栈内存溢出.
```



### 2.时间类

#### 2.1 Date

```java
概述: Date是用来表示时间的类. 在Java中Date也仅仅表示时间.
构造:
	public Date(); //获取当前系统时间
方法:
	public long getTime();			//获取时间对象所代表的毫秒值
	public void setTime(long l);	//设置时间对象的毫秒值
```

#### 2.2 SimpleDateFormat

##### ① 相关概述

```java
概述: 该类是时间格式对象,可以实现"String <-> Date"的相互转换
构造: public SimpleDateFormat("日期时间格式");		//时间格式可以书写为:yyyy-MM-dd HH:mm:ss
方法:
	//String -> Date
	Date d = SimpleDateFormat对象.parse(字符串);
	//Date -> String
	String str = SimpleDateFormat对象.format(Date对象);
```

##### ② 案例

```java
//String -> Date
String str = "2022-10-17 11:18";
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //注意:时间格式必须和字符串格式保持一致
Date d = sdf.parse(str);

//Date -> String
Date d = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); //注意:时间格式必须和字符串格式保持一致
String str = sdf.format(d);	//2022年10月17日 11时22分29秒
```





### 3.异常

#### 3.1 相关概述

```properties
概述: 所谓的异常,就是Java程序中的"非语法性报错".
体系:
	Throwable:
		Error: 错误.一般和硬件有关系.我们无法处理.
		Exception: 异常
			编译时异常: 写代码过程中的报错.除了RuntimeException和他的子类之外的类,都是编译时异常.
			运行时异常: 运行代码的时候报错.RuntimeException和他的子类都是运行时异常.
```



#### 3.2 异常处理

##### ① 默认处理

```properties
概述: 所谓的默认处理,其实就是不管,让JVM帮我们处理异常.
处理:
	1.把"异常类型,异常原因,异常位置"打印到控制台
	2.终止当前程序的运行
```

##### ② 异常声明

```java
概述: 异常抛出使用关键字throws,只能用在方法上,代表该方法中有肯能会出现异常.
格式:
	public void 方法名() throws 异常类型{ .... } 
注意:
	方法把异常抛出去后,由方法的调用者负责处理该异常.一般用于编译时异常的处理.
```

##### ③ 异常捕获

```java
概述: 异常捕获,就是把异常抓住,不让他影响其他代码的运行
格式:
	try{
		//try中存放可能出现异常的代码
        //如果不出现异常,try正常执行,执行完毕后,try-catch结束.
        //如果出现异常,则停止try中的代码的运行,立即跳转到catch中
	}catch(异常类型 e){
		//如果try中出现异常,则执行catch
        //如果try中不出现异常,则catch不执行
	}
注意:
	使用快捷键"Ctrl + Alt + T",选择"6.try/catch"可以直接生成
```



#### 3.3 Throwable方法

```java
public String getMassage();	//获取异常原因
public String toString();	//获取"异常类型+异常原因"
public void printStackTrace();	//把"异常类型,异常原因,异常位置"打印到控制台
```



#### 3.4 throw

```java
概述: throw用于"人为制造异常".
语法:
	throw new 异常类型("异常信息");
```



#### 3.5 自定义异常

```properties
意义: 让异常类名可以"见名知意".
步骤:
	1.自定义一个类,继承自RuntimeException. //如果继承RuntimeException就是运行时异常,如果继承Exception就是编译时异常
	2.(空参构造,有参构造)生成空参和字符串参数的构造方法
```

