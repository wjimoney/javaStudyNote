## JavaSE进阶-day07

### 1.Set

#### 1.1 相关概述

```java
特点: Set集合是"无序,无索引,元素不可重复".
子类:
	TreeSet: 底层是"红黑树".该结构要求"元素排序"
	HashSet: 底层是"哈希表".该结构要求"元素必须重写hashCode和equals方法"    
扩展:
	LinkedHashSet: LinkedHashSet是HashSet的子类.它的要求和HashSet一样.LinkedHashSet是Set集合中唯一有序的集合.
```

#### 1.2 元素排序

##### ① 自然排序

```properties
概述: 所谓的自然排序,就是让元素本身能够自己排序
步骤:
	1.让"元素所在的类"实现Comparable接口
	2.重写compareTo方法. 方法中:this代表新添加元素,o代表集合已有元素
		如果方法返回值是负数: "新添加元素"比"集合已有元素"小,"新添加元素"放在左边(前边)
		如果方法返回值是0:   "新添加元素"和"集合已有元素"相同,"新添加元素"不再添加
		如果方法返回值是正数: "新添加元素"比"集合已有元素"大,"新添加元素"放在右边(后边)
```

##### ② 比较器排序

```properties
概述: 所谓的比较器排序,就是人为指定排序规则
步骤:
	1.创建TreeSet集合时,给TreeSet的构造方法中传递"Comparator的匿名内部类".
	2.在匿名内部类内部重写compare方法. 方法中:o1代表新添加元素,o2代表集合已有元素
		如果方法返回值是负数: "新添加元素"比"集合已有元素"小,"新添加元素"放在左边(前边)
		如果方法返回值是0:   "新添加元素"和"集合已有元素"相同,"新添加元素"不再添加
		如果方法返回值是正数: "新添加元素"比"集合已有元素"大,"新添加元素"放在右边(后边)
```

##### ③ 注意事项

```properties
如果既有"自然排序",又有"比较器排序",则遵循"比较器排序"规则.
```





### 2.数据结构(了解)

#### 2.1 树形结构

```properties
二叉树: 任意一个节点最多有2个子节点的树形结构,就称之为"二叉树".
二叉查找树: 任意一个节点的左子节点比自己小,右子节点比自己大,的二叉树,被称之为"二叉查找树".也叫"二叉搜索树,二叉排序树"
平衡二叉树: 任意节点的左右子树的高度差不能超过1.
红黑树: 红黑树是遵守自己的"红黑规则"的二叉查找树. TreeSet的底层就是"红黑树".
```

#### 2.2 哈希表

```properties
概述: 相当于新华字典,由"目录"和"数据"共同组成.
注意:
	JDK7之前: Hash表 = 数组 + 链表;
	JDK8之后: Hash表 = 数组 + 链表 + 红黑树;  链表长度>8时,会自动变为红黑树
```





### 3.Map<K,V>

#### 3.1 基本概述

```properties
概述: Map集合是双列集合. 所谓的双列集合,就是,存和取的时候,必须一对一对的操作.
注意:
	1.泛型中,K表示键的类型,V表示值的类型.
	2.Map中的键,无序,且不可以重复. Map中的值不做要求.
	3.Map中的键和值必须一一对应.
	4.键+值,称之为一个键值对,也可以叫做Entry对象.
```

#### 3.2 成员方法

```java
基本方法:
	public void put(键, 值);		//添加/覆盖. 如果键不存在就是添加,如果键存在就是覆盖.
    public 值 remove(键);			//根据键删除"键值对",把值返回.   
    public void clear();	 	 //清空整个集合
    public boolean containsKey(键);	//判断集合中是否包含指定的"键"
    public boolean containsValue(值);//判断集合中是否包含指定的"值"
    public boolean isEmpty();	//判断是否为空
    public int size();			//获取集合元素个数(键值对的对数)
获取方法:
	public 值 get(键);					   //根据键获取值.
    public Set<K> keySet();		 			//获取所有的"键"
    public Set<Map.Entry<K,V>> entrySet();	//根据所有的"键值对"
```

#### 3.3 集合遍历

##### ① 根据键获取值

```java
Map<String, String> map = new HashMap<>();
map.put("1号丈夫", "1号妻子");
map.put("2号丈夫", "2号妻子");
map.put("3号丈夫", "3号妻子");
map.put("4号丈夫", "4号妻子");
map.put("5号丈夫", "5号妻子");

//1.获取到所有的键
Set<String> keys = map.keySet();
//2.遍历Set集合得到每一个键
for (String key : keys) {
    //3.通过每一个键key，来获取到对应的值
    String value = map.get(key);
    System.out.println(key + "---" + value);
}
```

##### ② 键值对获取键和值

```java
Map<String, String> map = new HashMap<>();
map.put("1号丈夫", "1号妻子");
map.put("2号丈夫", "2号妻子");
map.put("3号丈夫", "3号妻子");
map.put("4号丈夫", "4号妻子");
map.put("5号丈夫", "5号妻子");

//1.首先要获取到所有的键值对对象。
Set<Map.Entry<String, String>> entries = map.entrySet();
//2.遍历,得到每一个键值对对象
for (Map.Entry<String, String> entry : entries) {
   	//3.从每个键值对对象获取"键"和"值"
    String key = entry.getKey();
    String value = entry.getValue();
    System.out.println(key + "---" + value);
}
```

#### 3.4 Map子类

```properties
HashMap: HashMap的键就是"HashSet",所以HashMap的键要求"重写HashCode和equals方法"
TreeMap: TreeMap的键就是"TreeSet",所以TreeMap的键要求"元素 "
```





