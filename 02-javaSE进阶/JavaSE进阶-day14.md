## JavaSE进阶-day14

### 1.日志

```properties
概述: 日志可以理解为是一个输出语句,他可以输出到控制台,也可以输出到文件.
分类:
	日志的规范: commons-loggin(效率低), slf4j(效率高)
	日志的实现: log4j, logback(常用)
```



### 2 logback

#### 2.1 概述

```
概念:	logback是一种实现了slf4j规范的日志包.
注意:
	1.logback的配置文件的名字必须是"logback.xml"
	2.配置文件存放的位置必须是"src根目录"
	3.logback日志分为"TRACE,DEBUG,INFO,WARN,ERROR,ALL和OFF",默认"DEBUG"
	4.日志会打印"大于等于当前级别的信息"
```

#### 2.2 操作

##### ① 导入jar包

```properties
jar包:
	logback-classic-1.2.3.jar	日志的实现
    logback-core-1.2.3.jar 	基本代码
    slf4j-api-1.7.26.jar 日志的规范
步骤:
    1.在当前模块下,创建一个名字叫做lib的文件夹
    2.把jar包复制到lib文件夹中
    3.在lib文件夹上"右键 -> Add As Library"
```

##### ② 导入配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!--
        CONSOLE ：表示当前的日志信息是可以输出到控制台的。
    -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--输出流对象 默认 System.out 改为 System.err-->
        <target>System.out</target>
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度
               %c:运行的类 %msg：日志消息，%n是换行符-->
            <!--<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level] %c [%thread] : %msg%n</pattern>-->
            <pattern>%date{yyyy-MM-dd HH:mm:ss} %green(%-5level) --- [%thread] %cyan(%logger{36}) : %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File是输出的方向通向文件的 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}【%thread】 %-5level %logger{36} - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <!--日志输出路径-->
        <file>C:/code/itheima-data.log</file>
        <!--指定日志文件拆分和压缩规则-->
        <rollingPolicy  class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--通过指定压缩文件名称，来确定分割文件方式-->
            <fileNamePattern>C:/code/itheima-data-%d{yyyy-MMdd}.log%i.gz</fileNamePattern>
            <!--文件拆分大小-->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
    </appender>

    <!--
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF, 默认debug
        <root>可以包含零个或多个<appender-ref>元素，标识这个输出位置将会被本日志级别控制。
    -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <!--<appender-ref ref="FILE" />-->
    </root>
</configuration>
```

##### ③ 记录日志

```java
public class LogDemo {

    //1.获取日志对象
    private static final Logger LOGGER = LoggerFactory.getLogger(当前类的类名.class);

    public static void main(String[] args) {
        //2.记录日志
        LOGGER.trace("追踪信息");
        LOGGER.debug("调试信息");
        LOGGER.info("普通信息");
        LOGGER.warn("警告信息");
        LOGGER.error("错误信息");
        //3.记录参数日志
        LOGGER.debug("用户名为:{},密码为:{}", 第1个参数自动放入第1个{}中, 第2个参数自动放入第2个{}中);
        //4.记录异常信息
        LOGGER.debug("说明性信息",异常对象);
    }
}
```



### 3.枚举

```java
概述:
	枚举就是固定的选项,只能在这些选项中选择,不能自定义.
格式:
	public enum 枚举名{
		枚举项1, 枚举项2, 枚举项3;	//枚举可以有多个枚举项
	}
注意:
	1.每个枚举项都是枚举的一个对象.
    2.枚举的使用方式"枚举名.选项"
```



### 4.单元测试

#### 4.1 概述

```
概念: 单元测试可以替换main方法,可以做到每个方法都能独立运行. 一般用于功能测试.
注意:
	1.单元测试的方法不能有参数
	2.单元测试的方法不能有返回值类型
	3.单元测试的方法访问修饰符必须是public
	4:单元测试的方法不能添加static关键字
```

#### 4.2 步骤

##### ① 导入jar包

```
junit-4.12.jar
hamcrest-core-1.3.jar
```

##### ② 添加@Test注解

```java
public class TestDemo {
    @Test
    public void aaa() {
        //输入流->添加@Test注解后,该方法就可以独立运行了
    }
}
```

#### 4.3 其他注解

```properties
@Before:放在方法上,会在@Test测试方法执行之前执行.
@After:放在方法上,会在@Test测试方法执行之后执行.
```



### 5.断言(扩展)

#### 5.1 概述

```
概念: 断言指的是"推断".
注意: 断言是"单元测试"中的一个功能,一般也只用在"单元测试"中.
```

#### 5.2 语法

```java
Assert.assertEquals(预期值,实际值);	//如果"预期值"和"实际值"不一样,则断言失败,则报错
```

