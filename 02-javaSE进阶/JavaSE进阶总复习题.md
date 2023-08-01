###  编程题【Lambda】

```java
public class TestInvokeDirect {
    public static void main(String[] args) {
        // TODO 请分别使用Lambda调用invokeDirect方法,让其最终效果为在控制台打印"导演拍电影啦！"
    }
    private static void invokeDirect(Director director) {
        director.makeMovie();
    }
}

interface Director {
	void makeMovie();
}
```

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        // TODO 请分别使用Lambda调用invokeDirect方法,让其最终效果为在控制台打印"导演拍电影啦！"
        invokeDirect(() ->   System.out.println("导演拍电影啦！"));
    }

    private static void invokeDirect(Director director) {
        director.makeMovie();
    }
}

interface Director {
    void makeMovie();
}
```

### 编程题【Math类】

```
请编程进行以下运算：
1.请计算3的5次幂
2.请计算3.2向上取整的结果
3.请计算3.8向下取整的结果
4.请计算5.6四舍五入取整的结果
```

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        //请计算3的5次幂
        System.out.println(Math.pow(3,5));

        //请计算3.2向上取整的结果
        System.out.println(Math.ceil(3.2));

        //请计算3.8向下取整的结果
        System.out.println(Math.floor(3.8));

        //请计算5.6四舍五入取整的结果
        System.out.println(Math.round(5.6));
    }
}
```

###  编程题【System类】

```
有以下数组：
   int[] arr = {10,27,8,5,2,1,3,55,88};
请定义一个新数组，长度为5，并将arr中的"8,5,2,1,3"数字复制到新数组。
```

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        // 有以下数组：
        // int[] arr = {10,27,8,5,2,1,3,55,88};
        // 请定义一个新数组，长度为5，并将arr中的"8,5,2,1,3"数字复制到新数组。

        int[] arr = {10,27,8,5,2,1,3,55,88};
        int[] newArr = new int[5];
        System.arraycopy(arr,2,newArr,0,5);

        for (int i = 0; i < newArr.length; i++) {
            System.out.println(newArr[i]);
        }

    }
}
```



### 编程题【BigDecimal类】

```
有以下double数组：
	double[] arr = {0.1,0.2,2.1,3.2,5.56,7.21};
请使用BigDecimal编程.
计算它们的总值及平均值(四舍五入保留小数点后2位) 
```

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        //有以下double数组：
        //double[] arr = {0.1,0.2,2.1,3.2,5.56,7.21};
        //请使用BigDecimal编程计算它们的总值及平均值(四舍五入保留小数点后2位)

        //数组
        double[] arr = {0.1, 0.2, 2.1, 3.2, 5.56, 7.21};
        //定义求和变量
        BigDecimal sum = new BigDecimal("0.0");
        //遍历数组
        for (int i = 0; i < arr.length; i++) {
            //把每个double都转换为BigDecimal
            BigDecimal bd = BigDecimal.valueOf(arr[i]);
            sum = sum.add(bd);
        }
        //求平均值
        //BigDecimal.ROUND_HALF_UP: 使用四舍五入的模式
        BigDecimal avg = sum.divide(BigDecimal.valueOf(arr.length),2,BigDecimal.ROUND_HALF_UP);

        //四舍五入保留小数点后2位
        sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

        System.out.println("数组的总值为:" + sum);
        System.out.println("数组的平均值为:" + avg);

    }
}
```

### 编程题【包装类】

#### 1 题目一

> 请从控制台接收一个String类型的“分数”值，要求把这个String类型的分数值转换为int值，并将分数 + 5分后打印到控制台。

答案:

```java
public class TestDemo01 {
    public static void main(String[] args) {
        // 1 题目一
        // 请从控制台接收一个整数的“分数”值，
        // 要求用String类型的变量存储，
        // 程序可以将这个String类型的分数值转换为int值，
        // 并将分数 + 5分后打印到控制台。
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个分数:");
        String line = sc.nextLine();
        //转换为数字
        int score = Integer.parseInt(line);
        //将分数 + 5
        score += 5;
        System.out.println(score);

    }
}
```

#### 2 题目二

> 1. 请定义学员类，有以下成员属性：
>
> ​         姓名：String类型
>
> ​         年龄：int
>
> ​         身高：double
>
> ​         婚否：boolean
>
> ​         性别：String
>
> 2. 请从控制台接收以下数据：要求, 键盘录入时, 都以String类型进行键盘录入
>
> ​         姓名：王哈哈
>
> ​         年龄：24
>
> ​         身高：1.82
>
> ​         婚否：false
>
> ​         性别：男
>
> 3. 请创建“学员对象”，并将上述键盘录入得到的数据,  经过转换后, 存储到这个对象中,  最后打印到控制台。

 答案:

学员类

```java
public class Student {
    private String name;    //姓名
    private int age;        //年龄
    private double height;  //身高
    private boolean marriage;//婚否
    private char gender;    //性别

    public Student() {
    }

    public Student(String name, int age, double height, boolean marriage, char gender) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.marriage = marriage;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isMarriage() {
        return marriage;
    }

    public void setMarriage(boolean marriage) {
        this.marriage = marriage;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", marriage=" + marriage +
                ", gender=" + gender +
                '}';
    }
}
```

```java
public class TestDemo02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入姓名:");
        String name = sc.nextLine();
        System.out.println("请输入年龄:");
        String str_age = sc.nextLine();
        System.out.println("请输入身高:");
        String str_height = sc.nextLine();
        System.out.println("请输入婚否:");
        String str_marriage = sc.nextLine();
        System.out.println("请输入性别:");
        String str_gender = sc.nextLine();

        //转换
        int age = Integer.parseInt(str_age);
        double height = Double.parseDouble(str_height);
        boolean marriage = Boolean.parseBoolean(str_marriage);
        char gender = str_gender.charAt(0);

        Student s = new Student(name,age,height,marriage,gender);
        System.out.println(s);

    }
}
```

###  编程题【排序、查找算法】

#### 1 题目一

> 请按以下要求顺序编写程序：
>
> 1. 定义测试类，定义main()方法；
>
> 2. 定义以下数组：
>
>    int[] arr = {1,2,432,32,54,32,3,7,657,563,25,43,6,463,52};
>
> 3. 把数组转换为字符串, 要求格式为”[1, 2, 3]”形式, 并打印到控制台
>
> 4. 对数组进行进行升序排列；
>
> 5. 把升序排列后的数组, 转换为字符串,并打印到控制台

 答案:

```java
public class TestDemo01 {
    public static void main(String[] args) {
        int[] arr = {1,2,432,32,54,32,3,7,657,563,25,43,6,463,52};
        //1.把数组转换为字符串, 要求格式为”[1, 2, 3]”形式, 并打印到控制台
        String str1 = Arrays.toString(arr);
        System.out.println(str1);

        //2.对数组进行进行升序排列；
        Arrays.sort(arr);

        //3.把升序排列后的数组, 转换为字符串,并打印到控制台
        String str2 = Arrays.toString(arr);
        System.out.println(str2);

    }
}
```

#### 2 题目二

> 请定义main()方法，在main()中定义以下数组：
>
> int[] arr = {431,54,25,25,26,45,2,4,65,3,64,6,46,7,54};
>
> 1. 对数组进行升序排列；
>
> 2. 排序后，使用二分法查找2, 并打印查询结果；
>
> 3. 再使用二分法查找200，并打印查找结果；

 答案:

```java
public class TestDemo02 {
    public static void main(String[] args) {
        int[] arr = {431,54,25,25,26,45,2,4,65,3,64,6,46,7,54};
        //对数组进行升序排列；
        Arrays.sort(arr);

        //排序后，使用二分法查找2, 并打印查询结果；
        int index1 = Arrays.binarySearch(arr, 2);
        System.out.println(index1);

        //再使用二分法查找200，并打印查找结果；；
        int index2 = Arrays.binarySearch(arr, 200);
        System.out.println(index2);

    }
}
```

#### 3 题目三

> 已知有个字符串"93,65,72,84,53",要求,对这些数据进行排序.
>
> 打印结果: [53, 65, 72, 84, 93]

 答案:

```java
public class TestDemo03 {
    public static void main(String[] args) {
        //1.已知字符串
        String str = "93,65,72,84,53";
        //2.切分字符串
        String[] ss = str.split(",");
        //3.遍历并转换为int[]
        int[] arr = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            int num = Integer.parseInt(ss[i]);
            arr[i] = num;
        }
        //4.给数组排序
        Arrays.sort(arr);
        //5.转换为String类型
        String arrStr = Arrays.toString(arr);
        //6.输出
        System.out.println(arrStr);
    }
}
```

### 编程题【递归】

```java
/**
 * 7 编程题【递归】
 *    1 题目一
 *    请使用递归计算5的阶乘
 */
//答案:
public class TestDemo01 {
    public static void main(String[] args) {
        int jc = jc(5);
        System.out.println(jc);
    }

    public static int jc(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * jc(n - 1);
        }
    }
}
```

### 编程题【递归】



```java
/**
 *  编程题【递归】
 * 古典问题：
 * 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，
 * 假如兔子都不死，问第20个月的兔子对数为多少？
 * 提示： 兔子的规律
 * 第1月    1对
 * 第2月    1对
 * 第3月    2对
 * 第4月    3对
 * 第5月    5对
 * 第6月    8对
 * 第7月    13对
 * …
 */
 //答案:
 public class TestDemo02 {
    public static void main(String[] args) {
        int fb = fb(20);
        System.out.println(fb);
    }

    public static int fb(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fb(n-1) + fb(n - 2);
        }
    }
}
```



### 编程题【异常】

#### 1.1 题目一

> 请定义main()方法，在main()中按以下顺序要求编写代码：
>
> 1. 分别从控制台接收两个整数
>
> 2. 计算“第一个数 / 第二个数”的结果，并打印；
>
> 3. 为了防止第二个数为0导致异常，请在计算时使用异常处理。当出现异常时，向用户打印：第二个数不能为0！！

答案:

```java
public class TestDemo01 {
    public static void main(String[] args) {
        // 1. 分别从控制台接收两个整数
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个数字");
        int num1 = sc.nextInt();
        System.out.println("请输入第二个数字");
        int num2 = sc.nextInt();
        //3. 为了防止第二个数为0导致异常，请在计算时使用异常处理。当出现异常时，向用户打印：第二个数不能为0！！
        try {
            //2. 计算“第一个数 / 第二个数”的结果，并打印；
            int a = num1 / num2;
            System.out.println(a);
        } catch (Exception e) {
            System.out.println("第二个数不能为0");
        }
    }
}
```

#### 1.2 题目二

> 请定义main()方法，在main()中按以下顺序要求编写代码：
>
> 1. 定义一个String[]数组如下：
>
>    String[] arr = {”星期一”,”星期二”,”星期三”,”星期四”,”星期五”,”星期六”,”星期日”};
>
> 2. 请用户输入一个整数1--7的值：
>
> 3. 根据用户输入，从数组中取出对应的星期名称，例如：
>
>    用户输入：1
>
>    程序提示：星期一
>
> 4. 为了防止用户输入小于10或者大于7的值，请使用异常处理从数组中取出对应的“星期名称”，在异常处理中打印：输入错误！！

答案:

```java
public class TestDemo02 {
    public static void main(String[] args) {
        //1. 定义一个String[]数组如下：
        String[] arr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        //2. 请用户输入一个整数1--7的值：
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个整数(1--7):");
        int num = sc.nextInt();
        //4. 为了防止用户输入小于1或者大于7的值，请使用异常处理从数组中取出对应的“星期名称"，在异常处理中打印：输入错误！！
        try {
            //3. 根据用户输入，从数组中取出对应的星期名称，例如：用户输入：1, 程序提示：星期一
            String str = arr[num];
            System.out.println(str);
        } catch (Exception e) {
            System.out.println("输入错误！！");
        }
    }
}
```

### 编程题【自定义异常】

> 请按以下步骤要求编写代码：
>
> 1. 定义一个“年龄异常类”：AgeException，使其继承自RuntimeException，并添加无参、String参数的构造方法；
>
> 2. 定义一个“性别异常类”：SexException，使其继承自RuntimeException，并添加无参、String参数的构造方法；
>
> 3. 定义一个Student类，属性：姓名、性别、年龄。
>
>    在性别的set方法中判断是否是男/女，如果不是则抛出：性别异常
>
>    在年龄的set方法中判断年龄是否是15--50之间，如果不是则抛出：年龄异常
>
> 4. 编写测试类，创建一个Student对象，并在try{}中调用setXxx()方法为对象属性赋值，在catch()中打印年龄错误。

 答案:

年龄异常类”：AgeException

```java
package com.itheima.test06;

public class AgeException extends RuntimeException{

    public AgeException() {
    }

    public AgeException(String message) {
        super(message);
    }
}
```

“性别异常类”：SexException

```java
package com.itheima.test06;

public class SexException extends RuntimeException{

    public SexException() {
    }

    public SexException(String message) {
        super(message);
    }
}
```

定义一个Student类，

```java
public class Student {
    private String name;
    private String gender;
    private int age;

    public Student() {
    }

    public Student(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        //在性别的set方法中判断是否是男/女，如果不是则抛出：性别异常
        if (!"男".equals(gender) && !"女".equals(gender)) {
            throw new SexException("性别异常");
        }
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        //在年龄的set方法中判断年龄是否是15--50之间，如果不是则抛出：年龄异常
        if (age < 15 || age > 50) {
            throw new AgeException("年龄异常");
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
```

测试类

```java
public class TestDemo {
    public static void main(String[] args) {
        Student s = null;
        try {
            s = new Student();
            s.setName("张三");
            s.setAge(18);
            s.setGender("男");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(s);
    }
}
```

### 三 编程题【Date】

#### 3.1 题目一

> 请编写程序，从控制台接收一个“生日”，格式：yyyy-MM-dd，程序要能够计算并打印他来到世界xx天。
>
> **注意：“生日”必须早于“当前日期”，否则提示：生日必须早于当前日期！**

答案:

```java
public class TestDemo01 {
    public static void main(String[] args) throws ParseException  {
        test01();
        test02();
    }

    //方式1-> 使用Date,计算毫秒值
    private static void test01() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的生日(格式yyyy-MM-dd):");
        String line = sc.nextLine();
        //把生日转换为Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse(line);
        //把Date转换为毫秒值
        long birthdayTime = birthday.getTime();
        //计算距离现在的毫秒值
        long times = System.currentTimeMillis() - birthdayTime;
        //换算为天
        long days = times / 1000 / 60 / 60 / 24;
        if (days <= 0) {
            System.out.println("生日必须早于当前日期！");
        } else {
            System.out.println("您已经来到这个世界" + days + "天了");
        }
    }

    //方式2 -> 使用LocalDate
    private static void test02() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的生日(格式yyyy-MM-dd):");
        String line = sc.nextLine();
        //把生日转换为时间
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(line, dtf);
        //计算生日距今的天数
        long days = birthday.until(LocalDate.now(), ChronoUnit.DAYS);
        if (days <= 0) {
            System.out.println("生日必须早于当前日期！");
        } else {
            System.out.println("您已经来到这个世界" + days + "天了");
        }
    }
}
```

#### 3.2 题目二

> 请编写程序，从控制台接收一个“日期”，格式：yyyy-MM-dd，程序将其转换为：xxxx年xx月xx日的格式输出到控制台。

答案:

```java
public class TestDemo04 {
    public static void main(String[] args) throws ParseException {
        //1.键盘录入数据
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入指定日期(格式yyyy-MM-dd):");
        String dateStr = sc.nextLine();
        //2.设置日期格式
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //3.把字符串转换为日期类型
        Date date = sdf1.parse(dateStr);
        //4.定义第二个日期格式
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
        //5.把日期转换为指定格式
        String format = sdf2.format(date);
        System.out.println(format);
    }
}
```

### 编程题【Collection集合】

#### 题目

> 请定义一个Collection类型的集合，存储以下字符串：
>
> ​      “JavaEE企业级开发指南”，”Oracle高级编程”，”MySQL从入门到精通”，”Java架构师之路”
>
> 请编程实现以下功能：
>
> 1. 使用迭代器遍历所有元素，并打印
> 2. 使用迭代器遍历所有元素，筛选书名小于10个字符的，并打印；
> 3. 使用迭代器遍历所有元素，筛选书名中包含“Java”的，并打印
> 4. 如果书名中包含“Oracle”，则删掉此书。删掉后，遍历集合，打印所有书名。 

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        Collection<String> coll = new ArrayList<>();
        coll.add("JavaEE企业级开发指南");
        coll.add("Oracle高级编程");
        coll.add("MySQL从入门到精通");
        coll.add("Java架构师之路");

        //使用迭代器遍历所有元素，并打印
        Iterator<String> it1 = coll.iterator();
        while (it1.hasNext()) {
            String str = it1.next();
            System.out.println(str);
        }
        System.out.println("------------------");

        //使用迭代器遍历所有元素，筛选书名小于10个字符的，并打印；
        Iterator<String> it2 = coll.iterator();
        while (it2.hasNext()) {
            String str = it2.next();
            if (str.length() < 10) {
                System.out.println(str);
            }
        }
        System.out.println("------------------");



        //使用迭代器遍历所有元素，筛选书名中包含“Java”的，并打印
        Iterator<String> it3 = coll.iterator();
        while (it3.hasNext()) {
            String str = it3.next();
            if (str.contains("Java")) {
                System.out.println(str);
            }
        }
        System.out.println("------------------");

        //如果书名中包含“Oracle”，则删掉此书。删掉后，遍历集合，打印所有书名。
        Iterator<String> it4 = coll.iterator();
        while (it4.hasNext()) {
            String str = it4.next();
            if (str.contains("Oracle")) {
                it4.remove();
                continue;   //被删除后就不打印了
            }
            System.out.println(str);
        }
        System.out.println("------------------");
    }
}
```

### 编程题【增强for】

#### 题目

> 请定义一个List类型的集合，存储以下分数信息：
>
> ​       88.5,39.2,77.1,56.8,89,99,59.5
>
> 请编程实现以下功能：
>
> Ø 使用增强for遍历所有元素，并打印
>
> Ø 使用增强for遍历所有元素，打印不及格的分数；
>
> Ø 使用增强for遍历所有元素，计算不及格的分数的数量，和平均分，并打印计算结果。
>
> Ø 使用增强for遍历所有元素，求出最高分，并打印；
>
> (注意：以上所有功能写在一个main()方法中，但请单独实现)

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        List<Double> scores = new ArrayList<>();
        scores.add(88.5);
        scores.add(39.2);
        scores.add(77.1);
        scores.add(56.8);
        scores.add((double) 89);
        scores.add((double) 99);
        scores.add(59.5);

        //使用增强for遍历所有元素，并打印
        for (Double score : scores) {
            System.out.println(score);
        }
        System.out.println("---------------------------------");

        //使用增强for遍历所有元素，打印不及格的分数；
        for (Double score : scores) {
            if (score < 60) {
                System.out.println(score);
            }
        }
        System.out.println("---------------------------------");

        //使用增强for遍历所有元素，计算不及格的分数的数量，和平均分，并打印计算结果。
        int count = 0;
        double sum = 0;
        for (Double score : scores) {
            if (score < 60) {
                count++;
                sum += score;
            }
        }
        double avg = sum/count;
        System.out.println(count);
        System.out.println(avg);
        System.out.println("---------------------------------");

        //使用增强for遍历所有元素，求出最高分，并打印；
        double max = scores.get(0);
        for (Double score : scores) {
            if (score > max){
                max = score;
            }
        }
        System.out.println(max);

    }
}
```

### 编程题【Collection】

> 产生1-100的随机数，并放到一个集合中.
>
> 要求:
>
> 1. 集合中的数字不能重复.
> 2. 集合中总共存储10个数据.
> 3. 遍历集合,并把所有数据打印到控制台。

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        //1.定义一个集合
        Collection<Integer> list = new ArrayList<>();
        int[] arr = new int[10];
        //2.产生10个1-100的随机数，并放到集合中
        Random r = new Random();
        //3.while死循环,表示一直放
        while(true){
            //3.1 产生随机数
            int num = r.nextInt(100) + 1;
            //3.2 判断产生的随机数是否存在,如果不存在,才方进入
            if (!list.contains(num)){
                list.add(num);
            }
            //4.判断 集合中元素是否够10个,够了就停止
            if (list.size() >= 10){
                break;
            }
        }


        //5.遍历集合并打印
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println("--------------"+list.size()+"-----------");
    }
}
```

 

### 6 编程题【Set接口】

#### 6.1 题目

> ​    请编写main()方法，按以下要求顺序
>
> Ø 定义一个Set集合，并存储以下数据：
>
> 刘备，关羽，张飞，刘备，张飞
>
> Ø 打印集合大小
>
> Ø 使用迭代器遍历集合，并打印每个元素
>
> Ø 使用增强for遍历集合，并打印每个元素

答案:

```java
public class TestDemo {
    public static void main(String[] args) {
        //定义一个Set集合，并存储以下数据：
        Set<String> set = new HashSet<>();
        set.add("刘备");
        set.add("关羽");
        set.add("张飞");
        set.add("刘备");
        set.add("张飞");

        //打印集合大小
        System.out.println(set.size());

        //使用迭代器遍历集合，并打印每个元素
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String next = it.next();
            System.out.println(next);
        }

        //使用增强for遍历集合，并打印每个元素
        for (String s : set) {
            System.out.println(s);
        }
    }
}
```

#### 6.2 题目

> 请按以下要求顺序编码：
>
> 1. 定义学生类Student，属性：姓名、年龄
>
> 2. 定义测试类，及main()方法
>
> 3. 定义一个存储Student类型的TreeSet集合
>
> 4. 创建以下三个Student对象
>
>    张三,20
>
>    李四,21
>
>    张三,20
>
> 5. 将上述三个对象存储到Set集合中
>
> 6. 要求: 学生存储在集合后,自动排序, 排序的顺序为: 
>
>    Ø 先按照年龄排序升序排列
>
>    Ø 如果年龄相等, 则按照姓名升序排列
>
>    Ø 如果两者均相等, 则不允许重复存储

 答案:

定义学生类Student

```java
public class Student implements Comparable<Student>{
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        //先按照年龄排序倒序排列
        int num = this.age - o.age;
        //如果年龄相等, 则按照姓名倒序排列
        if (num == 0){
            num =  this.name.compareTo(o.name);
        }
        return num;
    }
}
```

定义测试类

```java
public class TestDemo {
    public static void main(String[] args) {
        //定义一个存储Student类型的TreeSet集合
        TreeSet<Student> set = new TreeSet<>();
        set.add(new Student("张三",20));
        set.add(new Student("李四",21));
        set.add(new Student("张三",20));

        for (Student stu : set) {
            System.out.println(stu);
        }

    }
}
```