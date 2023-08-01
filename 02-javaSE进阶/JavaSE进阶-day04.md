## JavaSE进阶-day04

### 0.API

```properties
概念:
    API:  API指的是"Java提前做好的那些类".
    API文档: 指的是"Java提前做好的类的说明书."
操作:
	1.打开API文档
	2.在左边索引位置下边的搜索框中,填写"你要找到类的名称",找到之后,点击回车
	3.看右边,类的说明
	4.看右边构造方法(有了构造方法才能创建对象)
	5.看普通方法(普通方法就是该类提供的功能)
```



### 1.Math

```java
概述: Math类中,定义了"和数学运算有关系的功能"
方法:
	public static int abs(int a)		//返回参数的绝对值
    public static double ceil(double a)	//向上取整
    public static double floor(double a)	//向下取整
    public static int round(float a)	//四舍五入
    public static int max(int a,int b)	//返回两个int值中的较大值
    public static int min(int a,int b)	//返回两个int值中的较小值
    public static double pow(double a,double b) //返回a的b次幂的值
    public static double random()		//返回值为double的随机数值，[0.0,1.0)
```



### 2.System

```java
概述: System中包含了一些"系统相关"和"JDK相关"的方法
方法:
	public static void exit(int status);	//终止当前虚拟机,0表示正常终止,非0表示异常终止.
	public static long  llis(); //获取当前系统时间的毫秒值
	public static void arraycopy(数据源数组, 起始索引, 目的地数组, 起始索引, 拷贝个数); //拷贝数组
```



### 3.Object

```java
概述: Object是一切类的父类. 所有类都直接或间接继承该类.
方法:
	public String toString(); 	//默认返回"对象的地址值".要求子类重写该方法.重写后直接打印对象时,会打印对象的内容.
	public boolean equals(对象); //默认比较"地址值",要求子类重写该方法,重写后,比较内容.
```



### 4.Objects

```java
概述: Objects是一个操作对象的工具类.
方法:
	public static Boolean isNull(对象)		 //判断对象是否"为空"
    public static Boolean nonNull(对象)		 //判断对象是否"不为空"
```



### 5.BigDecimal

```java
概述: BigDecimal是一个高精度的double类型数据. 在进行高精度运算时,要使用BigDecimal进行运算.
构造:
	public BigDecimal(double d);	
	public BigDecimal(String s);	//推荐使用,精确运算时,必须使用字符串构造方法
方法:
	public BigDecimal add(另一个BigDecimal对象)		//加法
    public BigDecimal subtract(另一个BigDecimal对象) //减法
    public BigDecimal multiply(另一个BigDecimal对象) //乘法
    public BigDecimal divide(另一个BigDecimal对象)	//除法
    public BigDecimal divide(另一个BigDecimal对象,精确几位,舍入模式)	//除法
	public BigDecimal setScale(精确几位,舍入模式)	//保留几位精度
补充:
	BigDecimal.ROUND_UP 	//舍入模式 -> 进一法. 		新版本用: RoundingMode.UP  
    BigDecimal.ROUND_FLOOR	//舍入模式 -> 去尾法. 		新版本用: RoundingMode.FLOOR
    BigDecimal.ROUND_HALF_UP//舍入模式 -> 四舍五入. 	新版本用: RoundingMode.HALF_UP
```



### 6.包装类

#### 6.1 概述

```java
概述:	就是基本数据类型对应的"引用数据类型形式",一般用于"基本数据类型和String之间的相互转换"
组成:
	byte 	-> 	Byte
    short 	-> 	Short
    int 	-> 	Integer
    long 	-> 	Long
    float 	-> 	Float
    double	-> 	Double
    char 	-> 	Character
    boolean -> 	Boolean  
注意:
	所有包装类,除了类名不一样之外,其功能和操作方式几乎一模一样.
```

#### 6.2 补充

```properties
装箱:	把"基本数据类型"变为"与之对应的包装类"
自动装箱: 指的就是自动发生的装箱操作 -> JDK5
拆箱: 把"包装类"变为"与之对应的基本数据类型"
自动拆箱: 指的就是自动发生的拆箱现象 -> JDK5
```

#### 6.3 Integer

```java
概述: Integer是int类型的包装类
转换:
	//String   ->    int
		int num = Integer.parseInt("数字类型字符串");
	//int   ->    String
		String s = 数字 + "";
		String s = String.valueOf(数字);
```



### 7.Arrays

```java
概述: 专门用来操作数组的工具类.
方法:
    public static void sort(int[] arr);    //对数组进行升序排列
    public static int binarySearch(int[] arr, int key);  //返回数组arr中的key元素所在的索引位置(必须从小到大的顺序).
    public static String toString(int[] arr);    //返回数组的字符串形式.("[1,2,3]")
```

