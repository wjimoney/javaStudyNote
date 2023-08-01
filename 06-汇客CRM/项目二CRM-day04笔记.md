---
typora-copy-images-to: img
---

# 项目二CRM-笔记

## 0、开营典礼

### 0.1、分组

- 6人一组：组长1名，组员5名 

- 评分原则：

  - 自行打分：根据任务进度（单个任务10分）
  - **加分：同学分享环节，5-10分（锻炼大家的语言表达能力（技术上））**

### 0.2、学习目标

```asciiarmor
0、和项目一从头到尾的去学习一个项目不同；项目二是模拟大家刚进入一家新公司，从中途介入一个项目的场景
1、学习目标不再是把一个项目所有的代码和功能都掌握
2、模拟企业真实项目开发情况
3、锻炼自己根据需求文档&产品原型&接口文档写代码的能力【重点】
4、了解表设计&接口设计
```



## 1、任务1-准备环境

```json
1、安装办公软件：企业微信，钉钉，飞书等（#忽略）
2、准备开发环境
	（1）安装JDK8（最好保证是1.8）
    （2）安装IDEA 
    （3）安装与配置Maven（资料中提供了本地仓库）
    （4）安装Git(源代码管理)
    （5）安装MySQL（#必须是5.7.x）
```



## 2、任务2-拉取代码

1）这里给大家提供项目git地址，大家**fork到自己的仓库里**

码云地址：https://gitee.com/itcastopen/crm.git

![image-20220529150041891](img/image-20220529150041891.png)

2）在idea中使用git clone下载自己fork的项目。

```asciiarmor
#1、克隆的地址：使用自己fork（fork：相当于把当前仓库内容复制到自己账号下新建一个仓库）的远程仓库
#2、使用idea打开之后：检查Maven配置（maven配置文件目录+本地仓库目录）
#3、打开Module Settings，JDK使用1.8
#4、刷新Maven视图，在父工程huike执行compile命令：看到BUILD SUCCESS
```

<img src="img/image-20220529150143370.png" alt="image-20220529150143370" style="zoom:50%;" /> 





## 3、任务3-阅读产品文档

> 熟悉需求：需求分析说明书（需求规格说明书），产品原型

![image-20220918192052550](img/image-20220918192052550.png)

```
线索 ——》跟进-》商机 ---》 客户（签合同）
```

在线产品文档：https://app.mockplus.cn/s/J75ri9TemI9U

产品在线演示地址：http://huike-crm.itheima.net/#/login

```
30分钟
找两个同学分享
```

### 3.1、背景介绍

```
CRM: 客户关系管理系统
OA: 办公自动化系统
ERP：企业人力资源管理系统
```

汇客CRM系统，主要是为企业销售人员提供服务以下服务：

第一，辅助销售人员对销售线索、商机、客户进行跟进转化，提高转化效率，实现销售线索的价值最大化；

第二，为企业提供自动化营销服务；

第三，对销售业绩、销售趋势进行数据汇总分析；

第四，销售数据统计为销售管理工作提供依据；

第五，为优化公司的业务发展，提供数据支撑。



### 3.2、术语解释

**CRM**

客户关系管理（CRM）是指企业为提高核心竞争力，利用相应的信息技术以及互联网技术协调企业与顾客间在销售、营销和服务上的交流互动，从而提升其管理方式，向客户提供创新式的个性化客户服务的过程。其最终目标是吸引新客户、保留老客户以及将已有客户转为忠实客户，增加市场。



**线索**

销售线索是与客户初次接触获得的原始信息， 通过推广活动获得的电话号码 ，或是外部渠道获得的客户简单信息，然后通过管理和跟进可以转化为商机。



**线索池**

跟进后，没有转化成商机的线索集合，可以理解为线索回收站



**商机**

商机是从意向客户到成交客户的跟进过程，最后的通过签订合同，转变为成交客户。



**公海池**

已跟进，但未成功转化的潜在客户，可以理解为潜在客户的回收站 。



**转派**

系统中用户离职，产生的线索、商机的重新分配任务。



**总结**

```
1) 线索---- 通过各种手段 获取的用户信息 (一般不完全信息)
2) 伪线索------------ 无效信息
3) 商机用户 潜在客户(有一定概率签单的客户)
4) 商机--公海---> 无效客户
5) 合同--客户(正式签合同的)
```



### 3.3、整体流程

![img](img/u9.png)



## 4、任务4-运行后端代码

### 4.1 、运行准备

1）导入sql文件：在MySQL客户端中执行huike-data.sql  

2）修改application-druid.yml （**将mysql密码改成自己的**）

```asciiarmor
小技巧：在idea中快速双击shift，搜索application-druid.yml 
```

3)   启动本机的redis-server(host:127.0.0.1)

```asciiarmor
如果设置了密码，需要在application.yml中自行配置
```



### 4.2、项目启动

启动huike-admin工程的HuiKeApplication.java的main方法

```asciiarmor
小技巧：在idea中快速双击shift，搜索HuiKeApplication
```



## 5、任务5-运行前端代码

1、huike-admin模块的application.yml：**修改server.port=8094**， http://localhost:8094/

2、将nginx-crm.rar解压到**没有中文和空格的目录**，然后双击nginx.exe

```asciiarmor
启动之前，关闭之前启动的nginx
使用管理员打开cmd：taskkill /f /im nginx.exe
```

3、浏览器直接访问localhost（**保证已在idea中启动了huike-admin**）：http://localhost:88



## 6、任务6-架构分析与技术调研

### 6.1、阅读代码

#### 6.1.1、代码结构&数据库

![image-20220530101749984](img/image-20220530101749984.png)

**代码于功能对应关系**

![image-20220530102616752](img/image-20220530102616752.png)

父工程

```
public class A extends B, C{}
Java单继承，不能同时继承多个类
```

```xml
<!-- 依赖声明 -->
<dependencyManagement>
    <dependencies>
        <!-- SpringBoot的依赖配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.2.13.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        
        
        
    </dependencies>
</dependencyManagement>
```

>  <scope>import</scope>
>  用来解决maven单继承的问题
>  Maven的继承和Java的继承一样，只能单继承，无法实现多继承，你是否想过我们如果要继承多个父模块的时候应该怎么做呢？或许你会想只往一个父模块中添加jar包依赖，只继承一个父模块就可以了，但是这样的话所有的jar包都混合在一起了，jar包分类就不在清晰了，其实我们可以用另外一种方式实现多模块继承，这个方法就是使用

> <type>pom</type>：type标签的默认值是jar，代表我们依赖导入的是一个jar包，现在我们设置成了pom，说明导入的是一个父模块，后面的scope标签中的值import代表把父模块中的jar包导入进来。
>
> 不过需要注意的是：
>
> ```
> <type>pom</type>
> <scope>import</scope>
> ```
>
> 这种方式只能用在<dependencyManagement></dependencyManagement>中

**数据表结构**

```
sys: system系统表
tb: table业务表
```

![image-20220530103117342](img/image-20220530103117342.png)   



#### 6.1.2、表现层数据封装

```
Result：code（状态码，标识成功，失败），msg（消息，给用户提示），data（数据）
{
	code:"",
	msg: "",
	data: "",
	url: ""
}
```

结果是如果返回给前端的

```properties
系统提供了 两个封装类 返回数据
   AjaxResult:  主要是用来封装一些非分页对象的 
   TableDataInfo:  主要是用来封装一些分页对象的,TableDataInfo包含了total 总条数 rows列表数据 code 状态码 msg消息内容 params(其他参数)
```

##### 1、简单数据返回


```java
package com.huike.common.core.domain;

public class AjaxResult extends HashMap<String, Object> {
    
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";
    
    //private String url;
    //private String url2;
    //private String url3;
    //private String url4;
    //private String url5;
    
    // put("url", "http://xxxx")
    
    //...省略
}
```

```json
{
	"code": "",
    "msg": "",
    "data": "",
    "url": ""
}
```



##### 2、分页数据返回

TableDataInfo：分页查询

```java
public class TableDataInfo implements Serializable{
	
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    private Map<String,Object> params;
    
    //...省略
}
```



#### 6.1.3、全局异常处理

```
AOP: 切面aspect, 共性功能：advice（通知）
ControllerAdvice
```

在**huike-framework**模块下有一个web目录，下面定义了一个全局异常处理的处理器：GlobalExceptionHandler

```asciiarmor
ctrl+shift+f（可能其他软件占用了这个快捷键）: 搜索ControllerAdvice
或者使用idea菜单：Edit -> Find -> Find in path
```

```java
package com.huike.framework.web.exception;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e) {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e) {
        if (StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }
}
```



#### 6.1.4、分页实现

> 项目中分页功能是如何实现的?

##### 1、spring-boot整合分页插件

利用**自动配置**：引入对应的jar包就会自动产生一些对象（META-INF/spring.factories）

```
PageHelperAutoConfiguration
```

```xml
<!-- common pagehelper 分页插件 -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
</dependency>
```

```yaml
# PageHelper分页插件
pagehelper: 
  helperDialect: mysql #oracle, sql server
  reasonable: true #合理化建议,page=-1->page=1; page=1000000 -> page= 最后一页
```

```
在需要 分页的方法前 增加一行代码：自动添加limit 10，10；
PageHelper.startPage(pageNum, pageSize, orderBy); 即可
```

##### 2、 项目中分页插件的使用

 TbClueController

```java
/**
 * 查询线索池
 * @param tbClue
 * @return
 */
@GetMapping("/pool")
//url: /pool?pageNum=10&pageSize=5
public TableDataInfo pool(TbClue tbClue) {
   startPage();
   List<TbClue> list = tbClueService.selectTbCluePool(tbClue);
   return getDataTable(list);
}
```

项目中在 BaseController 中单独封装了一个  startPage() 方法

```
1) 自动从参数中 获取页码 参数必须是 pageNum
1) 自动从参数中 获取每页条数 参数必须是 pageSize
3) 自动从参数中 获取排序字段 参数必须是 orderByColumn
4) 自动从参数中 获取排序 正序还是倒叙 ,参数必须是  isAsc 值 为asc 或desc
```

```java
/**
 * 设置请求分页数据
 */
protected void startPage() {
    //1.从request对象中获取前端传递分页参数
    PageDomain pageDomain = TableSupport.buildPageRequest();
    Integer pageNum = pageDomain.getPageNum();
    Integer pageSize = pageDomain.getPageSize();
    if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        //2.开启pagehelper分页功能
        PageHelper.startPage(pageNum, pageSize, orderBy);
    }
}
```

##### 3、 项目中的分页方式2 (不推荐)

```json
项目中还有一个  不使用分页插件的分页方法 getDataTablePage()
#这个方法是 查询数据库全部数据,然后在内存中分页, 效率极低
```



#### 6.1.5、编写简单的CRUD【复习】【重点】

在huike-mybatis-review模块中完成简单的CRUD



### 6.2、分析功能

```json
同一个组：必须完成3个，分工合作
验证码：1个人
JWT: 3个人
操作日志（sys_oper_log表）：2个人
机动：1人，根据情况安排到JWT或者日志
#总共时间：1个小时
```

#### 6.2.1、验证码

```
1、浏览器F12调试模式：/captchaImage
2、根据内容查找：ctrl+shift+f（菜单：Edit -> Find -> Find in Path(Find in Files)）
很多输入法默认使用了这个一个快捷键：切换简繁体
```

##### 1、后端实现代码

CaptchaController

```java
package com.huike.web.controller.common;

/**
 * 验证码操作处理
 */
//@Api("验证码")
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    
    // 验证码类型
    @Value("${huike.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException{
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
```

##### 2、验证码生成流程图

![](img/20220313115319.png)

##### 3、 具体分析

具体获取验证码流程：

###### 3.1、起点

前端触发调用验证码接口获取验证码/captchaImage。

###### 3.2、后端入口

由于后端提供了具体的controller，并且提供了对应的验证码接口，后端能够被触发

```java
/**
 * 生成验证码
 */
@GetMapping("/captchaImage")
public AjaxResult getCode(HttpServletResponse response) throws IOException{
```

###### 3.3、 验证码生成逻辑

验证码生成，使用了谷歌开源的工具包kaptcha

```xml
<!-- 验证码 -->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
</dependency>
```

生成的数字验证码的代码，并不需要我们过多关心，我们只需要调用api即可，kaptcha可以生成数字或字符串的不用的验证码，系统中使用配置文件的方式进行配置

```
第一种验证码char：32sd
第二种验证码math：7 * 3 = 21
```

通过@Value注解的方式读取application.yml里的配置信息

```java
@Value("${huike.captchaType}")
private String captchaType;
```

那么这里读取的配置就是配置文件里配置的字符串math。

![](img/26fe64d73a1e2e53848ca51a55b0ff2.png)

第一遍看代码的时候，遇到自己不明白的地方先跳过

```java
@GetMapping("/captchaImage")
public AjaxResult getCode(HttpServletResponse response) throws IOException{
    // 保存验证码信息
    String uuid = IdUtils.simpleUUID();
    String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

    String capStr = null, code = null;
    BufferedImage image = null;
```

> 这里初始化了一堆东西，是什么，有什么用，现在知不知道，不知道？
>
> 怎么办？先跳过，反正就是生成一个一堆字符串呗，也不知道干了什么
>
> 未来读代码的时候也是这样遇到不明白的部分就先跳过，等用到的时候再来研究

那么代码会走哪部分？根据配置文件里配置的字符串math得知：一定是上半部分 math的部分

```java
if ("math".equals(captchaType)){
	xxx
}else if ("char".equals(captchaType)){
	xxx
}
```

在这里面又做了什么

```java
String capText = captchaProducerMath.createText();
capStr = capText.substring(0, capText.lastIndexOf("@"));
code = capText.substring(capText.lastIndexOf("@") + 1);
image = captchaProducerMath.createImage(capStr);
```

这里就有几个问题了captchaProducerMath是什么？

capStr，code，image都是什么，一开始定义了什么？

substing是截取字符串，为什么要截取字符串？

我们回头来看定义的部分

```java
String capStr = null, code = null;
BufferedImage image = null;
if ("math".equals(captchaType)){
      String capText = captchaProducerMath.createText();
      capStr = capText.substring(0, capText.lastIndexOf("@"));
      code = capText.substring(capText.lastIndexOf("@") + 1);
      image = captchaProducerMath.createImage(capStr);
}
```

也就是说capStr，code，image都是提前定义好的，一开始都是null

我们可以打断点debug的方式来看具体都是什么内容，为什么要截取字符串

![](img/847e7372c5c374da99eab284481442c.png)

打上三个断点然后debug启动

前端刷新重新获取验证码，可以看到进入断点

![](img/f01f2cd72c8272dd8745199823553a5.png)

可以看到capText的内容是7*0=?@0

按照@来截取字符串  capStr是7*0=?   code的内容是0

这是什么？code是计算的结果capStr是公式

这个过程具体是怎么生成的我们需要管吗 这都是 String capText = captchaProducerMath.createText();替我们生成好的 而 captchaProducerMath 正是调用了google的kaptcha的工具包所以具体它是如何生成的我们并不需要关心

我们通过截取字符串的形式截取出验证码的结果code和验证码的公式capStr

但是我们返回给前端的不是一个公式，而是一个图片验证码，这个还需要调用captchaProducerMath的createImage方法将公式传入，工具包会给我们生成一个图片，通过BufferedImage来接收

![](img/da60d21f51efc15342cb3032343ddd3.png)

将其生成具体的图片后保存在内存中

###### 3.4、 验证码如何返回给前端

现在继续思考，我们在内存中有了图片了，

1）我不能把内存中的信息返回给前端，我应该怎么将图片返回给前端呢

2）现在后端生成了验证码的结果，内存中也存储了图片了，后续将图片返回给前端了，在登录的时候如何比对这个验证码的结果呢？ 比如现在张三获取了验证码1 ，李四获取了验证码2 ，这两个人都拿到了验证码，我如何区分这两个人呢？总不能张三提交的验证码和李四生成的验证码进行对比把

3）我们用过别人的系统，验证码都有超时时间，现在还没看到超时时间如何设置

带着这两个问题我们继续来看代码

```java
        //往redis里存了一堆东西
redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
```

```java
// 转换流信息写出
FastByteArrayOutputStream os = new FastByteArrayOutputStream();
try
{
    ImageIO.write(image, "jpg", os);
}
catch (IOException e)
{
    return AjaxResult.error(e.getMessage());
}
```

操作数据流，不知道做了什么 但是看到一个jpg似乎是个图片

```java
AjaxResult ajax = AjaxResult.success();
ajax.put("uuid", uuid);
ajax.put("img", Base64.encode(os.toByteArray()));
return ajax;
```

这里我知道 构建了一个结果集AjaxResult对象返回 并往这个对象里设置了一些属性 uuid 和 img不知道是什么

我们一个一个来分析

```java
redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
```

redisCache.setCacheObject是什么意思往redis里添加属性，redis里添加属性一般都是key,value形式的数据

那么verifyKey就是redis的key，

code在redis中是value ，存的是验证码的值

那么我们回头来看verifyKey是怎么生成的

```java
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
```

String uuid = IdUtils.simpleUUID();

这就说明这是一个简易的id生成工具类，有兴趣的同学可以了解一下什么是uuid

然后定义了一个verifyKey是一个常量+刚刚生成的id组合而来

然后作为一个redis的key存入到redis中 

存储redis的时候还设置了一个redis的超时时间

![](img/59ca85bbe5cd10fbd709fbd9433a48c.png)

这样我们就往redis里存了验证码的结果

具体怎么存的呢：定义了一个id作为Key,value是验证码的值，并且设置了一个过期时间是2分钟

这样有什么意义呢？先不管，反正先这样往redis里存储了一份

然后继续看代码

```java
		// 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }
```

定义了一个outputStream这是一个输出流

然后将图片里的信息写入到输出流里，那么这个输出流怎么用不知道

继续看代码

```java
		AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
```

在构建结果集的时候这里使用了Base64转码，os是输出流，将输出流里的信息进行base64位转码这是要做什么

这是一种文件的传输方式，后端用base64的方式进行转码，那么前端也可以用base64的方式转回来，我们给前端传的是图片，前端转回来也是图片

那么后端图片是如何传递给前端的呢？将图片先通过工具构建出来在内存中，并将其通过base64进行转码，然后通过包装类返回给前端

然后我们来看这个包装类里还有什么，将我们一开始生成的一个id也封装进去了，尝试思考，为什么要有这个id，有什么用？ 当然如果想不明白也没有关系，我们可以先跳过这个问题，但是大概的关于验证码生成这部分的逻辑就已经全部讲完了，我们总结一下

##### 4、 总结

步骤：

​	1.前端调用接口

​	2.后端读取配置文件(math, char)，基于配置文件里的信息生成验证码

​	3.生成了验证码以后，将验证码的结果保存在redis中，并设置超时时间2分钟，其中key为一个id，值为验证码的值

​	4.封装结果集返回前端，返回我们生成的一个id，和base64转码后的图片



#### 6.2.2、JWT

```
登录成功：登录信息存储到session(之前实现方式)
```

HTTP是无状态协议，一个人多次发送请求，服务器并不知道是不是同一个。



##### 1、什么是JWT

json web token，通过数字签名的方式，以json为载体，在不同的服务之间安全的传输信息的一种技术



##### 2、JWT的作用

一般使用在授权认证的过程中：

1. 一旦用户登录，后端返回一个token给前端，相当于后端给了前端返回了一个授权码
2. 之后前端向后端发送的**每一个请求都需要包含这个token**，后端在执行方法前会校验这个token（安全校验），校验通过才执行具体的业务逻辑。



##### 3、JWT的组成

![jwt](img/jwt.png)

由Header（头信息），PayLoad （载荷）,  signature（签名）三个部分组成：

- Header头信息主要声明加密算法：通常直接使用 HMAC HS256这样的算法


```json
{
  "typ":"jwt",
  "alg":"HS256" //加密算法
}
```

然后将头信息进行base64编码（该加密是可以对称解密的),构成了第一部分.

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
```

- PayLoad(载荷)：保存一些当前用户的**非敏感数据**，比如用户ID，昵称等


```json
{
  "username":"zhangsan",
  "name":"张三",
  ...
}
```

对其进行base64加密（编码），得到Jwt的第二部分。

```
eyJ1c2VybmFtZSI6InpoYW5nc2FuIiwibmFtZSI6IuW8oOS4iSIsImFnZSI6MTgsInNleCI6IuWlsyIsImV4cCI6MTY0NzE0NTA1MSwianRpIjoiMTIxMjEyMTIxMiJ9
```

- Signature 签证信息，这个签证信息由三部分组成（由加密后的Header，加密后的PayLoad，加密后的签名三部分组成）
  - header (base64编码后的)
  - payload (base64后的)
  - secret（保存在服务端，不能泄漏）

base64加密后的header和base64加密后的payload使用`.`连接组成的字符串，然后通过header中声明的加密方式进行加盐加密，然后就构成了jwt的第三部分，每个部分直接使用"."来进行拼接

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InpoYW5nc2FuIiwibmFtZSI6IuW8oOS4iSIsImFnZSI6MTgsInNleCI6IuWlsyIsImV4cCI6MTY0NzE0NTA1MSwianRpIjoiMTIxMjEyMTIxMiJ9.5tmHCpcsS_VuZ2_z5Rydf2OpsviBGwB-fJE5aS7gKqE
```



##### 4、如何使用JWT

引入Pom依赖：注：**使用的jdk1.8版本 高版本会报缺少jar包**

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <!-- java jwt -->
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

**jwt生成token：**

调用api封装header，payload，signature这三部分信息：

1什么一个全局的签名即我们的秘钥

```java
String signature = "itheima";
```

2.获取jwt的构造器

3.封装header，payload，signature这三部分属性

4.调用compact方法帮我们生成token

```java
//定义签名要用的密钥，注意安全不能泄漏
String signature = "itheima";

//使用JWT自带的构造器构造一个jwt
JwtBuilder builder = Jwts.builder();
//使用构造器里的方法封装属性
String token = builder.
    //封装header属性
    setHeaderParam("typ","JWT").
    setHeaderParam("alg", "HS256")
    //封装payload里的信息 使用claim方法
    .claim("username", "zhangsan")
    .claim("name", "张三")
    .claim("age", 18)
    .claim("sex", "女")
    //在payLoad中设置一个超时时间  秒   分 时
    .setExpiration(new Date(System.currentTimeMillis()+Long.valueOf(1000 * 60 * 60 * 1)))
    .setId("1212121212")
    //构造signature部分
    .signWith(SignatureAlgorithm.HS256, signature)
    //构造我们的签名 调用compact方法
    .compact();
System.out.println(token);
```

5.解密

获取解密器

解密器需要获取我们本地的秘钥 signature

将生成的token进行解密，拿到一个Claims

核心是获取payLoad里的用户信息调用getBody方法获取payLoad

获取payLoad里的参数

```java
//解密
JwtParser parser = Jwts.parser();
//如果伪造的token或者篡改了其中的内容，解析都会出异常（token过期）
Jws<Claims> claimsJws = parser.setSigningKey(signature).parseClaimsJws(token);
Claims body = claimsJws.getBody();

//获取性别
System.out.println(body.get("sex"));
//获取用户名
System.out.println(body.get("username"));
//获取姓名
System.out.println(body.get("name"));
//获取id
System.out.println(body.getId());

//获取有效期-截止时间
System.out.println(body.getSubject());
System.out.println(body.getExpiration());
```



##### 5、系统中如何使用JWT

```
全局搜索ctrl+shift+f 搜索 /login，找到对应的后端接口
```

到此我们已经基本明白了JWT是做什么的了，会怎么使用了，那么我们的CRM系统中是如何使用JWT的呢？

我们已经了解JWT是一个保存用户信息的秘钥，那么应该在**登录的时候使用**

**如何找登录呢？**

浏览器进入登录页面，打开F12 找到对应的接口通过F12看到的调用登录前端传递的参数有哪些

前端传递的参数：

```json
{
    "username":"admin",
    "password":"admin123",
    "code":"8",
    "uuid":"1653e83f3d9f450b97a5c2c8cd88d48d"
}
```

根据前端的请求找到对应的后端代码

全局搜索ctrl+shift+r 搜索 /login，找到对应的后端接口

在admin工程下system目录下的SysLoginController

```java
  /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
```

从controller层可以看出在loginService的login方法返回了一个token然后将其利用包装类返回给了前端

我们来看具体的逻辑，看这个token是如何生成的。

SysLoginService

```java
/**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            System.out.println("username "+username+" -----password "+password);
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
```

这里面的逻辑非常的复杂，我们就关心token是如何创建的就行了

我们可以看到tokenService.createToken()方法创建了token，我们聚焦到这个方法上来

```java
return tokenService.createToken(loginUser);
```

TokenService

```java
    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        //存放非敏感信息
        claims.put("username",loginUser.getUsername());
        claims.put("nickName",loginUser.getUser().getNickName());
        claims.put("createTime",loginUser.getUser().getCreateTime());
        return createToken(claims);
    }
	/**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims){
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }
```



##### 6、JWT弊端：两大问题【理解】

1）退出登录

```asciiarmor
1、登录成功之后，把登录用户的ID存入Redis中
2、每次请求过来之后，除了校验token合法和有效期之外，还要去Redis中查询用户是否登录
3、如果用户退出登录，就从Redis中删除用户ID
```



2）自动续期

```asciiarmor
每次请求过来后，除了校验token之外，还需要检查token的过期时间，如果快过期了，刷新token（重新生成token设置新的过期日期和延长Redis中用户ID的有效期）
```



#### 6.2.3、操作日志

> 思路
>
> 先根据日志表名sys_oper_log，找对应的数据层接口ISysOperLogService，再去找谁调用了添加日志方法
> （idea技巧：按着ctrl，点击方法名，会显示谁调用）

```
@Log(title = "菜单管理", businessType = BusinessType.INSERT)
```

##### 1、什么是操作日志

​		任何一个项目都会有一个用户操作日志（也叫行为日志）的模块，它主要用来记录某个用户做了某个操作，当出现操作失败时，通过日志就可以快速的查找是哪个用户在哪个模块出现了错误，以便于开发人员快速定位问题所在。

​		操作日志的功能是记录您在展台中所有的操作的一个记录。它像您的日记本，记录着每一步您操作的时间、内容等。如果您需要查看您的所有操作记录，那么，操作日志就会完全为您展现出来。



##### 2、基于AOP实现

​		使用Spring的AOP来实现记录用户操作，也是推荐的现如今都使用的一种做法。它的优势在于这种记录用户操作的代码独立于其他业务逻辑代码，不仅实现了解耦，而且避免了冗余代码。

###### 2.1 日志存储基本代码

（1）用于存储日志的数据库表: sys_oper_log

<img src="img/image-20220530142519236.png" alt="image-20220530142519236" style="zoom: 50%;" /> 

（2）实体类定义：SysOperLog

（3）数据访问层：SysOperLogMapper mapper，XML文件

（4）业务逻辑层  ISysOperLogService

###### 2.2 自定义注解

```java
package com.huike.common.annotation;

/**
 * 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块 
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}

```



###### 2.3 编写日志处理类

```
AOP切面类：com.huike.framework.aspectj.LogAspect
```

![image-20220525210817805](img/image-20220525210817805.png)

###### 2.4 异步方式存储日志

![image-20220525205949474](img/image-20220525205949474.png)

为了不影响业务方法的执行，存储日志采用**异步方式**存储。

```java
// 保存数据库：采用异步方式（新建一个子线程）来保存日志到数据库中
//new Thread(new Runnable() {
// @Override
// public void run() {
//    //记录日志
// }
//}).start();
//业务层的新增方法：execute(执行）
//1、创建一个定时任务完成日志记录：AsyncFactory.recordOper(operLog)
//2、从线程池里拿出一个新线程执行这个定时任务：execute(执行）
AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
```

（1）异步工厂类：**创建一个定时任务来完成日志记录**

```java
package com.huike.framework.manager.factory;


/**
 * 异步工厂（产生任务用）
 * 
 * 
 */
public class AsyncFactory
{

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
          	    operLog.setOperLocation(
                        AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
```

（2）异步任务管理器

```java
package com.huike.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.huike.common.utils.Threads;
import com.huike.common.utils.spring.SpringUtils;

/**
 * 异步任务管理器
 * 
 * 
 */
public class AsyncManager
{
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 单例模式
     */
    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    public void execute(TimerTask task)
    {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
```



###### 2.5 业务方法调用日志

在业务方法上添加@Log注解即可

```java
@Log(title = "批量捞取", businessType = BusinessType.UPDATE)
@PutMapping("/gain")
public AjaxResult gain(@RequestBody AssignmentVo assignmentVo) {

	//......
}
```



## 8、任务8-BUG分析

### 8.1、现象

http://localhost:88

```json
#使用普通用户（市场专员）登录
用户名：zhangsan
密码：123456
```

线索管理-线索池-线索捞取-提示信息异常：捞取失败！最大保有量(10)，剩余可以捞取-5条线索"  

![image-20220530202321229](img/image-20220530202321229.png)

任务：阅读代码，分析为什么剩余线索数为负数，如何解决这个问题。



### 8.2、思路

​	1.该BUG是在点击线索捞取时出现的，那么线索捞取触发的是哪个后端接口呢？

​			**可以通过F12查看对应的接口，和接口的返回值**： /gain

​	2.根据前端请求的接口地址，找到后端的代码，可以使用全局搜索接口名

​	3.定位到接口名后，分析什么是最大保有量，是写死的吗？

​	4.定位异常信息并分析负数是如何产生的

​	5.与产品经理（老师）沟通，需要告知产品经理产生问题的原因，如果是业务上定义的，想产品经理询问解决方案，如果是技术上的问题，找架构师（老师）沟通，这块的原因

​	

### 8.3、解决方法

```sql
--这是一个显示bug，规则保存在规则表里tb_rule_pool：
--规则表里定义了每个人拥有的线索的上限max_nunmber，因此在我们在捞取的时候，如果超过了上限，不用相减，直接返回0即可：
-- 1、查询项目中的规则：记录每个人最多持有的线索数
SELECT max_nunmber from tb_rule_pool
--type=0线索，type=1商机
WHERE TYPE = 0 

--2 、查询当前用户已经持有的线索数
SELECT COUNT(*) FROM tb_assign_record
WHERE user_id = 2 AND TYPE = 0
```



TbClueServiceImpl.gain(): 

```java
if (asignRecords >= rulePool.getMaxNunmber()) {
    throw new CustomException("捞取失败！最大保有量(" + rulePool.getMaxNunmber() + ")" +
                              "，剩余可以捞取0条线索");
}
```



## 9、任务9-BUG修复

### 9.1 权限管理-用户管理-高级搜索-手机号搜索不可用

![](img/tapd_53702935_base64_1626941937_82.png)

在SysUserMapper.xml的selectUserList中添加按照手机号模糊查询的逻辑：

```xml
<if test="phonenumber != null and phonenumber != ''">
   AND u.phonenumber like concat('%', #{phonenumber}, '%')
</if>
```

### 9.2 商机管理-公海池-创建时间搜索 -没有效果

![](img/tapd_53702935_base64_1626851085_23.png)

1、接受日期参数

TbBusinessController#pool

![image-20220812152607998](img/image-20220812152607998.png)

```java
@PreAuthorize("@ss.hasPermi('business:business:pool')")
@GetMapping("/pool")
public TableDataInfo pool(TbBusiness tbBusiness,HttpServletRequest req){
    startPage();
    if((!StringUtils.isEmpty(req.getParameter("params[beginCreateTime]")))&&
          (!StringUtils.isEmpty(req.getParameter("params[endCreateTime]")))) {
       tbBusiness.setBeginCreateTime(req.getParameter("params[beginCreateTime]"));
       tbBusiness.setEndCreateTime(req.getParameter("params[endCreateTime]"));
    }
    List<TbBusiness> list = tbBusinessService.selectTbBusinessPool(tbBusiness);
    return getDataTable(list);
}
```

2、查询时添加查询条件

> date_format：MySQL中的日期格式化函数
>
> %Y：四位数年
>
> %m：两位数月份
>
> %d：两位数日
>
> ```sql
> -- 获取当前日期
> SELECT NOW();
> 
> -- 格式化成：2022-10-01
> SELECT DATE_FORMAT(NOW(),'%Y-%m-%d');
> ```

TbBusinessMapper.xml: selectTbBusinessPool

```xml
<if test="beginCreateTime != null and endCreateTime != null"> 
and date_format(create_time,'%Y-%m-%d') BETWEEN #{beginCreateTime} AND #{endCreateTime}
</if>
```



### 9.3 商机管理-商机状态搜索框不可用 

![](img/tapd_53702935_base64_1626846729_40.png)

在TbBusinessMapper.xml的selectTbBusinessList中添加按照状态查询条件：

```xml
<if test="status != null and status != ''"> and b.status = #{status}</if>
<if test="status == null or status == ''"> and b.status in ('1','2')</if>
```



### 9.4 线索管理- 线索id和手机号应该支持模糊搜索

![](img/tapd_53702935_base64_1626835557_52.png)

在TbClueMapper.xml的selectTbClueList中添加id和手机号的模糊查询：

```xml
<if test="id != null  and id != ''"> and clue.id like concat('%', #{id}, '%')</if>
<if test="phone != null  and phone != ''"> and clue.phone like concat('%', #{phone}, '%')</if>
```



### 9.5 线索管理-添加线索-活动信息-应该只展示 正在活动时间内的活动 

BUG解释：

活动有一个有效时间，当我们创建活动的时候需要有一个活动开始时间和结束时间

比如说天猫双11，这是一个活动，在11月11号到11月20这段时间内平台提供福利，这个时间段就是活动时间，即在这段时间范围内就是活动的活动时间

![image-20220812155049530](img/image-20220812155049530.png)

bug显示的内容是：添加线索-活动信息-应该只展示 正在活动时间内的活动

我在2020-05-05的时候点击添加线索的时候，查看的活动应该有去年双11的活动吗？

因为去年双11的活动已经过期了，即活动已经结束了，所以不应该在下来列表中进行展示

这里比较的是当前的时间 与活动的开始时间和活动的结束时间进行比较

![](img/tapd_53702935_base64_1626934705_16.png)

![](img/tapd_53702935_base64_1626934783_59.png)

要求：改以上bug的时候不要对其他模块产生影响

**解决思路：**只显示当前时间可用的活动

1、在TbActivityController中：构建活动类的时候初始化一个当前时间

> 通过TbActivity父类BaseEntity中Map类型的params参数保存当前日期

```java
/**
 * 获取渠道下活动
 * @param channel
 * @return
 */
@GetMapping("/listselect/{channel}")
public AjaxResult list(@PathVariable("channel")  String channel)
{
    TbActivity tbActivity =new TbActivity();
    tbActivity.setChannel(channel);
    tbActivity.setStatus("2");
    
    //完整写法：
    Map params = new HashMap();
    params.put("now", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    tbActivity.setParams(params); //#{params.now}
    
    return AjaxResult.success(tbActivityService.selectTbActivityList(tbActivity));
}
```

2、在TbActivityMapper.xml的selectTbActivityList中添加判断条件：加上日期的比较，判断是否在有效期范围内

```xml
<!-- 处理当前时间 -->
<if test="params.now != null and params.now != ''">
    and #{params.now} between begin_time and end_time
</if>
```





## 10、任务10-优化代码

### 10.1 任务描述

优化添加活动部分代码：TbActivityServiceImpl.insertTbActivity

先分析原有代码存在的问题，然后找到最优方案！ **完成后进行分享**。

<img src="img/image-20220530195427001.png" alt="image-20220530195427001" style="zoom: 50%;" /> 

### 10.2 分析代码

代码中共有3次耗时操作：

- **生成随机活动码，到Redis中查询是否已存在【需要优化】**

  ```java
  private String getCode(){
      //随机8位编码
      String code= StringUtils.getRandom(8);
      //活动编号校验
      Set<String> codeSets =  redisCache.getCacheSet(Constants.ACT_CODE_KEY);
      if(codeSets.contains(code)){
          return getCode(); //递归
      }
      return code;
  }
  ```

- **保存活动信息到数据库【无法优化】**

  ```java
  //insert into ...
  int rows= tbActivityMapper.insertTbActivity(tbActivity);
  ```

- **加载所有活动的code码到Redis中**

  ```
  loadAllActivityCode();
  ```

  ```java
  /**
   * 加载活动编号到缓存中
   */
  public void loadAllActivityCode() {
      List<String> codeList= tbActivityMapper.selectAllCode();
      Set<String> set= new HashSet<>(codeList);
      redisCache.setCacheSet(Constants.ACT_CODE_KEY, set);
  }
  ```

### 10.3 解决思路

咱们只需要找一个不会重复的字符串作为code即可：

- 使用UUID

  ```
  1、项目中有个生成UUID的工具类：UUIDUtils
  2、JDK自带也有：UUID.randomUUID().toString().replace("-", "")
  94f0d3e2-48b9-4eab-95b4-9b7d9c380416：字符比较长，检查数据库中字段的类型(varchar)和长度
  ```

- 使用雪花算法（工具类：网上搜索直接使用）

### 10.4 完成优化

1、使用UUIDUtils替换获取活动Code部分方法：

```java
/**
 * 新增活动管理
 * 
 * @param tbActivity 活动管理
 * @return 结果
 */
@Override
@Transactional
public int insertTbActivity(TbActivity tbActivity){
    tbActivity.setCreateTime(DateUtils.getNowDate());
    tbActivity.setCode(UUIDUtils.getUUID());
    //tbActivity.setCode(UUID.randomUUID().toString().replace("-",""));
    tbActivity.setStatus("2");
    int rows= tbActivityMapper.insertTbActivity(tbActivity);
    
    //loadAllActivityCode();
    return rows;
}
```

2、删除所有对loadAllActivityCode方法的调用：HuiKeApplication

```asciiarmor
技巧：在方法定义上按着ctrl+鼠标左键，就会显示所有调用这个方法的代码
CommandLineRunner：当项目启动后要做一些初始化数据的任务时，可以实现此接口在run()方法进行
利用这个方法：启动后将热门的数据直接存储到Redis中（缓存预热）
```

```java
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableScheduling
//public class HuiKeApplication implements CommandLineRunner {
public class HuiKeApplication {

    public static void main(String[] args){
        SpringApplication.run(HuiKeApplication.class, args);
    }
    
    //@Override
    //public void run(String... args)  {
    //    try{
    //        //加载所有活动code到缓存
    //        activityService.loadAllActivityCode();
    //    }catch (Exception ex){
    //        ex.printStackTrace();
    //    }
    //}
}
```

  

## 11、任务11-按接口开发今日简报

```sql
--熟悉线索表：
SELECT * FROM tb_clue

-- 分配记录表：检查线索分配给谁了
SELECT * FROM tb_assign_record 
WHERE assign_id = 9790 AND TYPE=0 AND latest = 1

--商机表
SELECT * FROM tb_business

-- 检查商机分配给谁了
SELECT * FROM tb_assign_record 
WHERE assign_id = 3473 AND TYPE=1 AND latest = 1
```



### 11.1 任务描述

![image-20220531170803973](img/image-20220531170803973.png)



### 11.2 接口定义

需求：今日简报部分数据，当**前用户在当天的线索数量，商机数量，合同数量，成交的金额**

> 当前用户的线索 商机 合同 成交金额都不需要考虑状态只要是今日做的哪怕是回收了删除了 都要考虑
>
> 以便当前销售人员进行复盘分析工作，用来分析今天操作了多少线索，商机，合同等
>
> 线索数量、商机数量、合同数量、最终成交的合同金额

接口名：/index/getTodayInfo/

​	请求方式：

​		get

​	前端传入参数：

​		无

返回前端数据样例: AjaxResult

```json
{
    "msg":"操作成功",
    "code":200,
    "data":{ //IndexTodayInfoVO
        "todayCluesNum":0, //今日线索数目
        "todayBusinessNum":0,//今日商机数目
        "todayContractNum":1,//今日合同数目
        "todaySalesAmount":0 //今日销售金额
    }
}
```



### 11.3 代码实现

> 2小时实现，可以小组讨论

#### 1、定义返回结果【已提供】

根据前端需要的结果集创建VO对象IndexTodayInfoVO

> VO: view object，一般用于和前端进行交互

```java
import lombok.Data;

@Data
public class IndexTodayInfoVO {
    //Integer默认null，int=0
    private Integer todayCluesNum=0;  //今日线索数目
    private Integer todayBusinessNum=0;  //今日商机数目
    private Integer todayContractNum=0;  //今日合同数目
    private Double todaySalesAmount=0.0;  //今日销售金额
}
```

#### 2、编写SQL

##### 2.1、今日线索

查询数据库中所有的线索数目线索数目怎么查

```sql
SELECT COUNT(id) AS todayCluesNum FROM tb_clue 
```

> 首先思考，如何判断一个线索最终归属人是谁

由于系统中存在分配的逻辑，**一个线索可能被分配到一个人，然后又继续分配**，这里我们要判断线索的最终归属人就要去找分配表（tb_assign_record），这张表的特点是

assign_id 是关联id，它既可以线索id也可以是商机id，它的这个线索id和商机id的状态主要是由type字段来区分的

```json
type=0 标识这条技术是一条#线索
type=1 则这是一条#商机数据
#其中用了一个latest来标识是否为最后一个分配到的人
```

查询该用户下所有的线索数目：

```sql
SELECT
	count(*) 
FROM
	`tb_assign_record` 
WHERE
	user_name = 'zhangsan' 
	-- 最后一次分配到的人
	AND latest = 1 
	-- 只查询线索
	AND `type` = 0
	-- 查询今日分配的
	AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
```

> 思考：如果是测试数据库，数据会比较乱，因此需要判断下从tb_assign_record表中线索是否还存在

这个时候又有一个问题了，需要对比的是分配时间表里的创建时间，因为领导把任务分配给了具体的业务员的时候，这个任务就相当于分配下去了，就算作他今天的任务，所以在查询今日简报的时候，就应该被统计到

```sql
SELECT COUNT(id)
		FROM `tb_clue`
WHERE id IN
			  (SELECT assign_id FROM `tb_assign_record`
               WHERE user_name = 'admin'
               AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
               AND latest = 1
               AND `type` = 0)
```



##### 2.2、今日商机

```sql
SELECT COUNT(id)
		FROM  `tb_business`
		WHERE id IN
			  ( SELECT
					assign_id
				FROM
					`tb_assign_record`
				WHERE user_name =  'admin'
				  AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
				  AND latest = 1
				  AND `type` = 1 )
```

##### 2.3、今日客户

如果线索转化成商机，商机转换成合同，我们的流程就终止了，这个过程中

合同后续没有流转了，所以通过合同表的create_by来判断合同的最终归属人

```sql
SELECT COUNT(id) AS todayContractNum
		FROM `tb_contract`
		WHERE create_by =  'lisi'
 AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
```

##### 2.4、今日成交金额

```sql
SELECT SUM(contract_order) AS todaySalesAmount
FROM
`tb_contract`
WHERE create_by = 'lisi' AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
```

当我们需要存储小数，并且有精度要求，比如**存储金额时**，通常会考虑使用**DECIMAL**字段类型

声明语法DECIMAL(M, D)，取值范围如下：

```
33.66：
M -> 4：整数+小数 位数
D -> 2：小数 位数
```

- M是最大位数（精度），范围是1到65。可不指定，默认值是10。
- D是小数点右边的位数（小数位）。范围是0到30，并且不能大于M，可不指定，默认值是0。

```sql
  -- 判断是否为null，如果是null，取后边的值
 SELECT IFNULL('test', 0);
 
 -- 类型转换函数：cast
 SELECT CAST('17.600000381469727' as DECIMAL(30, 2));
 
 
SELECT CAST(IFNULL(SUM(contract_order), 0) AS DECIMAL(30, 0) )
		FROM
			`tb_contract`
		WHERE create_by =  'lisi'
		  AND DATE_FORMAT(create_time, '%Y-%m-%d') = '2022-06-03'
```

> CAST函数用于将某种数据类型的表达式显式转换为另一种数据类型。
>
> 语法：CAST (expression AS data_type) 参数说明： 
>
> ```properties
> expression：任何有效的SQL Server表达式。
> AS：用于分隔两个参数，在AS之前的是要处理的数据，在AS之后是要转换的数据类型。
> data_type：目标系统所提供的数据类型
> ```

#### 3、根据sql编写xml

编写对应的xml：ReportMapper.xml

```xml
<select id="getTodayCluesNum" resultType="Integer">
    SELECT COUNT(DISTINCT (id)) AS todayCluesNum
    FROM `tb_clue`
    WHERE id IN
    (SELECT  assign_id FROM `tb_assign_record`
    WHERE user_name =  #{username}
    AND DATE_FORMAT(create_time, '%Y-%m-%d') = #{now}
    AND latest = 1
    AND `type` = 0)
</select>

<select id="getTodayBusinessNum"  resultType="Integer">
    SELECT COUNT(DISTINCT (id)) AS todayBusinessNum
    FROM  `tb_business`
    WHERE id IN
    ( SELECT
    assign_id
    FROM
    `tb_assign_record`
    WHERE user_name =  #{username}
    AND DATE_FORMAT(create_time, '%Y-%m-%d') = #{now}
    AND latest = 1
    AND `type` = 1 )
</select>

<select id="getTodayContractNum"   resultType="Integer">
    SELECT   COUNT(id) AS todayContractNum
    FROM `tb_contract`
    WHERE create_by =  #{username}
    AND DATE_FORMAT(create_time, '%Y-%m-%d') = #{now}
</select>

<select id="getTodaySalesAmount"   resultType="Double">
    SELECT CAST(  SUM(`contract_order`) AS DECIMAL (30, 0) ) AS todaySalesAmount
    FROM
    `tb_contract`
    WHERE create_by =  #{username}
    AND DATE_FORMAT(create_time, '%Y-%m-%d') = #{now}
</select>
```

#### 4、基于xml编写mapper层

ReportMapper.java

```java
/**=========================================今日简报========================================*/
	/**
	 * 首页--今日简报--今日线索数量
	 * @param today  今日
	 * @param username 用户名
	 * @return
	 */
	Integer getTodayCluesNum(@Param("now") String today,@Param("username") String username);

	/**
	 * 首页--今日简报--今日商机数量
	 * @param today  今日
	 * @param username 用户名
	 * @return
	 */
	Integer getTodayBusinessNum(@Param("now") String today,@Param("username") String username);

	/**
	 * 首页--今日简报--今日合同数量
	 * @param today  今日
	 * @param username 用户名
	 * @return
	 */
	Integer getTodayContractNum(@Param("now") String today,@Param("username") String username);

	/**
	 * 首页--今日简报--今日销售金额
	 * @param today  今日
	 * @param username 用户名
	 * @return
	 */
	Double getTodaySalesAmount(@Param("now") String today,@Param("username") String username);
```

#### 5、编写业务层

IReportService.java

```java
/**
     * 获取今日简报数据
     * @param today
     * @return
     */
IndexTodayInfoVO getTodayInfo(String today);
```

ReportServiceImpl.java

```java
/**
     * 获取今日简报
     * @param today 今日
     * @return
     */
    @Override
    public IndexTodayInfoVO getTodayInfo(String today) {
        //1）构建一个空的结果集对象
        IndexTodayInfoVO result = new IndexTodayInfoVO();
        //2 封装结果集属性
        // 2.1 由于查询需要用到用户名 调用工具类获取用户名
        String username = SecurityUtils.getUsername();
        // 2.2 封装第一个属性 今日线索数量
        result.setTodayCluesNum(reportMpper.getTodayCluesNum(today,username));
        // 2.3 封装第二个属性 今日商机数量
        result.setTodayBusinessNum(reportMpper.getTodayBusinessNum(today,username));
        // 2.4 封装第三个属性 今日合同数量
        result.setTodayContractNum(reportMpper.getTodayContractNum(today,username));
        // 2.5 封装第四个属性 今日合同金额
        result.setTodaySalesAmount(reportMpper.getTodaySalesAmount(today,username));
        //3属性封装完成后，返回结果集
        return result;
    }
```

#### 6、controller层接收参数

IndexController主要是处理前端的交互，传入参数是空，返回值是包装类，用包装类封装我们的VO结果集

```java
/**
 * 首页--获取今日简报数据
 * @return
 */
@GetMapping("/getTodayInfo")
public AjaxResult getTodayInfo(){
    return AjaxResult.success(reportService.getTodayInfo(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
}
```

## 12、任务12-前后端联调

### 12.1 任务描述

任务：前后端联调

启动Nginx通过前端对任务11完成的结果进行前后端联调测试。

![image-20220531170803973](img/image-20220531170803973.png)



### 12.2 验证数据

- 用admin登录，将未分配的线索分配给lifeng

  ![image-20220814114807504](img/image-20220814114807504.png)

- 用lifeng登录，查看首页今日简报中：今日新增线索是否为1

  <img src="img/image-20220814115409296.png" alt="image-20220814115409296" style="zoom: 50%;" /> 

> 今日新增商机的测试也可以按照上述方式进行
>
> 今日新增合同和销售额：目前项目中还没有实现合同上传，因此无法将商机转为合同，暂时可直接修改合同表tb_contract中之前的数据为今天日期



## 13、任务13- 我的任务清单

### 13.1 任务描述

**首页--待办数据统计接口**：根据设计的接口设计接口文档

需求：查询当前用户待根进的线索，商机，和待分配的线索和商机

对于销售主管来说，主要的工作是分配线索和商机，对于销售人员来说主要是跟进线索和商机

在首页提供一个入口用来展示还有多少任务是没有完成的

![](img/d0a849471973f2bb16073ea332fee76.png)

​	

### 13.2 接口定义

接口名 /index/getTodoInfo

​	请求方式 get请求

​	参数列表	

​			传入参数：/index/getTodoInfo?beginCreateTime=2022-07-05&endCreateTime=2023-05-05

​					beginCreateTime 开始时间

​					endCreateTime 	结束时间

​			返回值：

```json
{
    "msg":"操作成功",
    "code":200,
    "data":{
        "tofollowedCluesNum":0, //待跟进线索数目
        "tofollowedBusinessNum":0,//待跟进商机数目
        "toallocatedCluesNum":1,//待分配线索数目
        "toallocatedBusinessNum":0 //待分配商机数目
    }
}
```

​			

### 13.3 代码实现

#### 	1. 阅读接口定义

#### 2. 构建返回对象【已提供】

```java
package com.huike.report.domain.vo;


import lombok.Data;

/**
 * 首页--今日待办--返回前端VO类
 */
@Data
public class IndexTodoInfoVO {

    private Integer tofollowedCluesNum=0;  //待跟进线索数目
    private Integer tofollowedBusinessNum=0;  //待跟进商机数目
    private Integer toallocatedCluesNum=0;  //待分配线索数目
    private Integer toallocatedBusinessNum=0;  //待分配商机数目

}
```

#### 3. 基于需求编写sql【重要】

按照开始时间和结束时间查询一段时间范围内（开始时间和结束时间）的基本数据

**需求：查询当前用户在选定范围内的待跟进的线索，商机，和待分配的线索和商机**

> 对于销售主管来说，主要的工作是分配线索和商机
>
> 对于销售人员来说主要是跟进线索和商机

这是我们的需求，我们要查询出对应的待跟进，待分配的线索和商机

##### 3.1 查询待跟进线索

查询所有的线索

```sql
select * from tb_clue
```

查询所有的待跟进线索：待跟进这个状态从哪里进行获取？

业务对象的状态是保存在实体类中的枚举类中：TbClue.StatusType

<img src="img/image-20220531175810358.png" alt="image-20220531175810358" style="zoom:50%;" /> 

线索的状态可以看到对应的待跟进，**定义了待跟进状态码对应的是1**

```asciiarmor
待跟进：也就是说已经将线索分配给某个市场专员了
因此：待跟进 == 已分配(status=1)
```

那么对应的待跟进的sql语句就应该按照枚举类里定义的内容来进行判断

```sql
SELECT * FROM tb_clue WHERE `status` = 1
```

接下来就是判断用户（查询当前用户待跟进的所有线索）

如果要判断这个线索是某一个人的需要关联到分配表

tb_assign_record  其中的用户名是对应的用户名：

```sql
SELECT * FROM 
	`tb_clue`
WHERE id IN
(
    SELECT assign_id
    FROM `tb_assign_record`
    --如果是最后一次分配 latest是1 ，如果是线索type为1
    WHERE user_name = 'zhangsan' AND latest = 1 AND `type` = 0
) 
-- status=1，代表线索为待跟进状态
AND `status` = 1
```

接下来需要考虑的是时间范围：**使用between关键字**

```sql
SELECT
	count( id ) 
FROM
	`tb_clue` 
WHERE
	id IN (
	SELECT
		assign_id 
	FROM
		`tb_assign_record` 
	WHERE
		user_name = 'zhangsan' 
		AND DATE_FORMAT(create_time, '%Y-%m-%d' ) BETWEEN '2021-05-05' AND '2023-12-05' 
		AND latest = 1 
		AND `type` = 0 
	) 
	AND `status` = 1
```



##### 3.2 查询待跟进商机数

```sql
SELECT 
	count(id)
FROM 
	`tb_business`
WHERE id 
	IN(
        SELECT assign_id FROM `tb_assign_record`
        WHERE user_name = 'lisi'
        AND DATE_FORMAT(create_time, '%Y-%m-%d') BETWEEN '2021-05-05' AND '2023-12-05'
        AND latest = 1
        AND `type` = 1
	  )
  AND `status` = 1
```



##### 3.3 查询待分配线索数

> 思路：如何找到待分配的线索或者商机
>
> 1、线索或者商机已经录入到系统中: tb_clue
>
> 2、但是还没有分配给任何人，也就是说分配记录表中没有任何相关的分配信息

对应的待分配的和待跟进的略有区别：就是一个状态的区别，**只是这个状态稍微要绕一下**

1、首先要查询所有的线索

```sql
select * from tb_clue
```

2、确定待分配的

> 什么是待分配？就是当前登录用户新增的线索，但还没有分配出去
>
> 这种就属于待分配线索

说白了就是 **线索表中有这个数据**，但是 **分配表中没有数据的数据**，因此使用`not in`

```sql
    SELECT COUNT(DISTINCT(id))
    FROM `tb_clue`
    WHERE id NOT IN (
        SELECT assign_id FROM `tb_assign_record`
        WHERE 
        `latest` = 1 AND `type` = 0
    )
    AND create_by = 'lifeng' 
	AND DATE_FORMAT(create_time, '%Y-%m-%d') BETWEEN '2021-05-05' AND '2022-06-05'
```

![image-20220814195609782](img/image-20220814195609782.png)

##### 3.4 查询待分配商机数

```sql
SELECT COUNT(DISTINCT(id)) AS toallocatedCluesNum
FROM `tb_business`
WHERE id NOT IN (
    SELECT assign_id
    FROM `tb_assign_record`
    WHERE 
     `latest` = 1 AND 
     `type` = 1
) 
AND create_by = 'zhangsan' 
AND DATE_FORMAT(create_time, '%Y-%m-%d') BETWEEN '2021-05-05' AND '2023-06-05'
```

通过线索管理：将线索转为商机即可（不用自己新建商机）

```
商机如何来的：经过市场专员对线索的跟进，如果有意向就转成商机
```

![image-20220814195721415](img/image-20220814195721415.png)

自己新增商机：

![image-20221015112631578](img/image-20221015112631578.png)

#### 4. 编写Mapper层接口

ReportMapper

```java
/**=========================================待办========================================*/
	/**
	 * 首页--今日待办--待分配线索数量
	 * @param beginCreateTime  开始时间
	 * @param endCreateTime	   结束时间
	 * @param username		   用户名
	 * @return
	 */
	Integer getToallocatedCluesNum(@Param("startTime")String beginCreateTime,
								   @Param("endTime")String endCreateTime,
								   @Param("username")String username);

	/**
	 * 首页--今日待办--待分配商机数量
	 * @param beginCreateTime  开始时间
	 * @param endCreateTime	   结束时间
	 * @param username		   用户名
	 * @return
	 */
	Integer getToallocatedBusinessNum(@Param("startTime")String beginCreateTime,
									  @Param("endTime")String endCreateTime,
									  @Param("username")String username);

	/**
	 * 首页--今日待办--待跟进线索数量
	 * @param beginCreateTime
	 * @param endCreateTime
	 * @param username
	 * @return
	 */
	Integer getTofollowedCluesNum(@Param("startTime")String beginCreateTime,
								  @Param("endTime")String endCreateTime,
								  @Param("username")String username);

	/**
	 * 首页--今日待办--待跟进商机数量
	 * @param beginCreateTime  开始时间
	 * @param endCreateTime	   结束时间
	 * @param username		   用户名
	 * @return
	 */
	Integer getTofollowedBusinessNum(@Param("startTime")String beginCreateTime,
									 @Param("endTime")String endCreateTime,
									 @Param("username")String username);
```

ReportMapper.xml

```xml
<!--待跟进线索-->
<select id="getTofollowedCluesNum"   resultType="Integer">
   SELECT COUNT(DISTINCT (id)) AS tofollowedCluesNum
   FROM `tb_clue`
   WHERE id IN
        (
           SELECT assign_id FROM `tb_assign_record`
           WHERE user_name = #{username}
            AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN #{startTime}
              AND #{endTime}
            AND latest = 1
            AND `type` = 0
        )
     AND `status` = 1
</select>

<!--待跟进商机-->
<select id="getTofollowedBusinessNum"   resultType="Integer">
   SELECT COUNT(DISTINCT(id)) AS tofollowedBusinessNum
   FROM `tb_business`
   WHERE id IN (
      SELECT assign_id FROM `tb_assign_record`
      WHERE user_name = #{username}
        AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN #{startTime}
         AND #{endTime}
        AND latest = 1
        AND `type` = 1
   )
     AND `status` = 1
</select>

<!--待分配线索-->
<select id="getToallocatedCluesNum"   resultType="Integer">
   SELECT COUNT(DISTINCT(id)) AS toallocatedCluesNum
   FROM `tb_clue`
   WHERE id NOT IN (
      SELECT assign_id FROM `tb_assign_record`
      WHERE
        `latest` = 1
        AND
        `type` = 0
   )
     AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN #{startTime}
      AND #{endTime}
</select>

<!--待分配商机-->
<select id="getToallocatedBusinessNum"   resultType="Integer">
   SELECT COUNT(DISTINCT(id)) AS toallocatedBusinessNum
   FROM `tb_business`
   WHERE id NOT IN (
      SELECT assign_id FROM `tb_assign_record`
      WHERE
        `latest` = 1
        AND `type` = 1
   )
     AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN #{startTime}
      AND #{endTime}
</select>
```



#### 5. 编写service层代码

IReportService

```java
    /**
     * 获取待办数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    IndexTodoInfoVO getTodoInfo(String beginCreateTime, String endCreateTime);
```

ReportServiceImpl

```java
/**
     * 获取待办
     * @param beginCreateTime  开始时间
     * @param endCreateTime    结束时间
     * @return
     */
    @Override
    public IndexTodoInfoVO getTodoInfo(String beginCreateTime, String endCreateTime) {
        IndexTodoInfoVO result = new IndexTodoInfoVO();
        //2 封装结果集属性
        // 2.1 由于查询需要用到用户名 调用工具类获取用户名
        String username = SecurityUtils.getUsername();
        // 2.2 封装第一个属性 待分配线索数量
        result.setToallocatedCluesNum(reportMpper.getToallocatedCluesNum(beginCreateTime,endCreateTime,username));
        // 2.3 封装第二个属性 待分配商机数量
        result.setToallocatedBusinessNum(reportMpper.getToallocatedBusinessNum(beginCreateTime,endCreateTime,username));
        // 2.4 封装第三个属性 待跟进线索数量
        result.setTofollowedCluesNum(reportMpper.getTofollowedCluesNum(beginCreateTime,endCreateTime,username));
        // 2.5 封装第四个属性 待跟进商机
        result.setTofollowedBusinessNum(reportMpper.getTofollowedBusinessNum(beginCreateTime,endCreateTime,username));
        //3属性封装完成后，返回结果集
        return result;
    }
```



#### 6. 编写controller层代码

IndexController

```java
     /**
     * 首页--获取待办数据
     * @return
     */
    @GetMapping("/getTodoInfo")
	//url -> api/index/getTodoInfo?beginCreateTime=2022-05-31&endCreateTime=2022-06-04
    public AjaxResult getTodoInfo(String beginCreateTime, String endCreateTime){
        return AjaxResult.success(reportService.getTodoInfo(beginCreateTime,endCreateTime));
    }
```



## 14、任务14-更高的要求

### 14.1 任务描述

使用 **并发异步编程CompletableFuture** 优化首页基本数据的统计代码。

>  并发异步编程：通俗说就是创建多个线程一块做不同的任务

```json
1、企业中工作，实现某一个功能如果要用到一个没掌握的知识
2、先根据自己搜索#看文章，官方文档（优选），看视频（次选）
3、#先把功能实现（完成自己的本职工作）后续有时间，再去研究学习
```

#### 静态工厂方法

这两个方法可以帮助我们封装任务逻辑。

```
sync: 同步（从上往下，按顺序执行）
async: 异步（创建多个线程，一块执行不同的任务）
```

#### supplyAsync有返回值

```
方法名一样，参数不一样：重载
```

```java
//异步执行任务：异步（新创建一个线程）
static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
// 使用自定义线程池
static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor);
```

`supplyAsync()` 方法接受的参数是 `Supplier<T>` ，这也是一个函数式接口，T 是返回结果值的类型。

```java
@FunctionalInterface
public interface Supplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}
```

当你需要异步操作且关心返回结果的时候,可以使用 `supplyAsync()` 方法。

```java
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
    //执行sql，获取返回值
    System.out.println("新建子线程执行任务");
    return 2;
});

System.out.println(future1.get());
```

#### runAsync无返回值

```java
static CompletableFuture<Void> runAsync(Runnable runnable);
// 使用自定义线程池
static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor);
```

`runAsync()` 方法接受的参数是 `Runnable` ，没有返回值。当你需要**异步操作且不关心返回结果**的时候可以使用

```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

```java
CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("hello!"));
future.get();// 输出 "hello!"
```



### 14.2 实现思路

1. 确定接口代码位置，调用的接口是：/index/getBaseInfo

2. 阅读实现代码：ReportServiceImpl.getBaseInfo()

   ```java
   /**
        * 获取首页基本数据
        * @param beginCreateTime
        * @param endCreateTime
        * @return
        */
   @Override
   public IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime) {
       //1）构建一个空的结果集对象
       IndexBaseInfoVO result = new IndexBaseInfoVO();
       //2 封装结果集属性
       //由于查询需要用到用户名 调用工具类获取用户名
       String username = SecurityUtils.getUsername();
       //4 封装结果集对象
       //4.1 查询线索数量
       result.setCluesNum(reportMpper.getCluesNum(beginCreateTime, endCreateTime, username));
     	//4.2 查询商机数量
       result.setBusinessNum(reportMpper.getBusinessNum(beginCreateTime, endCreateTime, username));
     	//4.3 查询合同数量
       result.setContractNum(reportMpper.getContractNum(beginCreateTime, endCreateTime, username);
       //4.4 查询销售金额
       result.setSalesAmount(reportMpper.getSalesAmount(beginCreateTime, endCreateTime, username));
       //5 返回结果集对象
       return result;
   }
   ```

3. 分析优化的点的所在位置

   > 接收请求参数和返回参数给前端和sql语句部分有没有问题
   >
   > 看起来都是为了完成基本功能的需求，感觉没有什么问题啊
   >
   > 现在我们要注意，我们是对代码进行性能调优，**功能并不是不能用，而是要在其原有代码上进行优化**
   >
   > 我们可以看到他整体的代码是可以正常运行的，**但是他的service层的代码是一个串行执行的**
   >
   > 那么整个接口的返回时间是：线索查询的时间+商机数查询的时间+合同数查询的时间+销售金额查询时间
   >
   > 因为是串行执行的当第一个代码执行完了以后，才会继续执行第二个sql语句这种串行的方式显然是不合理的，那么如何来优化呢？能否采用并行执行的方式呢？

4. 需要将代码默认串行执行（同步执行按顺序从上往下，如果上边代码没执行完需要等待）换成并行（同时查询4个）

   > 提示：研究CompletableFuture的作用，如何使用？

   ```java
   CompletableFuture aFuture = CompletableFuture.supplyAsync(()->{
      //.....
      return xxx;
   });
   CompletableFuture bFuture = CompletableFuture.supplyAsync(()->{
      //.....
      return xxx;
   });
   CompletableFuture cFuture = CompletableFuture.supplyAsync(()->{
      //.....
      return xxx;
   });
   //并行处理
   CompletableFuture.allOf(aFuture,bFuture,cFuture).join();
   //取值
   var a= aFuture.get();
   var b= bFuture.get();
   var c= cFuture.get();
   ```

### 14.3 代码实现

按照CompletableFuture来修改代码，修改后的代码：

```java
/**
     * 获取首页基本数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime) {
        //1）构建一个空的结果集对象
        IndexBaseInfoVO result = new IndexBaseInfoVO();
        //2 封装结果集属性
        // 2.1 由于查询需要用到用户名 调用工具类获取用户名
        String username = SecurityUtils.getUsername();
        try {
           // 2.2 开启独立线程: 开始查询 线索数量
            CompletableFuture<Integer> clueNums = CompletableFuture.supplyAsync(()->{

                return reportMpper.getCluesNum(beginCreateTime, endCreateTime, username);
            });
            // 2.3 开启独立线程:开始查询 商机数量
            CompletableFuture<Integer> bussinessNum = CompletableFuture.supplyAsync(()->{

                return reportMpper.getBusinessNum(beginCreateTime, endCreateTime, username);
            });
            // 2.4 开启独立线程:开始查询 合同数量
            CompletableFuture<Integer> contractNum = CompletableFuture.supplyAsync(()->{

                return reportMpper.getContractNum(beginCreateTime, endCreateTime, username);
            });
            // 2.5 开启独立线程:开始查询 销售金额数量
            CompletableFuture<Double> saleAmount = CompletableFuture.supplyAsync(()->{

                return reportMpper.getSalesAmount(beginCreateTime, endCreateTime, username);
            });

            //3 join等待所有线程全部执行完成
            CompletableFuture.allOf(clueNums,
                            bussinessNum,
                            contractNum,
                            saleAmount)
                    .join(); //阻塞主线程，等待所有的任务都执行完
            //4 封装结果集对象
            result.setCluesNum(clueNums.get());
            result.setBusinessNum(bussinessNum.get());
            result.setContractNum(contractNum.get());
            result.setSalesAmount(saleAmount.get());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //5 返回结果集对象
        return result;
    }
```

另外一种方式：

```java
//1）构建一个空的结果集对象
IndexBaseInfoVO result = new IndexBaseInfoVO();
//2 封装结果集属性
// 2.1 由于查询需要用到用户名 调用工具类获取用户名
String username = SecurityUtils.getUsername();

CompletableFuture<Void> clueNums = CompletableFuture.runAsync(()->{
    // 2.2 开始查询第一个属性 线索数量
    result.setCluesNum(reportMpper.getCluesNum(beginCreateTime, endCreateTime, username));
});

CompletableFuture<Void> bussinessNum = CompletableFuture.runAsync(()->{
    // 2.3 开始查询第一个属性 商机数量
    result.setBusinessNum(reportMpper.getBusinessNum(beginCreateTime, endCreateTime, username));
});

CompletableFuture<Void> contractNum = CompletableFuture.runAsync(()->{
    // 2.4 开始查询第一个属性 合同数量
    result.setContractNum(reportMpper.getContractNum(beginCreateTime, endCreateTime, username));
});
CompletableFuture<Void> saleAmount = CompletableFuture.runAsync(()->{
    // 2.5 开始查询第一个属性 销售金额数量
    result.setSalesAmount(reportMpper.getSalesAmount(beginCreateTime, endCreateTime, username));
});
//3 join等待所有线程全部执行完成
CompletableFuture
        .allOf(clueNums,
                bussinessNum,
                contractNum,
                saleAmount)
        .join();

return result;
```



## 15、任务15-理解新需求

> 30分钟分析需求：可以组内讨论
>
> 如何熟悉新需求：
> 1、查看产品原型
> 2、需求规格说明书（需求分析说明书）

### 15.1 任务描述

产品原型地址：https://app.mockplus.cn/s/J75ri9TemI9U

要实现的功能：

（1）线索跟进【优先】

（2）商机跟进【优先】

（3）伪线索

（4）踢回公海



### 15.2 分析思路

#### 1、如何看原型

​	提出问题：我们要看什么，线索跟进和商机跟进

​	我们来看线索跟进对应的页面：https://app.mockplus.cn/app/share-9faea72911ef86bf8e3842177b0785feshare-J75ri9TemI9U/comment/byFT7QUzmCqJ/4UpDwgB_cMkiy

![](img/15c976437581f1cb4ab4f1994ef3cc3.png)

​	注意在看任何原型的时候 每个功能点 你们的产品经理都会做出一部分说明

​	如何去对照，可以利用连线，可以利用两侧数字编号去对照

![](img/8a34a2f084349cd932134775dd8720b.png)

​			![](img/4ebc652c2bfc61d1e3bc2c2f782d981.png)																										

#### 2、理解需求部分

> 通过页面和对项目的理解，左右通过序号对照 通过连线对照，理解对应页面实现的功能和需求

#### 3、理解跟进

那么所谓跟进，产生交互行为，就是打电话跟进

那么我们公司这么多的销售人员可能第二次打电话的时候的销售人员就发生了变动，那么这个时候前一个销售人员如何获取第一个销售人员沟通过程中的信息的呢？不可能再去和客户进行一次沟通把，这样客户的体验就很糟糕了，每次都要从头说一遍，就觉得我们的销售人员不专业，如何解决这样的问题呢？



### 15.3 实现思路

**新增跟进模块功能**

跟进又分为：

- 线索跟进：在线索阶段跟进客户，与客户沟通的过程中记录客户的一些基本信息
- 商机跟进：在商机阶段跟进客户，与客户沟通的过程中记录客户的一些意向信息

所有的跟进记录都存储在数据库中，方便当前销售人员或下一个销售人员进行跟进沟通，在进入到根据页面的时候应当展示之前的销售人员的跟进记录，这时销售人员应当快速浏览之前的跟进记录和当前的客户的一些信息，通过这些信息整理出和当前用户沟通时的重点，方便在打电话的时候直击主题



## 16、任务16-表设计

```
30分钟时间：根据产品原型中的页面确定需要保存哪些字段
```

### 16.1 任务描述

实现线索和商机跟进需要用到哪些表？表里需要哪些字段？

```
提示: 线索跟进是否要存储跟进记录?  
现有的表是否满足要求,如果不满足,线索和商机跟进 两个业务 应该有几个表? 表应该有那些字段?
```

### 16.2 实现思路

1.先完成粗粒度设计

​	1.1 确定我们的跟进表是否要进行拆分
​	1.2 能否不进行拆分？为什么？
​	1.3 如果要拆分怎么拆分？

2.细粒度设计

​	基于粗粒度设计的表和关联关系，基于原型设计每张表里面的字段

### 16.3 具体设计

> 从产品原型分析可以看到：
>
> 1、线索跟进和商机跟进需要保存的字段相差还是比较大的，因此分开更合理一些
>
> 2、跟进表里保存有一些中文的信息，用一张表随着系统使用数据量会非常大

![image-20220815115459234](img/image-20220815115459234.png)

```sql
USE `huike`;

CREATE TABLE `tb_clue_track_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `clue_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '线索id',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '跟进人',
  `subject` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '意向等级',
  `record` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '跟进记录',
  `level` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '意向等级',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '跟进时间',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '0 正常跟进记录 1 伪线索',
  `false_reason` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '原因',
  `next_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='线索跟进记录';

CREATE TABLE `tb_business_track_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `business_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商机id',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '跟进人',
  `key_items` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '沟通重点',
  `record` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '沟通纪要',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '跟进时间',
  `track_status` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '跟进状态',
  `next_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8444 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商机跟进记录';
```



## 17、任务17-接口设计

```
30分钟：组内分工合作
```

### 17.1 任务描述

设计如下功能的接口  （分工完成）

（1）线索跟进（新增和分页查询TableDataInfo）

（2）商机跟进（新增和列表查询AjaxResult）

	在企业开发中, 前后端几乎是同时开始开发的
	开发的第一步是前后台人员讨论接口应该 怎么设计
		1) 大多数时候接口的约定是由后端完成的
			例如请求方式, url , 请求参数, 返回值等
			Restful开发风格，查询指定线索：
			传统：/getClue?id=xxx
			GET: /clue/124324
		2) 前端主要关心返回值中是否包含他需要的数据

---------------------------------------------------------

### 17.2 实现思路

​	1.进行粗粒度设计

​		分析页面原型，有哪些接口

​	2.进行详细接口设计

​		针对每个接口进行分析

​		需要确定每个接口的 **接口名 参数列表 返回值 请求方式**

​		总结：先确定有哪些接口，然后再设计接口



### 17.3 接口文档

#### 1. 线索跟进

##### 1.1 添加跟进记录

![](img/87ce4ce3bda66e77e88da3bb2305819.png)

路径:/clues/record

请求方式:POST

参数列表:

​				传入参数：跟进记录+用户基本信息（可能有修改或补充）（自定义一个VO类型）

```json
{
	"clueId":9009,  			线索id
    "subject;":"1",				学科
    "record":"根据记录", 		 根据记录
    "level":"1",				意向级别
	"type":"0",					0:正常跟进 1伪线索
	"falseReason":"空号",	  	   标记失败原因
	"name":"张三",			  姓名
	"sex":"女",			 	   性别
	"weixin":"wx123456",		微信
	"qq":"qq123456",			QQ
	"age":20				   年龄
    ...
}
```

​				返回值：AjaxResult

```
{"msg":"操作成功","code":200}
```

##### 1.2 查询跟进列表

![](img/04904c4357e2144b67798ca27da266c.png)

方法名:/clues/record/list

请求方式GET

参数列表

​				传入参数:

​			/clues/record/list?clueId=9027

​					线索id:clueId

​				返回值：TableDataInfo

```json
{
    "total":1,
    "rows":[
        {
            "createBy":"admin",
            "createTime":"2021-11-20 16:30:05",
            "updateBy":null,
            "updateTime":null,
            "id":237,
            "clueId":9009,
            "subject":"6",
            "record":"下次继续跟进",
            "level":"0",
            "type":"0",
            "falseReason":null,
            "nextTime":"2021-11-25 12:00"
        }
    ],
    "code":200,
    "msg":"查询成功",
    "params":null
}
```



#### 2. 商机跟进	

##### 2.1 新增商机跟进记录

![](img/33c99baf78289fc306edbc384915785.png)

方法名：/business/record

请求方式:POST

参数列表:

​		传入参数：跟进记录+商机基本信息（自定义一个VO类型）

```json
{
    "id":"",
    "name":"韩人",
    "phone":"13811111400",
    "occupation":"4",
    "education":"5",
    "provinces":"北京市",
    "city":"市辖区",
    "weixin":"w123245743",
    "age":20,							年龄
    "major":"8",						专业			
    "job":"2",							专业
    "salary":"3",						薪资				
    "qq":"99880276",					QQ
    "sex":"0",							性别id
    "expectedSalary":"4",				目标薪资 对应字典id
    "remark":"备注信息",				备注信息
    "subject":"1",						学科id
    "reasons":"学习原因",				学习原因
    "plan":"职业计划",					职业计划
    "planTime":"2022-03-14",			计划学习
    "courseId":12,						课程id
    "otherIntention":"其他意向", 		其他意向
    "trackStatus":"1",					跟进状态
    "nextTime":"2022-03-15 12:00",		下次跟进时间
    "keyItems":"3,1,2",					沟通重点
    "record":"沟通纪要",				沟通纪要
    "createBy":"blackman",				创建人
    "createTime":"2021-11-16 15:34:12",	 创建时间
    "channel":"0",						渠道id
    "activityId":73,					活动id	
    "businessId":"3392"					商机id
}
```

​		返回值：

```
{"msg":"操作成功","code":200}
```



##### 2.2 查询商机跟进列表

![](img/33c99baf78289fc306edbc384915785-1654150683789.png)

方法名:/business/record/list

请求方式:GET

参数列表:

​				传入参数：

​					/business/record/list?businessId=3395

​					businessId:商机id

​				返回值：AjaxResult

```json
{
    "msg":"操作成功",
    "code":200,
    "data":[
        {
            "createBy":"admin",
            "createTime":"2021-11-19 17:21:14",
            "updateBy":null,
            "updateTime":null,
            "id":8437,
            "businessId":3392,
            "keyItems":"5,3",
            "keys":[
                "师资",
                "位置"
            ],
            "record":"1",
            "trackStatus":"1",
            "nextTime":"2021-11-27 12:00"
        }
    ]
}
```



## 18、任务18-接口开发

### 18.1 任务描述

按照提供的接口文档，开发如下功能  ：

（1）线索跟进（新增和分页查询TableDataInfo）

（2）商机跟进（新增和列表查询AjaxResult）

> 1、以小组为单位，2人用一个远程仓库（分支合并的能力）
> 2、把远程仓库地址共享给同学：https://gitee.com/xxxx/crm.git
> 3、两个人共用一个仓库，创建两个分支：clue-track, business-track
> 4、两个人在对应的分支上完成自己选择的功能（线索跟进，商机跟进）
> 5、完成后，在提交之前一直要先pull拉取，再commit+push推送



### 18.2 线索跟进

#### 1、添加跟进记录

```asciiarmor
业务层service需要做的事：
1、更新线索的基本信息（经过和客户沟通，更新最新的客户信息）、状态（改为跟进中）：update tb_clue
2、新增一条线索跟进记录：insert into tb_clue_track_record
```

```java
/////////////////////////////////////伪代码
//tbClueTrackRecord=线索的基本信息+跟进记录
TbClue tbClue = new TbClue();
BeanUtils.copyProperties(tbClueTrackRecord, tbClue);
//设置本次跟踪的线索ID
tbClue.setId(tbClueTrackRecord.getClueId());
//TODO 调用线索数据层对象的更新方法(已经提供，直接调用)


TbClueTrackRecord record = new TbClueTrackRecord();
BeanUtils.copyProperties(tbClueTrackRecord, record);
//TODO 保存本地跟进信息到数据库（自己来写）
```

##### 1.1  设计VO对象

```
view object:视图对象
```

根据接口定义中的参数编写：ClueTrackRecordVo

```java
package com.huike.clues.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ClueTrackRecordVo {

    /** 线索id */
    private Long clueId;

    /** 意向等级 */
    private String subject;

    /** 跟进记录 */
    private String record;

    /** 意向等级 */
    private String level;

    /** 0 正常跟进记录 1 伪线索 */
    private String type;

    /** 原因 */
    private String falseReason;

    /** 客户姓名 */
    private String name;

    /** 1 男 0 女 */
    private String sex;

    /** 微信 */
    private String weixin;

    /** qq */
    private String qq;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;

}
```

##### 1.2 编写控制层

路径:/clues/record

请求方式:POST

TbClueTrackRecordController

```java
/**
 * 新增线索跟进记录
 */
@PreAuthorize("@ss.hasPermi('clues:record:add')") //Spring Security权限控制
@Log(title = "线索跟进记录", businessType = BusinessType.INSERT) //使用AOP记录日志
@PostMapping
public AjaxResult add(@RequestBody ClueTrackRecordVo tbClueTrackRecord) {
    TbClueTrackRecord trackRecord = new TbClueTrackRecord();
    //对象拷贝：将前端传递的数据拷贝到TbClueTrackRecord实体类中
    BeanUtils.copyProperties(tbClueTrackRecord, trackRecord);
    //设置当前时间
    trackRecord.setCreateTime(DateUtils.getNowDate());
    //设置当前用户名
    trackRecord.setCreateBy(SecurityUtils.getUsername());

    //此处因为需要更新线索的状态，因此创建线索对象
    TbClue tbClue = new TbClue();
    BeanUtils.copyProperties(tbClueTrackRecord, tbClue);
    //注意：线索ID前端传递用的clueId，因此需要手动设置
    tbClue.setId(tbClueTrackRecord.getClueId());
    tbClue.setStatus(TbClue.StatusType.FOLLOWING.getValue()); //进行中
    return toAjax(tbClueTrackRecordService.insertTbClueTrackRecord(tbClue, trackRecord));
}
```

##### 1.3 编写service层

ITbClueTrackRecordService

```java
/**
 * 添加线索跟进记录
 * @param tbClue
 * @param tbClueTrackRecord
 * @return
 */
public int insertTbClueTrackRecord(TbClue tbClue, TbClueTrackRecord tbClueTrackRecord);
```

service层实现类：TbClueTrackRecordServiceImpl

```java
@Override
@Transactional
public int insertTbClueTrackRecord(TbClue tbClue, TbClueTrackRecord tbClueTrackRecord) {
    //1、更新线索基本信息和状态
    tbClueMapper.updateTbClue(tbClue);
    //2、新增线索跟进记录
    return tbClueTrackRecordMapper.insertTbClueTrackRecord(tbClueTrackRecord);
}
```

##### 1.4 编写Mapper层

###### 复习MyBatis中的trim

> trim 在英语中有“点缀物”，修剪的意思。可以把‘’标签为一个装饰sql的标签。

```xml
基本格式
<trim prefix="" suffix="" suffixOverrides="" prefixOverrides=""></trim>
```

> prefix：
> 	表示在trim包裹的SQL语句**前面**添加的指定内容。
> suffix：
> 	表示在trim包裹的SQL**末尾**添加指定内容
> prefixOverrides：
> 	表示去掉（覆盖）trim包裹的SQL的**指定首部内容**
> suffixOverrides：
> 	表示去掉（覆盖）trim包裹的SQL的**指定尾部内容**

suffixOverrides的使用示例

如下sql 

```xml
<update id="updateUser">
  update tb_user
  set
  <if test="lastName != null">
   last_name=#{lastName},
  </if>
  <if test="age != null">
   age=#{age},
  </if>
  <if test="phone != null">
   phone=#{phone}
  </if> 
  where id = #{id}
</update>
```

在上述的动态sql中，若phone的值为null，则会导致生成的sql语句最后一个符号为“,”，导致生成的sql出错。

```sql
update tb_user set ast_name=?,  age=?, where id = 2342
```

为了避免此种情况，可使用trim标签中的suffixOverrides 将最后面的一个符号覆盖掉。即:

```xml
<update id="updateUser">
 <trim suffixOverrides=",">
  update user_tab
  set
  <if test="lastName != null">
   last_name=#{lastName},
  </if>
  <if test="age != null">
   age=#{age},
  </if>
  <if test="phone != null">
   phone=#{phone}
  </if> 
 </trim>
</update>
```



浏览TbClueMapper.xml中的更新方法：

```xml
<update id="updateTbClue" parameterType="TbClue">
    update tb_clue
    <trim prefix="SET" suffixOverrides=",">
        <if test="name != null">name = #{name},</if>
        <if test="phone != null and phone != ''">phone = #{phone},</if>
        <if test="channel != null and channel != ''">channel = #{channel},</if>
        <if test="activityId != null">activity_id = #{activityId},</if>
        <if test="sex != null">sex = #{sex},</if>
        <if test="age != null">age = #{age},</if>
        <if test="weixin != null">weixin = #{weixin},</if>
        <if test="qq != null">qq = #{qq},</if>
        <if test="level != null">level = #{level},</if>
        <if test="subject != null">subject = #{subject},</if>
        <if test="status != null">status = #{status},</if>
        <if test="owner != null">owner = #{owner},</if>
        <if test="createTime != null">create_time = #{createTime},</if>
        <if test="createBy != null">create_by = #{createBy},</if>
        <if test="assignBy != null">assign_by = #{assignBy},</if>
        <if test="assignTime != null">assign_time = #{assignTime},</if>
        <if test="falseCount != null">false_count = #{falseCount},</if>
        <if test="nextTime != null">next_time = #{nextTime},</if>
    </trim>
    where id = #{id}
</update>
```

**使用set标签简化【任选一种】：**

```xml
<update id="updateTbClue" parameterType="TbClue">
    update tb_clue
    <set>
        <if test="name != null">name = #{name},</if>
        <if test="phone != null and phone != ''">phone = #{phone},</if>
        <if test="channel != null and channel != ''">channel = #{channel},</if>
        <if test="activityId != null">activity_id = #{activityId},</if>
        <if test="sex != null">sex = #{sex},</if>
        <if test="age != null">age = #{age},</if>
        <if test="weixin != null">weixin = #{weixin},</if>
        <if test="qq != null">qq = #{qq},</if>
        <if test="level != null">level = #{level},</if>
        <if test="subject != null">subject = #{subject},</if>
        <if test="status != null">status = #{status},</if>
        <if test="owner != null">owner = #{owner},</if>
        <if test="createTime != null">create_time = #{createTime},</if>
        <if test="createBy != null">create_by = #{createBy},</if>
        <if test="assignBy != null">assign_by = #{assignBy},</if>
        <if test="assignTime != null">assign_time = #{assignTime},</if>
        <if test="falseCount != null">false_count = #{falseCount},</if>
        <if test="nextTime != null">next_time = #{nextTime},</if>
    </set>
    where id = #{id}
</update>
```

TbClueTrackRecordMapper

```java
    /**
     * 新增线索跟进记录
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);
```

TbClueTrackRecordMapper.xml

```sql
insert into tb_clue_track_record (clu_id,record) values(233, "原因")
```

```asciiarmor
1、<if test="createBy != null and createBy != ''">
字符串类型才能去比较 != ''
2、useGeneratedKeys="true"，使用主键自增
3、keyProperty="id"： 指定主键字段名
```

```xml
<insert id="insertTbClueTrackRecord" parameterType="TbClueTrackRecord" useGeneratedKeys="true" keyProperty="id">
        insert into tb_clue_track_record
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="clueId != null and clueId != ''">clue_id,</if>
            <if test="createBy != null and createBy != ''">create_by,</if>
            <if test="subject != null">subject,</if>
            <if test="record != null">record,</if>
            <if test="level != null">level,</if>
            <if test="createTime != null">create_time,</if>
            <if test="type != null">type,</if>
            <if test="falseReason != null">false_reason,</if>
            <if test="nextTime != null">next_time,</if>
         </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <if test="clueId != null and clueId != ''">#{clueId},</if>
            <if test="createBy != null and createBy != ''">#{createBy},</if>
            <if test="subject != null">#{subject},</if>
            <if test="record != null">#{record},</if>
            <if test="level != null">#{level},</if>
            <if test="createTime != null">#{createTime},</if>
            <if test="type != null">#{type},</if>
            <if test="falseReason != null">#{falseReason},</if>
            <if test="nextTime != null">#{nextTime},</if>
         </trim>
    </insert>
```



##### 1.5 测试新增

```asciiarmor
如果出问题：
1、检查SQL语句：不要到mapper.xml中找SQL，到idea控制台中看最终执行的SQL
2、在业务层添加断点，去观察调用mapper中方法时传递的参数
```



#### 2、查询跟进列表

##### 2.1 设计传入参数

```java
线索id:clueId
```

方法名:/clues/record/list

请求方式GET

参数列表

​				传入参数:

​			/clues/record/list?clueId=9027

​					线索id:clueId

​				返回值：

##### 2.2 编写控制层

```asciiarmor
#注意：返回数据类型
#接口定义中，返回的列表数据属性为：rows，因此需要使用TableDataInfo
```

TbClueTrackRecordController

```java
 @PreAuthorize("@ss.hasPermi('clues:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long clueId) {
        //startPage();
        List<TbClueTrackRecord> list = tbClueTrackRecordService.selectTbClueTrackRecordList(clueId);
        return getDataTable(list);
    }
```

##### 2.3 编写Service层

ITbClueTrackRecordService

```java
    /**
     * 跟进线索id查询线索跟进记录
     * @param clueId
     * @return
     */
	public List<TbClueTrackRecord> selectTbClueTrackRecordList(Long clueId);
```

TbClueTrackRecordServiceImpl

```java
   /**
    * 根据线索id查询线索跟进记录
    */
@Override
public List<TbClueTrackRecord> selectTbClueTrackRecordList(Long clueId) {
   return tbClueTrackRecordMapper.selectTbClueTrackRecordListByClueId(clueId);
}
```

##### 2.4 编写Mapper层

TbClueTrackRecordMapper

```java
    /**
     * 根据线索id查询线索跟进记录
     * @param clueId
     * @return
     */
	public List<TbClueTrackRecord> selectTbClueTrackRecordListByClueId(Long clueId);
```

TbClueTrackRecordMapper.xml

```sql
selec * from tb_clue_track_record
where 
 -- 1=1
 and clue_id = #{clueId}
 and status = #{satus}
```

```xml
<sql id="selectTbClueTrackRecordVo">
    select id, clue_id, create_by, subject, record, level, create_time, type, false_reason, next_time from tb_clue_track_record
</sql>

 <select id="selectTbClueTrackRecordListByClueId" parameterType="Long" resultMap="TbClueTrackRecordResult">
    <include refid="selectTbClueTrackRecordVo"/>
   <where>
        <if test="clueId != null  and clueId != ''"> and clue_id = #{clueId}</if>
    </where>
</select>
```



### 18.3 商机跟进

#### 1、新增商机跟进记录

##### 1.1 设计VO对象

BusinessTrackVo

```java
package com.huike.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class BusinessTrackVo {

    private Long businessId;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 渠道 */
    private Long channelId;

    /** 活动id */
    private Long activityId;

    /** 省 */
    @Excel(name = "省")
    private String provinces;

    /** 区 */
    @Excel(name = "区")
    private String city;

    /** 男或者女 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 微信 */
    private String weixin;

    /** qq */
    private String qq;

    /** 意向等级 */
    private String level;

    /** 意向学科 */
    private String subject;

    /** 课程 */
    private Long courseId;

    /** 职业 */
    private String occupation;

    /** 学历 */
    private String education;

    /** 在职情况 */
    private String job;

    /** 薪资 */
    private String salary;

    /** 专业 */
    private String major;

    /** 希望薪资 */
    private String expectedSalary;

    /** 学习原因 */
    private String reasons;

    /** 职业计划 */
    private String plan;

    /** 计划时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /** 其他意向 */
    @Excel(name = "其他意向")
    private String otherIntention;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;

    //沟通备注
    private String remark;

    //沟通重点
    private String keyItems;


    /** 沟通纪要 */
    private String record;

    /** 跟进状态 */
    private String trackStatus;

}
```

##### 1.2 编写控制层

方法名：/business/record

请求方式:POST

```java
	 /**
     * 新增商机跟进记录
     */
    @PreAuthorize("@ss.hasPermi('business:record:add')")
    @Log(title = "商机跟进记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusinessTrackVo businessTrackVo){
        System.out.println("-------"+businessTrackVo);
        TbBusinessTrackRecord trackRecord=new TbBusinessTrackRecord();
        BeanUtils.copyProperties(businessTrackVo,trackRecord);
        trackRecord.setCreateTime(DateUtils.getNowDate());
        trackRecord.setCreateBy(SecurityUtils.getUsername());
        
        TbBusiness business=new TbBusiness();
        BeanUtils.copyProperties(businessTrackVo,business);
        business.setStatus(TbBusiness.StatusType.FOLLOWING.getValue());
        business.setId(businessTrackVo.getBusinessId());
        return toAjax(tbBusinessTrackRecordService.insertTbBusinessTrackRecord(business,trackRecord));
    }
```

##### 1.3 编写Service层

ITbBusinessTrackRecordService

```java
/**
     * 新增商机跟进记录
     * @param tbBusiness
     * @param tbBusinessTrackRecord
     * @return
     */
    public int insertTbBusinessTrackRecord(TbBusiness tbBusiness,TbBusinessTrackRecord tbBusinessTrackRecord);
```

TbBusinessTrackRecordServiceImpl

```java
@Override
@Transactional
public int insertTbBusinessTrackRecord(TbBusiness tbBusiness, TbBusinessTrackRecord tbBusinessTrackRecord) {
    tbBusinessMapper.updateTbBusiness(tbBusiness);
    return tbBusinessTrackRecordMapper.insertTbBusinessTrackRecord(tbBusinessTrackRecord);
}
```

##### 1.4 编写Mapper层

TbBusinessMapper

```java
 	/**
     * 修改商机
     * 
     * @param tbBusiness 商机
     * @return 结果
     */
    public int updateTbBusiness(TbBusiness tbBusiness);
```

TbBusinessMapper.xml

> update中的set可以不用trim标签，用<set>进行简化

```xml
 <update id="updateTbBusiness" parameterType="TbBusiness">
        update tb_business
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != null">name = #{name},</if>
            <if test="phone != null">phone = #{phone},</if>
            <if test="channel != null">channel = #{channel},</if>
            <if test="activityId != null">activity_id = #{activityId},</if>
            <if test="provinces != null">provinces = #{provinces},</if>
            <if test="city != null">city = #{city},</if>
            <if test="sex != null and sex != ''">sex = #{sex},</if>
            <if test="age != null">age = #{age},</if>
            <if test="weixin != null">weixin = #{weixin},</if>
            <if test="qq != null">qq = #{qq},</if>
            <if test="level != null">level = #{level},</if>
            <if test="subject != null">subject = #{subject},</if>
            <if test="courseId != null">course_id = #{courseId},</if>
            <if test="createBy != null">create_by = #{createBy},</if>
            <if test="createTime != null">create_time = #{createTime},</if>
            <if test="occupation != null">occupation = #{occupation},</if>
            <if test="education != null">education = #{education},</if>
            <if test="job != null">job = #{job},</if>
            <if test="salary != null">salary = #{salary},</if>
            <if test="major != null">major = #{major},</if>
            <if test="expectedSalary != null">expected_salary = #{expectedSalary},</if>
            <if test="reasons != null">reasons = #{reasons},</if>
            <if test="plan != null">plan = #{plan},</if>
            <if test="planTime != null">plan_time = #{planTime},</if>
            <if test="otherIntention != null">other_intention = #{otherIntention},</if>
            <if test="nextTime != null">next_time = #{nextTime},</if>
            <if test="status != null">status = #{status},</if>
        </trim>
        where id = #{id}
    </update>
```

TbBusinessTrackRecordMapper

```java
    /**
     * 新增商机跟进记录
     * 
     * @param tbBusinessTrackRecord 商机跟进记录
     * @return 结果
     */
    public int insertTbBusinessTrackRecord(TbBusinessTrackRecord tbBusinessTrackRecord);
```

TbBusinessTrackRecordMapper.xml

```xml
<insert id="insertTbBusinessTrackRecord" parameterType="TbBusinessTrackRecord" useGeneratedKeys="true" keyProperty="id">
        insert into tb_business_track_record
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="businessId != null and businessId != ''">business_id,</if>
            <if test="createBy != null and createBy != ''">create_by,</if>
            <if test="keyItems != null">key_items,</if>
            <if test="record != null">record,</if>
            <if test="createTime != null">create_time,</if>
            <if test="trackStatus != null">track_status,</if>
            <if test="nextTime != null">next_time,</if>
         </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <if test="businessId != null and businessId != ''">#{businessId},</if>
            <if test="createBy != null and createBy != ''">#{createBy},</if>
            <if test="keyItems != null">#{keyItems},</if>
            <if test="record != null">#{record},</if>
            <if test="createTime != null">#{createTime},</if>
            <if test="trackStatus != null">#{trackStatus},</if>
            <if test="nextTime != null">#{nextTime},</if>
         </trim>
    </insert>
```



#### 2、商机跟进记录列表

##### 2.1 设计传入参数

方法名:/business/record/list

请求方式:GET

参数列表:

​				传入参数：

​					/business/record/list?businessId=3395

​					businessId:商机id

```java
商机id：businessId
```

##### 2.2 编写控制层

```java
 /**
     * 查询商机跟进记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:record:list')")
    @GetMapping("/list")
	public AjaxResult list(Long businessId){
    //public AjaxResult list(@RequestParam("businessId")Long id){
        //查询出来的沟通重点是dict_value（1,7）
        List<TbBusinessTrackRecord> list= tbBusinessTrackRecordService.selectTbBusinessTrackRecordList(id);
        return AjaxResult.success(list);
    }
```

##### 2.3 编写Service层

ITbBusinessTrackRecordService

```java
    /**
     * 跟进商机id查询商机跟进记录
     * @param id
     * @return
     */
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(Long id);
```

TbBusinessTrackRecordServiceImpl

```java
 /**
     * 跟进商机id查询商机跟进记录
     */
	@Override
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordList(Long id) {
		return tbBusinessTrackRecordMapper.selectTbBusinessTrackRecordListByBusinessId(id);
	}
```



##### 2.4 编写Mapper层

TbBusinessTrackRecordMapper

```java
    /**
     * 跟进商机id查询商机跟进记录
     * @param id
     * @return
     */
	public List<TbBusinessTrackRecord> selectTbBusinessTrackRecordListByBusinessId(Long id);
```

TbBusinessTrackRecordMapper.xml

```xml
<sql id="selectTbBusinessTrackRecordVo">
    select id, business_id, create_by, key_items, record, create_time, track_status,next_time from tb_business_track_record
</sql>

<select id="selectTbBusinessTrackRecordListByBusinessId" parameterType="Long" resultMap="TbBusinessTrackRecordResult">
    <include refid="selectTbBusinessTrackRecordVo"/>
    <where>  
        <if test="businessId != null  and businessId != ''"> and business_id = #{businessId}</if>
    </where>
</select>
```



### 18.4 伪线索和退回公海【扩展】

#### 1、伪线索

经过对线索的跟进，发现是伪线索后，伪线索会进入线索池之后可能会被其它用户捞取，如果这条确实是伪线索这样反复被捞取是很浪费资源的，所以如果此线索被标记了三次伪线索，那这条线索就可以删除了。

```java
/**
 * 伪线索
 * @param id
 * @param falseClueVo
 * @return
 */
@PreAuthorize("@ss.hasPermi('clues:clue:false')")
@Log(title = "伪线索", businessType = BusinessType.UPDATE)
@PutMapping("/false/{id}")
public AjaxResult cluesFalse(@PathVariable Long id, @RequestBody FalseClueVo falseClueVo) {
   return toAjax(tbClueService.falseClue(id, falseClueVo.getReason(), falseClueVo.getRemark()));
}
```

```java
@Override
@Transactional
public int falseClue(Long id, String reason, String remark) {
    TbClue tbClue = tbClueMapper.selectTbClueById(id);
    int falseCount = tbClue.getFalseCount();
    // 上报超过三次删除
    if (falseCount >= 2) {
        // 删除这条线索，（企业逻辑删除）
        return tbClueMapper.removeClueByFalseClue(id);
    }
    // 少于三次入线索池
    tbClue.setFalseCount(tbClue.getFalseCount() + 1);
    tbClue.setStatus(TbClue.StatusType.FALSE.getValue());
    updateTbClue(tbClue);
    // 伪线索原因
    TbClueTrackRecord trackRecord = new TbClueTrackRecord();
    trackRecord.setCreateBy(SecurityUtils.getUsername());
    trackRecord.setFalseReason(reason);
    trackRecord.setRecord(remark);
    trackRecord.setClueId(id);
    trackRecord.setType("1");
    trackRecord.setCreateTime(DateUtils.getNowDate());
    return tbClueTrackRecordMapper.insertTbClueTrackRecord(trackRecord);
}
```

#### 2、退回公海

```ABAP
如果已经进入商机阶段，客户报名了别的机构，而不是在我们机构报名学习，我们该如何处理这部分客户：
踢回商机公海池即可
```

 TbBusinessController

```java
/**
 * 退回公海
 *
 * @param id
 * @param reason
 * @return
 */
@PreAuthorize("@ss.hasPermi('business:business:back')")
@Log(title = "退回公海", businessType = BusinessType.UPDATE)
@PutMapping("/back/{id}/{reason}")
public AjaxResult back(@PathVariable("id") Long id, @PathVariable("reason") String reason) {
    return AjaxResult.success(tbBusinessService.backPool(id, reason));
}
```



 TbBusinessServiceImpl

```java
@Override
public int backPool(Long busniessId, String backReason) {
    // 退回公海原因
    TbBusinessTrackRecord trackRecord = new TbBusinessTrackRecord();
    trackRecord.setCreateBy(SecurityUtils.getUsername());
    trackRecord.setRecord(dictDataMapper.selectDictLabel("reasons_for_business_reporting", backReason));
    trackRecord.setBusinessId(busniessId);
    trackRecord.setCreateTime(DateUtils.getNowDate());
    //保存商机跟进记录
    tbBusinessTrackRecordMapper.insertTbBusinessTrackRecord(trackRecord);
    //修改商机状态
    return updateStatus(busniessId, TbBusiness.StatusType.FALSE.getValue());
}
```

## 19、任务19-代码合并

任务：代码合并

将今天完成的代码进行合并，并解决冲突，合并后进行功能测试。

```asciiarmor
1、切换（checkout）分支之前：
	先提交（commit）代码到本地仓库（如果有自己配置相关的改动，比如mysql密码，端口等，这些内容不需要提交，	进行回滚（rollback）即可）
	然后再进行推送（push）
2、分支合并时，要先切换到目的地分支（如果要合并到master，则checkout master）
3、要将哪个分支合并过来，右下角点击对应的分支选择“merge into current”
4、记得测试，如果测试通过，记得push到远程的master分支上
```



## 20、总结

### 20.1 git使用

```asciiarmor
每个人创建一个分支去开发？？？？错误做法
正确的做法：新实现一个大需求，才去新建分支（团队所有开发人员都需要在这个分支下进行开发）
```



### 20.2 更深的理解

1）如何确定一条商机**从过去到现在的所有的拥有人**

查询商机表和分配表，分配表里的assign_id若等于商机表里的id，并且分配表里的type为1，则分配表里的user_id则为所属人id

```sql
SELECT  ar.user_name, b.*
FROM `tb_business` b
LEFT JOIN `tb_assign_record` ar ON b.id = ar.`assign_id` AND ar.type = 1
```



2）如何确定一条线索的**最终所属人**

查询线索表和分配表，分配表里的assign_id若等于线索表里的id，且分配表里的latest为1,且type为0，则分配表里的user_id则为所属人id

```sql
SELECT ar.user_name, c.*
FROM `tb_clue` c
LEFT JOIN `tb_assign_record` ar ON c.id = ar.assign_id AND ar.latest = 1 AND ar.type = 0
```



### 20.3 日志记录

项目中通过Spring AOP实现了日志记录，一般选择在Controller上添加@Log注解，主要通过如下两种通知：

1. @AfterReturning：返回后通知
2. @AfterThrowing：抛出异常后通知

```java
/**
 * 修改商机
 */
@Log(title = "商机", businessType = BusinessType.UPDATE)
@PutMapping
public AjaxResult edit(@RequestBody TbBusiness tbBusiness) {
    return toAjax(tbBusinessService.updateTbBusiness(tbBusiness));
}
```



### 20.4 数据权限控制

项目中也是通过AOP+自定义注解的方式实现的数据权限控制的：

- 数据过滤处理切面类：DataScopeAspect

- 其中通过前置通知before，在业务层service方法执行之前进行数据权限控制

  ```java
  /**
   * 商机Service业务层处理
   *
   * @date 2021-04-25
   */
  @Service
  public class TbBusinessServiceImpl implements ITbBusinessService {
      /**
       * 查询商机列表
       *
       * @param tbBusiness 商机
       * @return 商机
       */
      @Override
      @DataScope(deptAlias = "r", userAlias = "r")
      public List<TbBusiness> selectTbBusinessList(TbBusiness tbBusiness) {
          return tbBusinessMapper.selectTbBusinessList(tbBusiness);
      }
  }
  ```

- 数据权限过滤注解：@DataScope



### 20.5 项目中Redis使用

在我们系统中redis存储了哪些内容：

- 验证码的结果
- 登录的用户信息（JWT实现退出登录）


- 活动编号（老版活动编码是否已存在）


- 字典表的内容



## 21、任务21-技术调研

```
大家任选1个技术调研的任务进行：30分钟
```

### 21.1 任务描述

#### 1、技术调研 EasyExcel

> 用于实现**线索批量导入**

![image-20220603075526059](img/image-20220603075526059.png)

官网地址如下： https://yuque.com/easyexcel

Git地址：https://gitcode.net/mirrors/alibaba/easyexcel?utm_source=csdn_github_accelerator



#### 2、技术调研 Minio

> 用于实现**合同上传**

![image-20220603075609728](img/image-20220603075609728.png)

官网地址：https://docs.min.io/

中文文档地址 http://docs.minio.org.cn



## 22、任务22-技术应用

### 22.1 任务描述

#### 1、线索批量导入

因为线下做活动或线上做推广的时候（比如你留一个手机号给给你一瓶水），活动的运营人员会收取很多的手机号，个人信息，这些手机号不可能让活动人员到我们的crm系统中一个一个进行录入,他最多整理成一份excel，然后在crm端应该有一个功能，这个功需要能批量的导入excel中的线索到我们的数据库中

线索模板如下图所示：

<img src="img/image-20220604150001418.png" alt="image-20220604150001418" style="zoom:50%;" /> 

线索数据如下图所示：

<img src="img/3fc3a2e4c1371eec60991777bc3ca9c.png" style="zoom:50%;" /> 

导入完成后需要在页面上显示成功了多少条，失败了多少条

![](img/9180b5704ed390abf0d64a04f87a4f6.png)

那么多的数据，需要利用EasyExcel解析出每一列的数据，整理好数据后存储到数据库中

接口文档：

​	接口名：/clue/importData

​	请求方式：Post

​	传入参数:file 上传的文件

​	返回值：

```json
{
    "msg":"操作成功",
    "code":200,
    "data":{
        "successNum":27,
        "failureNum":2173
    }
}
```



#### 2、合同上传

**传统文件存储**：直接存储到tomcat服务器上

![image-20220531105815238](img/image-20220531105815238.png)

```
缺点:
   1) 本次磁盘空间有限,不能无限制扩容
   2) 容易丢失数据
优点:
	简单   
```

分布式存储 Minio 技术

![image-20220531110921733](img/image-20220531110921733.png)

````
缺点:
   繁琐
优点:
   1) 支持扩容
   2) 防止数据丢失
````

​	接口名：/common/upload

​	请求方式：POST

​	传入参数：

​		上传的文件

​	返回值：

```json
{
    "msg":"操作成功",
    "fileName":"/huike-crm/upload/2022/03/18/8916214c-44ff-4aef-83bb-dbc7d3f88ed8.pdf",
    "code":200,
    "data": "",
    "url":"http://127.0.0.1:9000/huike-crm/upload/2022/03/18/8916214c-44ff-4aef-83bb-dbc7d3f88ed8.pdf"
}
```

​	