## JavaSE进阶-day08

### 1.集合补充

#### 1.1 可变参数

```java
概述: 可变参数,其实就是一种特殊的参数. 可变参数本质上是一个数组
格式:
	public void 方法名(数据类型... 参数名){
		
	}
注意:
	1.可变参数本质上就是一个数组
    2.如果方法的参数有多个,则可变参数必须放在最后.
    3.方法最多只能有一个可变参数.
    4.可变参数的"实参",可以给1个,可以给多个,也可以没有,还可以直接是一个数组.
```

#### 1.2 数组转集合

##### ① 概述

```java
语法:
	//JDK8之前
	List<T> list = Arrays.asList(T... arr);
	//JDK9之后
	List<T> list = List.of(T... arr);
	Set<T> set = Set.of(T... arr);
注意:
	1.不论是JDK8之前,还是JDK9之后.转换后的集合,都不允许"增删改".
    2.如果非要"增删",则可以把"不可变集合"作为构造方法的参数,重新创建集合,新集合就可以"增删改"了
    3.转换时,要求数组必须是引用数据类型的数组.
```

##### ② 案例

```java
public class TestDemo {
    public static void main(String[] args) {
        //1.准备数组
        String[] arr = {"aaa", "bbb", "ccc"};

        //2.数组转换为集合
        List<String> jdk8List = Arrays.asList(arr);

        //3.把"不可变集合"作为构造方法参数,重新创建集合(新集合就可以"增删改"了)
        ArrayList<String> newList = new ArrayList<>(jdk8List);
        System.out.println(newList);
        newList.add("ddd");
        System.out.println(newList);
        
        
        //另外: 该操作可以快速创建集合,并在集合中添加数据
        //ArrayList<String> list = new ArrayList<>(List.of("aaa","bbb","ccc"));
    }
}
```



### 2.Stream流

#### 2.1 概述

```properties
概述: Stream是一种专门快速操作集合的技术. JDK8之后才可以使用.
注意: 
	1.Stream流的操作不能改变源数据.
	2.Stream流只能操作一次.
	3.每个Stream流最终都必须有"终结方法".
```

#### 2.2 获取

```java
Stream<T> s = Stream.of(T...);			  //"多个普通数据"获取Stream流
Stream<T> s = Stream.of(数组);			 //"数组"获取Stream流
Stream<T> s = 集合对象.stream();			//"单列集合"获取Stream流
Stream<T> s = Map集合.keySet().stream();	 //"Map集合的键"获取Stream流
Stream<T> s = Map集合.values().stream();	 //"Map集合的值"获取Stream流
Stream<T> s = Map集合.entrySet().stream(); //"Map集合的键值对"获取Stream流
```

#### 2.3 操作

```java
public Stream<T> filter(Predicate predicate) //用于对流中的数据进行过滤(参数内部,返回值为false则丢掉,为true则留下) 
public Stream<T> limit(long maxSize)	//截取前指定参数个数的数据         
public Stream<T> skip(long n)	//跳过指定参数个数的数据，       
public static Stream<T> concat(Stream a, Stream b)	//合并a和b两个流为一个流
public Stream<T> distinct()	//去除重复(根据hashcode和equals方法去除重复)
public void forEach(Consumer action)	//对此流的每个元素执行操作
public long count()	//获取次流中的元素的个数
public Stream map(Function f);	//把Stream转换为其他类型的Stream
```

#### 2.4 转换

```java
List<T> list = Stream流.collect(Collectors.toList());	//把Stream流转换为List集合
Set<T> set = Stream流.collect(Collectors.toSet());		//把Stream流转换为List集合
```

