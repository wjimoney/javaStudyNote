## JavaSE进阶-day15

### 1.类的加载器

#### 1.1 概述(了解)

```properties
作用：
	负责将.class文件加载到内存中，并为之生成对应的 java.lang.Class 对象。		
分类：
	1）Bootstrap ClassLoader	//启动类加载器/内置类加载器
		负责加载$JAVA_HOME中jre/lib/rt.jar里所有的class，由C++实现，程序员无法获取也无法操作。
		换句话说，JDK中的默认有的类，都由该加载器加载。
	2）Platform ClassLoader	//平台类加载器
		负责加载java平台中扩展功能的一些jar包.负责加载和操作系统有关系的信息
		包括$JAVA_HOME中“jre\lib\*.jar”或-Djava.ext.dirs指定目录下的jar包
	3）System ClassLoader(App ClassLoader)	//应用类加载器
		负责加载classpath中指定的jar包及目录中class.
		换句话说，就是我们自己写的类都是由该加载器加载	
```

#### 1.2 应用(掌握)

```java
语法:
	//我们一般会用"类的加载器"帮我们加载"配置文件"
	InputStream is = 当前类类名.class.getClassLoader().getResourceAsStream("配置文件名字");
注意:
	1.配置文件必须放在src目录下.
    2.如果配置文件在src的根目录下,则直接写配置文件的名字
    3.如果配置文件在src中的包中,则书写"包名/配置文件名字"
```



### 2.反射(会用)

#### 2.1 概述

```properties
概念：
    程序在运行过程中,获“类的字节码”文件,从而获取类的“成员变量”,“成员方法”,“构造方法”,并且使用他们,这种现象就叫做反射。
步骤：
	1.先获取“类的字节码”
	2.从“类的字节码”获取“成员变量”，“成员方法”，“构造方法”
	3.使用这些“成员变量”，“成员方法”，“构造方法”
优点：
	1.可以越过访问权限,进行暴力破解			
	2.可以提高代码的“可扩展性”，是很多框架的底层实现。
```



#### 2.2 获取类的字节码文件：

```properties
方式1: Class clazz = 类名.class
方式2: Class clazz = 对象.getClass();
方式3: Class clazz = Class.forName("类的全名称"); 
```



#### 2.3 越过权限

```java
//如果flag是true，则暴力破解，越过权限。
public void setAccessible(boolean flag);  //构造方法，成员变量，成员方法三者均可以使用
```



#### 2.4 反射-构造方法

##### ① 获取(通过Class类的方法):

```java
public Constructor[] getConstructors();   							//获取所有的public修饰的构造方法
public Constructor getConstructor(Class... parameterTypes); 		//获取指定的public修饰的构造方法
public Constructor[] getDeclaredConstructors();   					//获取所有的构造方法(包含private)
public Constructor getDeclaredConstructor(Class... parameterTypes);//获取指定的构造方法(包含private)
```

##### ② 使用(通过Constructor类的方法):	

```java
T newInstance(Object... initargs) 
```

##### ③ 案例：

```java
//1.通过无参构造方法创建对象
//1.1 通过反射找到类的字节码文件
Class clazz = Class.forName("com.itheima.test.Student");
//1.2 获取无参构造方法
Constructor c = clazz.getDeclaredConstructor();  
//1.3暴力破解,越过权限
c.setAccessible(true);  	
//1.4 通过无参构造方法创建一个对象	
Student obj = (Student)c.newInstance();   			


//2.通过有参构造方法创建对象
//2.1 通过反射找到类的字节码文件
Class clazz = Class.forName("com.itheima.test.Student");
//2.2获取有参构造方法(参数必须有两个,第一是String类型，第二个是int类型)
Constructor c = clazz.getDeclaredConstructor(String.class,int.class);
//2.3暴力破解,越过权限
c.setAccessible(true);  	
//2.4通过有参构造方法创建一个对象
Student obj = (Student)c.newInstance("张曼玉",18);   	
```

​	

#### 2.5 反射-成员变量

##### ① 获取(通过Class类的方法):	

```java
public Field[] getFields();  				//获取所有的public修饰的成员变量
public Field getField(String name); 		//获取指定的public修饰的成员变量			
public Field[] getDeclaredFields();  		//获取所有的成员变量(包含private)
public Field getDeclaredField(String name); //获取指定的成员变量(包含private) 
```

##### ② 使用(通过Field类的方法):	

```java
public Object get(Object obj);  			//获取"obj对象"中"当前Field成员变量"的值;
public void set(Object obj, Object value); //把"obj对象"中"当前Field成员变量"的值设置为"value";		
```

##### ③ 案例(使用之前必须有对象)。

```java
//创建一个对象
Constructor c = clazz.getConstructor(String.class,int.class);   
Object obj = c.newInstance("张曼玉",18);	

//获取成员变量name
Field f = clazz.getField("name");

//获取obj中成员变量name的值
Object nameValue = f.get(obj);

//把"obj"中成员变量name的值设置为"张三丰";
f.set(obj,"张三丰");
```



#### 2.6 反射-成员方法

##### ① 获取(通过Class类的方法):

```java
public Method[] getMethods()    										//得到所有的public修饰的成员方法
public Method getMethod(String name, Class... parameterTypes) 			//得到指定的public修饰的成员方法
public Method[] getDeclaredMethods()  									//得到所有的成员方法(包含私有)
public Method getDeclaredMethod(String name, Class... parameterTypes)	//得到指定的成员方法(包含私有)	
```

##### ② 使用(通过Method类的方法):

```java
Object invoke(Object obj, Object... args)  		
```

##### ③ 案例:		

```java
//获取学生类的字节码对象
Class clazz = Class.forName("com.itheima_01.Student");
//获取学生类的对象,因为有了学生类的对象之后才能操作方法
Object stu = clazz.newInstance();
//获取成员方法
Method m = clazz.getMethod("getName");  //获取的是名字叫做getName的无参的方法，
m.invoke(stu);  //让stu对象调用getName方法、

//获取名字叫做setName的有参的方法，参数只有一个，参数的类型是String
Method m = clazz.getMethod("setName",String.class); 
//让stu对象调用setNamee方法
m.invoke(stu,"参数的值");  //如果无返回值,则直接调用
Object obj = m.invoke(stu,"参数的值");  //如果有返回值,则需要接收返回值
```

