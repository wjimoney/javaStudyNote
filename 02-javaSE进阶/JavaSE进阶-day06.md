## JavaSE进阶-day06

### 1.集合体系

```properties
Collection: 单列集合(每次存取都是单个元素)
	List: 有序,有索引,元素可以重复
		ArrayList:
		LinkedList:
	Set: 无序,无索引,元素不可重复
		HashSet:
		TreeSet:
		
Map: 双列集合(每次存取都是一对元素)
	TreeMap:
	HashMap:
```



### 2.Collection

#### 2.1 集合概述

```java
概念: 是单例集合的顶层接口,集合中一次只能存取一个元素
方法:
	public boolean add(E e)		//添加元素
    public boolean remove(Object o)	    //从集合中移除指定的元素
    public boolean removeIf(Object o)	//根据条件进行删除
    public void clear()			//清空集合
    public boolean contains(Object o)	//判断集合中是否存在指定的元素
    public boolean isEmpty()		//判断集合是否为空
    public int size()			//集合的长度，也就是集合中元素的个数
```



#### 2.2 迭代器(了解)

##### ① 概述

```java
介绍:	迭代器又名遍历器,专门用于集合的遍历(map集合无法使用迭代器														)
获取: 
	Iterator<E> it = 集合对象.iterator(): 
使用:
	public boolean hasNext():  //判断集合中是否还有下一个元素
	public E next(): 		   //指针往后移动一次,并且返回下一个元素
	public void remove(); 	   //删除指针指向的那个元素(指针指向谁,就删除谁)
注意:
    1.一个迭代器对象只能使用一次,如果想再次遍历,就重新获取迭代器对象
    2.当使用迭代器进行遍历的时候,不允许对原始集合进行了添加或删除,如果非要删除,只能通过迭代器进行删除
```

##### ② 案例

```java
ArrayList<String> list = new ArrayList<>();
list.add("aaa");
list.add("bbb");
list.add("ccc");

//1.获取迭代器对象
Iterator<String> it = list.iterator();
//2.循环判断是否还有下一个元素
while (it.hasNext()){
    //3.获取下一个元素
    String next = it.next();
    //4.打印
    System.out.println(next);
}
```



#### 2.3 增强for(掌握)

```java
概述: 增强for是专门用来遍历"集合"和"数组". 遍历集合时,底层是"迭代器". 遍历数组时,底层是"普通for循环".
格式:
	for(元素类型 变量名 : 集合/数组){
		//"变量名"中存储着"集合或数组"中的每个元素
	}
注意:
	使用"集合或数组.for"快捷键,可以快速生成增强for循环
```





### 3.List集合

#### 3.1 相关概述

```java
特点: List集合是"有序,有索引,元素可以重复".
方法:
	public void add(索引位置, 要添加元素);	//在指定索引位置上,插入元素
	public 被删除元素 remove(索引位置);		//删除指定索引位置上的元素,并且把"被删除元素"返回
	public 旧元素 set(索引位置, 新元素);	   //修改指定索引位置上的元素,并且把"被修改元素"返回
	public 元素 get(索引位置);			  //获取指定索引位置上的元素
```

#### 3.2 常用子类

```properties
分类:
    ArrayList:  底层是"数组",所以查询快,增删慢.
    LinkedList: 底层是"链表",所以查询慢,增删快.
结论:
	1.如果数据查询比较多,增删比较少,就用ArrayList
	2.如果数据查询比较少,增删比较多,就用LinkedList
	3.如果啥都不知道,那就用ArrayList.
```



### 4.Set

#### 4.1 相关概述

```java
特点: Set集合是"无序,无索引,元素不可重复".
子类:
	TreeSet: 底层是"红黑树".该结构要求"元素排序"
	HashSet:        
```

#### 4.2 元素排序

##### ① 自然排序

```properties
概述: 所谓的自然排序,就是让元素本身能够自己排序
步骤:
	1.让"元素所在的类"实现Comparable接口
	2.重写compareTo方法
		如果方法返回值是负数: "新添加元素"比"集合已有元素"小,"新添加元素"放在左边(前边)
		如果方法返回值是0:   "新添加元素"和"集合已有元素"相同,"新添加元素"不再添加
		如果方法返回值是正数: "新添加元素"比"集合已有元素"大,"新添加元素"放在右边(后边)
```

##### ② 比较器排序





### 5.集合补充

#### 5.1.数据结构

```properties
概述: 数据存储的方式. 不同的数据存储方式,就称之为"不同的数据结构"
分类:
	栈: 先进后出,后进先出.
	队列: 先进先出,后进后出.
	数组: 查询快,增删慢.
	链表: 查询慢,增删快.
```



#### 5.2.泛型\<T>

##### ① 概述

```java
概述: 泛型本质上是一个"存储数据类型"的变量.
场景: 
	 public class 类名<T>{ ... }			//可以定义在类上
	 public interface 接口名<T>{ ... }		//可以定义在接口上
```

##### ② 应用

```properties
赋值: 在创建类的对象时,给泛型赋值.
注意:
	1.泛型只能赋值为引用数据类型
	2.如果不给泛型赋值,则泛型为Object类型.
	3.泛型可以定义在任何需要"数据类型"的地方.
	4.当泛型被赋值后,泛型会自动变为对应的数据类型.
```



