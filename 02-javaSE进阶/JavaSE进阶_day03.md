## JavaSE进阶_day03

### 1.接口新特性

#### ① 默认方法(掌握)

```java
概述: JDK8之后接口可以有默认方法
格式:
	public default void 方法名(){
		方法体
	}
特点: 子类可以重写,也可以不重写
```

#### ② 静态方法(了解)

```java
概述: JDK8之后接口可以有静态方法(不推荐使用)
格式:
	public static void 方法名(){
		方法体
	}
特点: 可以通过"接口名.方法()"来调用.
```

#### ③ 私有方法(了解)

```java
概述: JDK9之后接口可以有私有法(不推荐使用)
格式:
	private void 方法名(){ ...方法体... }
	private static void 方法名(){ ...方法体... }
特点: 
	"非静态私有方法"就是抽取"默认方法"的公共部分. 
    "静态私有方法"就是抽取"静态方法"的公共部分.
```





### 2.内部类

#### 2.1 相关概述

```properties
概述: 所谓的内部类,就是放在一个类的内部的类.
分类:
	成员内部类: 就是放在"类中方法外"的内部类.
	局部内部类: 就是放在"方法中"的内部类.
```

#### 2.2 成员内部类(了解)

```properties
使用: 外部类名.内部类名 对象名 = new 外部类().new 内部类();
场景: 成员内部类一般使用private修饰,用于限制类的使用范围(该类只自己用,别人都不能用).
```

#### 2.3 局部内部类(了解)

```properties
概述: 局部内部类就是放在"方法内部"的内部类
意义: 方法执行完毕后,类就会被销毁,节省内存空间.
```

#### 2.4 匿名内部类

```java
概述: 其实"匿名内部类"就是"局部内部类"的简化写法
格式:
	new 父类/父接口(){
        方法重写
    }
注意: "匿名内部类"本质上是一个"继承了父类/实现了父接口"的子类的对象
```



### 3.Lambda表达式

#### 3.1 概述

```properties
概述: Lambda表达式是"匿名内部类"的简化写法.他基于的是"可推断,可省略"原则,只保留"方法重写部分"即可
格式: (方法参数) -> { 方法体 }
```

#### 3.2 补充

```properties
快捷方式: Alt + 回车 -> 回车
省略模式:
	1.参数的数据类型可以省略. 如果参数有多个,则不能只省略一个.
	2.参数如果只有一个,则小括号可以省略.
	3.如果方法体只有一句话,则"大括号,分号,return"可以一起省略.
```

#### 3.3 注意

```properties
1.Lambda是JDK8之后才有的技术.
2.Lambda表达式要求,父类必须是接口,且接口中有且仅有一个抽象方法
```

