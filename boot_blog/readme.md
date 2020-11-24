# 介绍

这篇博客通过 Spring 搭建 个人博客，能够实现初步的对 SpringBoot 有个认识，原创链接在  https://blog.csdn.net/u013967175/category_9273727.html， 我自己的fork的代码在 https://githbu.com/notfresh/spring-blog 

**我提供对这个项目的一些简单问题的答疑和我 fork的代码 的更新维护。**如有什么问题，请在下方留言。

学习这个项目，首先要了解一些基本的 spring-boot 知识，装好 mysql 数据库，会用 intellij-idea， 了解 maven 工具，装好 redis。总之，这是一个绝对入门的项目，可以帮助你简单快速了解 spring-boot的知识。

代码的版本比老，但学习够用了。2020-08-02 17:40:20。

代码结构如下：

```
.
├── pom.xml
├── readme.md
├── script
│   └── create.sql
├── src
│   └── main...
```

src代码的结构如下：

```
.
└── main
    ├── java
    │   └── cn ...
    └── resources
        ├── SensitiveWords.txt
        ├── application.properties
        ├── mybatis-config.xml
        ├── static
        ├── templates
        └── toolbox.xml
```

主要核心代码目录如下：

```
.
└── cn
    └── tzy
        └── Jblog
            ├── WebApplication.java
            ├── aync # 这段代码估计是遗产代码。。。从其他地方拷贝而没有用到的！
            │   ├── EventConsumer.java
            │   ├── EventHandler.java
            │   ├── EventModel.java
            │   ├── EventProducer.java
            │   ├── EventType.java
            │   └── MailHandler.java
            ├── configuration # 拦截器配置代码
            │   └── WebConfiguration.java
            ├── controller  # 路由页面
            │   ├── ArchiveController.java
            │   ├── ArticleController.java
            │   ├── CommentController.java
            │   ├── IndexController.java
            │   └── LikeController.java
            ├── dao # dao页面
            │   ├── ArchiveDao.java
            │   ├── ArticleDao.java
            │   ├── ArticleTagDao.java
            │   ├── CommentDao.java
            │   ├── LoginTicketDao.java
            │   ├── TagDao.java
            │   └── UserDao.java
            ├── interceptor  # # 拦截器编写代码
            │   ├── ArticleClickInterceptor.java
            │   ├── LoginRequestInterceptor.java
            │   └── PassportInterceptor.java
            ├── model
            │   ├── Archive.java
            │   ├── Article.java
            │   ├── ArticleTag.java
            │   ├── Comment.java
            │   ├── HostHolder.java
            │   ├── LoginTicket.java
            │   ├── Tag.java
            │   ├── User.java
            │   └── ViewObject.java
            ├── service
            │   ├── ArticleService.java
            │   ├── CommentService.java
            │   ├── JedisService.java
            │   ├── LikeService.java
            │   ├── SensitiveService.java
            │   ├── TagService.java
            │   └── UserService.java
            └── util
                ├── JblogUtil.java
                ├── MailSender.java
                └── RedisKeyUntil.java

```



# 目录

[介绍](#介绍)<br/>[Spring boot 搭建个人博客系统（一）——整体思路](#Spring-boot-搭建个人博客系统（一）——整体思路)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[1. 搭建项目工程](#1-搭建项目工程)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[1.1 引入spring boot依赖包](#11-引入spring-boot依赖包)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[1.2 工程结构](#12-工程结构)<br/>&nbsp;&nbsp;[２. 搭建数据库](#２-搭建数据库)<br/>&nbsp;&nbsp;[３. 总结](#３-总结)<br/>[Spring boot 搭建个人博客系统（二）——登录注册功能](#Spring-boot-搭建个人博客系统（二）——登录注册功能)<br/>&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[1. 数据模型](#1-数据模型)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[1.1 引入MyBatis ORM框架](#11-引入MyBatis-ORM框架)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[1.2 添加数据库表实体类](#12-添加数据库表实体类)<br/>&nbsp;&nbsp;&nbsp;&nbsp;[1.3 添加数据库操作DAO类](#13-添加数据库操作DAO类)<br/>&nbsp;&nbsp;[2. 用户注册](#2-用户注册)<br/>&nbsp;&nbsp;[3. 用户登录](#3-用户登录)<br/>&nbsp;&nbsp;[4. 免密码登录](#4-免密码登录)<br/>&nbsp;&nbsp;[5. 总结](#5-总结)<br/>[Spring boot 搭建个人博客系统（三）——权限管理功能](#Spring-boot-搭建个人博客系统（三）——权限管理功能)<br/>&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[1. 数据模型](#1-数据模型)<br/>&nbsp;&nbsp;[2. Spring拦截器](#2-Spring拦截器)<br/>&nbsp;&nbsp;[3. 获取用户信息](#3-获取用户信息)<br/>&nbsp;&nbsp;[4. 访问权限管理](#4-访问权限管理)<br/>&nbsp;&nbsp;[5. 总结](#5-总结)<br/>[Spring boot 搭建个人博客系统（四）——文章的发布和分页显示](#Spring-boot-搭建个人博客系统（四）——文章的发布和分页显示)<br/>&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[1. 数据模型](#1-数据模型)<br/>&nbsp;&nbsp;[2. 文章的发布](#2-文章的发布)<br/>&nbsp;&nbsp;[3. 文章的分页显示](#3-文章的分页显示)<br/>&nbsp;&nbsp;[4. 分页查询](#4-分页查询)<br/>&nbsp;&nbsp;[5. 总结](#5-总结)<br/>[Spring boot 搭建个人博客系统（五）——标签和标签云](#Spring-boot-搭建个人博客系统（五）——标签和标签云)<br/>&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[1. 数据模型](#1-数据模型)<br/>&nbsp;&nbsp;[2. 标签功能](#2-标签功能)<br/>&nbsp;&nbsp;[3. 标签云功能](#3-标签云功能)<br/>&nbsp;&nbsp;[4. 总结](#4-总结)<br/>[Spring boot 搭建个人博客系统（六）——文章点击量和阅读排行榜](#Spring-boot-搭建个人博客系统（六）——文章点击量和阅读排行榜)<br/>&nbsp;&nbsp;[0. 思路](#0-思路)<br/>&nbsp;&nbsp;[１. 数据模型](#１-数据模型)<br/>&nbsp;&nbsp;[２. 文章点击量](#２-文章点击量)<br/>&nbsp;&nbsp;[３. 热门文章排行榜](#３-热门文章排行榜)<br/>&nbsp;&nbsp;[４. 总结](#４-总结)<br/>[参考](#参考)



# Spring boot 搭建个人博客系统（一）——整体思路

### 0. 思路

博客的基本作用就是博主写文章，读者浏览文章，因此博客的基本功能就是文章的发布和阅读；同时为了方便浏览加入主题分类和标签分类等功能，为了增加互动加入赞踩和评论等功能；另外为了博客系统的完整性加入了文章点击量，热门文章排行，文章按月归档等功能。

博客功能列表为：

- 用户注册登录功能
- Markdown文章发布功能
- 文章分页显示
- 文章主题分类
- 文章标签/标签云
- 文章点击量
- 热门文章排行
- 赞踩功能
- 评论/回复邮件提醒功能
- 评论敏感词过滤
- Archives/按月归档

## 1. 搭建项目工程

使用IDEA搭建项目工程：File－>New－>Project－>Maven－>输入group和artifact等－>Finish

### 1.1 引入spring boot依赖包

Maven项目是通过pom.xml文件配置工程依赖，项目组织结构等，修改pom.xml文件引入sping boot依赖包和工程开发依赖的包等

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.tzy</groupId>
    <artifactId>blog</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>wenda</name>
    <description>Demo project for Spring Boot</description>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-velocity</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

</project>123456789101112131415161718192021222324252627282930313233343536373839404142
```

其中，引入spring-boot-devtools依赖是为了方便工程开发，比如静态文件修改之后无需重新运行整个工程，只需要重新编译修改的文件。

```
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-devtools</artifactId></dependency>123
```

另外，引入spring-boot-velocity依赖作为Web工程的模板引擎，用来将后端数据渲染到HTML页面再传输到前端浏览器显示。模板引擎的诞生是为了将显示与数据分离，从Sevlet开发时的数据页面混杂在一起变成了现在的数据和显示分离，从而也将页面渲染分成了前端渲染和后端渲染；后端渲染主要就是通过模板引擎将后端数据填充到模板页面中用来传输到前端浏览器显示，前端渲染主要是浏览器对页面CSS样式和JS动作的渲染。
![模板引擎作用](https://typora-1256991781.cos.ap-beijing.myqcloud.com/uPic/SouthEast-20200802171949082.png)

```
<dependency>
   <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-velocity</artifactId>
</dependency>1234
```

同时，引入spring-boot-web依赖作为工程Web开发的基础包，Webk开发需要的一些注解，接口，类等都在这个包中。

```
<dependency>
   <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>1234
```

### 1.2 工程结构

项目Web程序的搭建基于MVC的设计模式，即模型(Model)—视图(View)—控制器(Controller)。控制器用来接收请求，发送响应；视图用来渲染显示，模型用作表征数据实体。因此搭建工程结构如下图所示：
![工程结构图](https://typora-1256991781.cos.ap-beijing.myqcloud.com/uPic/SouthEast.png)

## ２. 搭建数据库

建好数据库用于存储博客系统数据。创建user表用于存储系统用户信息，创建ticket表用于存储用户登录的ticket信息，创建aricle表用于存储文章信息，创建tag表用于存储标签信息，创建article_tag表用于存储文章标签关系信息，创建comment评论表用于存储文章评论信息。

| table       | describe       |
| ----------- | -------------- |
| user        | 用户信息表     |
| ticket      | 登录信息表     |
| aricle      | 文章表         |
| tag         | 标签表         |
| article_tag | 文章标签关系表 |
| comment     | 评论表         |

具体的表结构和对应的实体类在接下来应用中会逐步提到。

## ３. 总结

完成项目搭建，从下一篇开始逐步讲解系统的功能模块。



# Spring boot 搭建个人博客系统（二）——登录注册功能

## 0. 思路

　　用户登录注册功能主要实现用户的添加，验证和记住密码一段时间内的免密码登录。用户的注册是往数据库中插入用户的用户名和密码等信息，用户的验证是从数据库中取出用户的用户名和密码等信息进行比对。明文密码存储有很大的风险，采用在密码后加salt再经过MD5加密的形式存储，这样一方面避免了用户密码信息泄露的风险，同时也防止了暴力破解密码的可能。
　　登录成功后生成一串字符作为ticket存储在数据库和cookie中，用于下次登录的免密码登录验证。一段时间后数据库中的ticket失效，用户需要重新登录。

## 1. 数据模型

　　系统登录注册功能需要验证用户信息，需要操纵数据库的user表和login_ticket表，使用MyBatis作为系统的ORM框架用来简化数据操作。
　　

### 1.1 引入MyBatis ORM框架

(1) 在pom.xml中添加MyBatis依赖jar包和MySQL连接相关的jar包，引入MyBatis相关类库和数据库连接相关类库。

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>12345678910
```

(2) 添加MyBatis配置文件，设置MyBatis相关参数

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <settings>
        <setting name="cacheEnabled" value="true"/>
        <setting name="defaultStatementTimeout" value="3000"/>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="useGeneratedKeys" value="true"/>
    </settings>

</configuration>1234567891011121314
```

(3) 在系统配置文件application.properities中配置数据库URL,用户名密码等信息，同时设置MyBatis配置文件位置。

```
spring.datasource.url=
spring.datasource.username=
#spring.datasource.password=
spring.datasource.password=

mybatis.config-location=classpath:mybatis-config.xml123456
```

### 1.2 添加数据库表实体类

　　根据user表的具体字段设置实体类的对应字段，MyBatis的mapUnderscoreToCamelCase参数设定为true，说明数据库字段的下划线分割自动对应实体类的驼峰形式。

```
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
    private String role;

    public User(){}

    public User(String name){
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
        this.role = "user";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566
```

根据login_ticket表的具体字段设置实体类的对应字段

```
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}1234567891011121314151617181920212223242526272829303132333435363738394041424344454647
```

### 1.3 添加数据库操作DAO类

　　根据MyBatis的使用特点，创建数据库操作接口UserDao.class和LoginTicket.class，用作对数据库执行具体的操作。

```
@Mapper
public interface UserDao {
    String TABLE_NAEM = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ,role ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{name},#{password},#{salt},#{headUrl},#{role})"})
    public void insertUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    public User seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where name=#{name}"})
    public User seletByName(@Param("name") String name);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    public void deleteById(int id);
}123456789101112131415161718
@Mapper
public interface LoginTicketDao {
    String TABLE_NAEM = " login_ticket ";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{userId},#{ticket},#{expired},#{status})"})
    void insertLoginTicket(LoginTicket loginTicket);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    LoginTicket seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where ticket=#{ticket}"})
    LoginTicket seletByTicket(String ticket);

    @Update({"update",TABLE_NAEM,"set status = #{status} where ticket = #{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}123456789101112131415161718192021
```

## 2. 用户注册

　　ORM框架的作用就是将对象与关系型数据库的表进行关联性绑定，根据插入的对象解析出对应的字段插入数据库中。用户注册是在验证注册用户名密码可用的情况下生成一个User对象插入数据库中，同时为了保护用户信息安全在密码存储时，随机生成固定长度的字符串作为salt与密码组合后讲过MD5加密存储在数据库字段中。
　　用户密码如果直接散列后存储在数据库中，黑客可以通过获得这个密码散列值，然后通过查散列值字典（彩虹表）的方式暴力破解，得到用户的密码；通过加salt加密的方式可以一定程度上解决这一问题，因为salt值由系统随机生成，也只有系统知道。即便黑客获取了密码的散列值但在不知道salt值的前提下暴力破解散列值的几率大大降低。
　加salt加密并不能完全杜绝用户密码的泄露，因为一旦密码散列值和salt值同时泄露，黑客通过salt值重建彩虹表，依旧能够获取用户密码。只是这样的计算成本会大大增加，假设每个用户一个salt, 散列值字典的字段有10万, 一次基于salt值的字典重建就要重新生成10万个字段,那么10个密码就需要生成10个散列字典,也就是100万 个字段。因此，从某种意义上来讲,增加salt的长度也就增加了散列值字典字段的数目,也可以提高安全性。还有一种提高安全性的方式，就是salt值的动态生成。通过一定算法动态生成salt值，这样可以大大降低salt值泄露的风险。

```
public Map<String,String> register(String username, String password){
        Map<String,String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User u = userDao.seletByName(username);
        if (u!=null){
            map.put("msg","用户名已经被占用");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        user.setPassword(JblogUtil.MD5(password+user.getSalt()));
        user.setRole("user");
        userDao.insertUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }1234567891011121314151617181920212223242526272829303132
```

## 3. 用户登录

　　用户登录主要是进行用户名和密码的验证，由于用户在注册时候会生成随机的salt值进行密码存储加密，在密码验证时需要读取用户对应的salt值组合进行散列化后与数据库中用户密码进行比对，如果一致则登录成功。
　　用户登录时明文传输密码存在风险，黑客可以通过抓包的方式截取用户信息。一般为了降低这种Web应用登录密码传输过程中泄露的风险，可以采用https方式传输和通过公匙和私匙的非对称加密的方式。

```
public Map<String,String> login(String username, String password){
        Map<String,String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User u = userDao.seletByName(username);
        if (u==null){
            map.put("msg","用户名不存在");
            return map;
        }

        if (!JblogUtil.MD5(password+u.getSalt()).equals(u.getPassword())){
            map.put("msg","密码错误");
            return map;
        }

        String ticket = addLoginTicket(u.getId());
        map.put("ticket",ticket);

        return map;
    }1234567891011121314151617181920212223242526272829
```

## 4. 免密码登录

　　免密码登录功能主要是通过一种自动身份验证的方式实现。用户登录或注册成功后，在一定时间内（如2个小时）再次访问同一个Web程序的任一个页面时都无需再次登录，而是直接进入界面（仅限于本机）。实现这个功能关键就是服务端要识别客户的身份。使用Cookie是最简单的身从验证。
　　Cookie是web服务器存放在客户端的一个文件,客户端访问特定URL时会查询该文件，将与该URL相关的Cookie字段传输至服务端用作特定处理。Cookie可以设置失效时间，当Cookie过了失效时间后会自动消失不再随请求传输到服务器。
　　用户在登录成功或注册成功后随机生成一个ticket作为用户后续操作无需密码验证的凭证，往login_ticket表中插入一条记录将ticket与具体的用户绑定，这样用户在操作时候就能通过ticket凭证辨别身份。登录成功或注册成功后将ticket凭证放入Cookie，保存在用户浏览器中，在下次访问时候会随请求传输到服务器用作身份验证。
(1) 往login_ticket表中插入一条记录用于绑定ticket凭证和用户身份

```
public String addLoginTicket(int userId){
   LoginTicket loginTicket = new LoginTicket();
    loginTicket.setUserId(userId);
    Date date = new Date();
    date.setTime(date.getTime()+1000*3600*30);
    loginTicket.setExpired(date);
    loginTicket.setStatus(0);
    loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));

    loginTicketDao.insertLoginTicket(loginTicket);

    return loginTicket.getTicket();
}12345678910111213
```

(2)　登录成功后将ticket凭证放入cookie，保存在用户浏览器；ticket有时效，过凭证期后用户需重新登录。

```
@RequestMapping("/login")
    public String login(Model model, HttpServletResponse httpResponse,
                        @RequestParam String username,@RequestParam String password,@RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.login(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            if (StringUtils.isNotBlank(next)){
                return "redirect:"+next;
            }

            return "redirect:/";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }12345678910111213141516171819
```

(3)　注册成功后将ticket凭证放入cookie，保存在用户浏览器；ticket有时效，过凭证期后用户需重新登录。

```
@RequestMapping("/register")
    public String register(Model model, HttpServletResponse httpResponse,
                           @RequestParam String username, @RequestParam String password
            ,@RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.register(username,password);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            if (StringUtils.isNotBlank(next))
                return "redirect:"+next;
            else
                return "redirect:/";
        }else {
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }12345678910111213141516171819
```

## 5. 总结

　　系统的注册登录功能主要是将用户注册的用户名密码存储在数据库，在下次登录时进行比对。同时为了免密码登录，在登录成功或注册成功时后生成用户凭证ticket与用户身份绑定在一起放入cookie中，等用户下次访问时候随请求传输至服务器进行验证。这样就能直接根据凭证识别用户身份，无需再经过用户名密码的验证。



# Spring boot 搭建个人博客系统（三）——权限管理功能

## 0. 思路

　　系统权限管理功能用来添加存储区分系统用户，进而实现系统的权限管理。博客系统权限可以简单分为博主权限和普通用户权限，普通用户权限可以浏览文章，发表评论或者赞踩等，博主权限除此之外还可以添加文章，管理系统等。未登录用户只能浏览文章不能发表评论或者赞踩等。
　　权限管理一般有三种常见的思路：利用Spring拦截器进行访问权限验证，利用Spring AOP进行访问权限验证和直接使用Spring Security集成模块进行访问权限验证。

- 利用Spring拦截器进行访问权限验证是通过拦截器在请求到达控制器之前拦截请求进行相应的权限验证。
- 利用Spring AOP进行访问权限验证是利用AOP切面编程的特点，可以在所有控制器函数内织入权限验证模块代码，当请求到达控制器之后会进行响应的权限验证。
- Spring Security是Spring的一个集成的安全框架，其底层原理也是集成众多的拦截器对url进行拦截从而实现响应的权限管理。

三种实现权限管理的方法相比较之下，Spring Security作为一个框架使用简单功能完善但较为笨重；Spring AOP织入权限验证代码比较方便但是不够灵活；系统选用利用Spring拦截器进行访问权限验证，根据具体需求插入相应的拦截器，使用较为方便。

## 1. 数据模型

　　权限管理需要验证用户信息，需要操纵数据库的user表和login_ticket表，这两个表的数据模型已在上一篇[Spring boot 搭建个人博客系统（二）——登录注册功能](http://blog.csdn.net/u013967175/article/details/77435178)中详细说明，这里不再赘述；

## 2. Spring拦截器

Spring拦截器主要是通过Interceptor实现类来实现，主要用来拦截用户的请求并进行相应的处理。拦截器能够在用户请求进入控制器之前将请求拦截下来并进行处理。请求先进入拦截器的preHandle()函数进行处理，处理结束后进入控制器，在控制器处理结束后，页面渲染之前会进入拦截器的postHandle()函数处理，最终在页面渲染结束后进入拦截器的afterCompletion()函数处理。
![拦截器](https://typora-1256991781.cos.ap-beijing.myqcloud.com/uPic/SouthEast-20200802172224078.png)

- preHandle()： 这个方法在业务处理器处理请求之前被调用，SpringMVC 中的Interceptor 是链式的调用的，在一个应用中或者说是在一个请求中可以同时存在多个Interceptor ，多个Interceptor 的调用会依据它的声明顺序依次执行，而且最先执行的都是Interceptor 中的preHandle 方法，所以可以在这个方法中进行一些前置初始化操作或者是对当前请求的一个预处理，也可以在这个方法中进行一些判断来决定请求是否要继续进行下去。该方法的返回值是布尔值Boolean 类型的，当它返回为false 时，表示请求结束，后续的Interceptor 和Controller 都不会再执行；当返回值为true 时就会继续调用下一个Interceptor 的preHandle 方法，如果已经是最后一个Interceptor 的时候就会是调用当前请求的Controller 方法。
- postHandle()：这个方法在当前请求进行处理之后，也就是Controller 方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。postHandle 方法被调用的方向跟preHandle 是相反的，也就是说先声明的Interceptor 的postHandle 方法反而会后执行。在 postHandle()函数链式调用结束后才会进行页面渲染。
- afterCompletion()：该方法也是需要当前对应的Interceptor 的preHandle 方法的返回值为true 时才会执行。顾名思义，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的。

## 3. 获取用户信息

结合上一篇[Spring boot 搭建个人博客系统（二）——登录注册功能](http://blog.csdn.net/u013967175/article/details/77435178)中的免密码登录，拦截用户访问页面的请求，获取请求中的Cookie信息进而判断用户是否有免密码登陆凭证和凭证是否过期。如果用户Cookie存在登录凭证并且凭证有效，则可以通过凭证识别用户信息并将访问设定为已登录状态。如果用户Cookie中不存在凭证或者登录凭证已经过期则将访问设定为未登录状态。已登录状态和未登录状态在具体页面访问时受访问权限管理。

```
@Component
public class PassportInterceptor implements HandlerInterceptor{
    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies()!=null){
            for (Cookie cookie : httpServletRequest.getCookies()){
                if ("ticket".equals(cookie.getName())){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket!=null){
            LoginTicket loginTicket = loginTicketDao.seletByTicket(ticket);
            if (loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){
                return true;
            }
            User user = userDao.seletById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
```

将拦截器添加到系统中：

```
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        super.addInterceptors(registry);
    }
}1234567891011
```

## 4. 访问权限管理

用户在访问页面的时候可以分为已登录状态和未登录状态，同时已登录状态又分为博主权限和普通用户权限。为了区分不同状态的权限范围，通过拦截器拦截特定请求判断用户状态，如果状态不符合则拒绝访问。比如设定拦截器拦截”/create”请求，判断用户是否已经登录，如果未登录则拒绝访问；如果用户已登录则判断用户的身份是博主还是普通用户，如果是普通用户则拒绝访问。

```
@Component
public class LoginRequestInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (hostHolder.getUser()==null||"user".equals(hostHolder.getUser().getRole())){
            httpServletResponse.sendRedirect("/in");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}1234567891011121314151617181920212223
```

将拦截器添加到系统中：

```
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/create");
        super.addInterceptors(registry);
    }
}
```

## 5. 总结

利用Spring拦截器可以拦截所有请求或部分特定用户请求作特定处理，先利用拦截器判断用户登录状态，再利用拦截器判断用户身份进而实现用户权限管理。



# Spring boot 搭建个人博客系统（四）——文章的发布和分页显示

## 0. 思路

- 文章的发布是将文章内容以及文章信息插入到数据库相应的字段中。为了后续文章的显示，在发布文章时需要插入数据库文章标题(title)，文章的描述(describe)，文章的内容(content)，文章的创建时间(create_date)，文章的评论数目(commet_count)和文章的类别(category)等。为了文章编辑的便利引入Markdown编辑页面以及Markdown解析包将输入的Markdown语法的文本解析成相应的HTML文本插入数据库中。
- 文章的显示是按照日期降序从数据库中取出文章，在主页按照发布时间早晚显示文章列表，在文章显示页面显示具体文章的内容。
- 文章的分页显示是为减少页面显示的延迟，因为如果在页面上显示数据库中的所有文章，则所需的查询显示时间较长，降低了用户的体验。

## 1. 数据模型

文章的发布和显示功能需要操作数据库中的article表，使用MyBatis作为系统的ORM框架用来简化数据操作。

- 添加数据库表实体类

```
public class Article {
    private int id;
    private String title;
    private String describes;
    private String content;
    private Date createdDate;
    private int commentCount;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566
```

- 添加数据库操作DAO类

```
@Mapper
public interface ArticleDao {
    String TABLE_NAEM = " article ";
    String INSERT_FIELDS = " title, describes, content, created_Date, comment_Count, category ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{title},#{describes},#{content}" +
            ",#{createdDate},#{commentCount},#{category})"})
    int insertArticle(Article article);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    Article selectById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by id desc limit #{offset},#{limit}"})
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where category=#{category} order by id desc limit #{offset},#{limit}"})
    List<Article> selecttArticlesByCategory(@Param("category") String category,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",TABLE_NAEM,"where category=#{category}"})
    int getArticleCountByCategory(@Param("category") String category);

    @Select({"select count(id) from",TABLE_NAEM})
    int getArticleCount();

    @Update({"update",TABLE_NAEM,"set comment_count = #{commentCount} where id = #{questionId}"})
    void updateCommentCount(@Param("questionId") int questionId,@Param("commentCount") int commentCount);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}12345678910111213141516171819202122232425262728293031
```

## 2. 文章的发布

(1) 为了文章编辑的便利，添加Editor.md插件,在文章编辑页引入Markdown编辑器。

```
<form action="/articleAdd"  method = "post">
    <div class="title option"><label>Title</label><input type="text" class="form-control" id="title" name="title"></div>
    <div class="row categoryTag">
        <div class="col-md-6"><div><label>Category</label></div>
            <select class="" class="form-control" id="category"  name="category">
                <option value="Java">Java</option>
                <option value="Web">Web</option>
                <option value="Linux">Linux</option>
                <option value="分布式系统">分布式系统</option>
                <option value="数据库">数据库</option>
                <option value="算法">算法</option>
                <option value="其它">其它</option>
            </select>
        </div>
        <div class="col-md-6"><label>Tag</label><input type="text" class="form-control" id="tag" name="tag"></div>
    </div>
    <div class="describe option"><label>Describe</label><input type="text" class="form-control" id="describe" name="describe"></div>
    <div class="option"><label>Content</label></div>
    <div class="row">
        <div id="test-editormd">
            <textarea id="content" name="content" style="display:none;"></textarea>
        </div>
    </div>
    <div class="form-group articleSubmit option">
        <button  type="submit" class="btn btn-default">Submit</button>
    </div>
</form>123456789101112131415161718192021222324252627
 editormd("test-editormd", {
    width   : "90%",
    height  : 640,
    syncScrolling : "single",
    path    : "<%=request.getContextPath()%>/resources/editormd/lib/",
    saveHTMLToTextarea : true
});1234567
```

(2) 为了解析输入的Markdown语法的文本，添加flexmark-java插件，将Markdown语法的文本解析成能够直接显示的HTML文本，将文章内容和文章相关信息保存在数据库中。

```
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>0.26.4</version>
</dependency>12345

@RequestMapping("/articleAdd")
    public String addArticle(@RequestParam("title")String title,@RequestParam("category")String category,
                             @RequestParam("tag")String tag,@RequestParam("describe")String describe,
                             @RequestParam("content")String content){
  Article article = new Article();
    article.setTitle(title);
    article.setDescribes(describe);
    article.setCreatedDate(new Date());
    article.setCommentCount(0);
    article.setContent(JblogUtil.tranfer(content));
    article.setCategory(category);
    int articleId = articleService.addArticle(article);

    return "redirect:/";
}123456789101112131415
public static String tranfer(String content){
    MutableDataSet options = new MutableDataSet();
    Parser parser = Parser.builder(options).build();
    HtmlRenderer renderer = HtmlRenderer.builder(options).build();
    Node document = parser.parse(content);
    return renderer.render(document);
}1234567
```

## 3. 文章的分页显示

(1) 为了加快页面显示，在文章列表显示的时候采用分页查询的方式，只查询显示一部分文章。考虑到数据库中文章的量级，系统的分页策略采用最简单的offset+limit的方式。

```
@RequestMapping(path = {"/","/index"})
    public String index(Model model){
       List<ViewObject> vos = new ArrayList<>();
       List<Article> articles = articleService.getLatestArticles(0,4);
       for (Article article:articles){
           ViewObject vo = new ViewObject();
           vo.set("article",article);
           vos.add(vo);
       }

       ViewObject pagination = new ViewObject();
       int count = articleService.getArticleCount();
       User user = hostHolder.getUser();
       if (user==null||"admin".equals(user.getRole())){
           model.addAttribute("create",1);
       }else {
           model.addAttribute("create",0);
       }
       pagination.set("current",1);
       pagination.set("nextPage",2);
       pagination.set("lastPage",count/4+1);
       model.addAttribute("pagination",pagination);

     return "index";
}12345678910111213141516171819202122232425
```

(2) 主页文章列表的显示

```
<ul class="articles">
  #foreach($vo in $vos)
    <li class="blogAticle">
      <div class="articleHeader">
        <p><a href="/article/$!{vo.article.id}">$!{vo.article.title}</a></p>
      </div>
      <div class="articleContent">
        <p>$!{vo.article.describes}</p>
      </div>
      <div class="articleFooter">
        <ul>
          <li><i class="fa fa-calendar" aria-hidden="true"></i><span>$date.format('yyyy-MM-dd', $!{vo.article.createdDate})</span></li>
          <li><i class="fa fa-eye" aria-hidden="true"></i><span>$!{vo.clickCount}</span></li>
          <li><i class="fa fa-list" aria-hidden="true"></i><span>$!{vo.article.category}</span></li>
          <li><i class="fa fa-tags" aria-hidden="true"></i>
            #foreach($tag in $vo.tags)
            <span>$!{tag.name}</span>
            #end
          </li>
          <li class="readMore"><a href="/article/$!{vo.article.id}">read more</a></li>
        </ul>
      </div>
    </li>
  #end
</ul>
<div class="paginationWapper">
  <ul class="pagination">
    #if($pagination.current > 1)
    <li>
      <a href="/page/$!{pagination.prePage}">&laquo;</a>
    </li>
    #else
    <li class="disabled">
      <a href="">&laquo;</a>
    </li>
    #end
    <li><a href="">$!{pagination.current}/$!{pagination.lastPage}</a></li>
    #if($pagination.current < $pagination.lastPage)
    <li>
      <a href="/page/$!{pagination.nextPage}">&raquo;</a>
    </li>
    #else
    <li class="disabled">
      <a href="">&raquo;</a>
    </li>
    #end
  </ul>
</div>123456789101112131415161718192021222324252627282930313233343536373839404142434445464748
```

## 4. 分页查询

```
分页查询有很多实现的方案，在数据库数据量不大的情况下，各种方案的性能相差不大，但当数据库数据量到达一定量级之后，不同的查询方案的分页查询性能相差较大。
1
```

- 最基本的分页方式:

```
SELECT * FROM articles order by id desc LIMIT 50, 10 1
```

limit 子句的优点很明显，简单好用。缺点平时不显著，数据量一大就暴露了。数据库会完整扫描 offset 的行，然后继续扫描 200 行之后才把结果集返回。 offset 在 400W 的时候，这样的 SQL 做一次分页查询就已经至少耗时 5s 了。

- 子查询的分页方式：

```
select * from table where id in (select id from table order by id limit #offset#, #size#)1
```

利用子查询分页的语句的效率在 offset 达到百万级时相比直接 limit 有数倍的提升，但是这条语句不但没有避免遍历 offset，还做了大量的无用重复工作。

- 主键范围分页方式：
  `select * from table where id >= #minId# limit 200`
  通过直接计算出主键的起始值，往后获取200条记录，这条语句执行效率较高，无需遍历offset条记录，但是要求通过主键查询。

## 5. 总结

利用Markdown插件发布存储文章，并通过分页查询的方式将文章列表分页显示在主页上。





# Spring boot 搭建个人博客系统（五）——标签和标签云

## 0. 思路

　　标签作为文章中的分类标记，会显示出该文章是关于哪一方面的文章，当我们点击该标签的时候，会出现该博客中所有属于该标签的文章。一篇文章可以对应很多标签，同时一个标签也可以对应很多篇文章，博客系统的标签功能是一种典型的多对多的情况。
　　解决多对多的标签问题，一般有两种方案。一种是将标签直接以逗号隔开组成一个字符串存储在article表的字段里，这种方案在文章插入和显示时比较简单，可以直接从标签字段里插入或读取，但是在归档同一标签的文章时复杂度较高，需要遍历全部文章记录将标签字段进行分割，看看该记录是否含有此标签，也可以直接使用like语法查询。另一种是将标签存在tag表中，同时创建article_tag关系表用来存储文章和标签的对应关系。在文章插入的同时插入文章标签关系记录，在文章显示的时候通过查询关系表获取article_id或者tag_id来实现标签功能。
　　

## 1. 数据模型

文章的标签功能需要操作数据库中的tag表和article_tag表，使用MyBatis作为系统的ORM框架用来简化数据操作。

- 添加数据库表实体类

```
public class Tag {
    private int id;
    private String name;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}123456789101112131415161718192021222324252627282930
public class ArticleTag {
    private int id;
    private int articleId;
    private int tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}1234567891011121314151617181920212223242526272829
```

- 添加数据库操作DAO类

```
@Mapper
public interface ArticleDao {
    String TABLE_NAEM = " article ";
    String INSERT_FIELDS = " title, describes, content, created_Date, comment_Count, category ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{title},#{describes},#{content}" +
            ",#{createdDate},#{commentCount},#{category})"})
    int insertArticle(Article article);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    Article selectById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by id desc limit #{offset},#{limit}"})
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where category=#{category} order by id desc limit #{offset},#{limit}"})
    List<Article> selecttArticlesByCategory(@Param("category") String category,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",TABLE_NAEM,"where category=#{category}"})
    int getArticleCountByCategory(@Param("category") String category);

    @Select({"select count(id) from",TABLE_NAEM})
    int getArticleCount();

    @Update({"update",TABLE_NAEM,"set comment_count = #{commentCount} where id = #{questionId}"})
    void updateCommentCount(@Param("questionId") int questionId,@Param("commentCount") int commentCount);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}12345678910111213141516171819202122232425262728293031
@Mapper
public interface ArticleTagDao {
    String TABLE_NAEM = " article_tag ";
    String INSERT_FIELDS = " article_id, tag_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    String TAG_FIELDS = " id, name, count ";
    String ARTICLE_FIELDS = " id, title, describes, content, created_Date, comment_Count , category ";

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{articleId},#{tagId})"})
    int insertArticleTag(ArticleTag articleTag);

    @Select({"select",TAG_FIELDS,"from tag where id in (select tag_id from article_tag where article_id=#{articleId})"})
    List<Tag> selectByArticleId(int articleId);

    @Select({"select",ARTICLE_FIELDS,"from article where id in (select article_id from article_tag where tag_id=#{tagId}) limit #{offset},#{limit}"})
    List<Article> selectByTagId(@Param("tagId") int tagId,@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from article where id in (select article_id from article_tag where tag_id=#{tagId})"})
    int selectArticleCountByTagId(@Param("tagId") int tagId);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}123456789101112131415161718192021222324
```

## 2. 标签功能

博客系统采用文章标签关系表的方式实现博客的标签功能，功能的实现主要包括标签的插入，特定文章所有标签的查询和特定标签所有文章的查询。

(1) 标签的插入:在文章发布的时候，将输入的标签信息插入数据库。如果数据库中已存在欲插入的标签则直接返回标签id，同时在文章标签关系表中插入一条关系记录;如果数据库中未存在欲插入的标签则插入该标签并返回标签id，同时在文章标签关系表中插入一条关系记录;

```
@RequestMapping("/articleAdd")
    public String addArticle(@RequestParam("title")String title,@RequestParam("category")String category,
                             @RequestParam("tag")String tag,@RequestParam("describe")String describe,
                             @RequestParam("content")String content){
    Article article = new Article();
    article.setTitle(title);
    article.setDescribes(describe);
    article.setCreatedDate(new Date());
    article.setCommentCount(0);
    article.setContent(JblogUtil.tranfer(content));
    article.setCategory(category);
    int articleId = articleService.addArticle(article);

    String[] tags = tag.split(",");
    for (String t : tags){
        Tag tag1 = tagService.selectByName(t);
        if (tag1==null){
            Tag tag2 = new Tag();
            tag2.setName(t);
            tag2.setCount(1);
            int tagId = tagService.addTag(tag2);

            ArticleTag articleTag = new ArticleTag();
            articleTag.setTagId(tagId);
            articleTag.setArticleId(articleId);
            tagService.addArticleTag(articleTag);
        }else {
            tagService.updateCount(tag1.getId(),tag1.getCount()+1);

            ArticleTag articleTag = new ArticleTag();
            articleTag.setTagId(tag1.getId());
            articleTag.setArticleId(articleId);
            tagService.addArticleTag(articleTag);
        }
    }

    return "redirect:/";
}1234567891011121314151617181920212223242526272829303132333435363738
```

(2) 特定文章所有标签的查询:文章显示时查询此文章所有标签，先通过本文章标签关系表article_tag查询出所有与该文章关联的标签id，再在标签tag表中根据标签id查询出所有关联标签。

```
 @RequestMapping("/article/{articleId}")
    public String singleArticle(Model model, @PathVariable("articleId")int articleId){
 Article article = articleService.getArticleById(articleId);
 List<Tag> tags = tagService.getTagByArticleId(article.getId());
 model.addAttribute("article",article);
 model.addAttribute("tags",tags);
 return "article";
}12345678
```

通过子查询获取特定文章的所有标签

```
    @Select({"select",TAG_FIELDS,"from tag where id in (select tag_id from article_tag where article_id=#{articleId})"})
    List<Tag> selectByArticleId(int articleId);
1234
```

(3) 特定标签所有文章的查询:根据标签对文章分类显示时，通过标签查询所有关联文章。先通过本文章标签关系表article_tag查询出所有与该标签关联的文章id，再在文章article表中根据文章id查询出所有关联文章。

```
 @RequestMapping(value = "/tag/{tagId}",method = RequestMethod.GET)
    public String tag(Model model, @PathVariable("tagId")int tagId, @RequestParam("pageId")int pageId){
        List<Article> articles = articleService.getArticlesByTag(tagId,(pageId-1)*4,4);
        List<ViewObject> vos = new ArrayList<>();
        for (Article article:articles){
            ViewObject vo = new ViewObject();
            List<Tag> tags = tagService.getTagByArticleId(article.getId());
            String clickCount = jedisService.get(RedisKeyUntil.getClickCountKey("/article/"+article.getId()));
            if (clickCount==null)
                clickCount = "0";
            vo.set("clickCount",clickCount);
            vo.set("article",article);
            vo.set("tags",tags);
            vos.add(vo);
    }
    model.addAttribute("vos",vos);
    return "tag";
}123456789101112131415161718
```

通过子查询获取特定文章的所有标签

```
 @Select({"select",ARTICLE_FIELDS,"from article where id in (select article_id from article_tag where tag_id=#{tagId}) limit #{offset},#{limit}"})
    List<Article> selectByTagId(@Param("tagId") int tagId,@Param("offset") int offset, @Param("limit") int limit);12
```

## 3. 标签云功能

标签云的实现主要是通过一个JS插件jquery-tagclould.js实现的。

```
<div class="side tags">
 <div class="header"><i class="fa fa-tags" aria-hidden="true"></i><span>Tags</span></div>
  <div id="tagCloud">
    #foreach($tag in $tags)
    <a href="/tag/$!{tag.id}?pageId=1" rel="$!{tag.count}">$!{tag.name}</a>
    #end
  </div>
</div>12345678
$('#tag').tokenfield();
$('#tagCloud a').tagcloud();
$.fn.tagcloud.defaults = {
    size: {start: 10, end: 18, unit: 'pt'},
    color: {start: '#aaa', end: '#555'}
};123456
```

## 4. 总结

　　博客系统的标签功能是一种典型的数据库多对多情况，通过建立中间关系表将文章和标签关联起来，进而实现特定文章的标签列表查询和特定标签的文章列表查询。



# Spring boot 搭建个人博客系统（六）——文章点击量和阅读排行榜



## 0. 思路

　　文章点击量是记录访问文章页的次数，可以通过Spring拦截器或Spring AOP实现对文章页请求的拦截，每次拦截到请求后增加相应文章的一次点击量。阅读排行榜是对每篇文章的点击量进行排序，取出排序后的点击量排名前10的文章作为热门文章。
　如果使用数据库记录点击次数，每次用户访问文章页需要写一次数据库，当并发量较大的时候写数据库是一种很影响性能的操作。可以在系统中使用Redis作为内存数据库用来存储页面点击量等信息，同时Redis具有丰富的数据结构，其中的Sorted Sets可以用来做根据点击量的热门文章排序。
　

## １. 数据模型

　　系统采用Redis的zset数据结构存储文章点击量，zset是一种有序集合。每个有序集合的成员都关联着一个评分，这个评分用于把有序集合中的成员按最低分到最高分排列。在热门文章排序中，用于有序集合排序的就是每篇文章的点击量。

- 引入Redis的java操作jar包

```
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.8.0</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>1234567
```

- 有序集合zset的操作函数

```
@Service
public class JedisService implements InitializingBean{

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/1");
    }

    public double zincrby(String key,String value){
        Jedis jedis = pool.getResource();
        double result = jedis.zincrby(key,1,value);
        jedis.close();

        return result;
    }

    public Set<String> zrevrange(String key,int start, int end){
        Jedis jedis = pool.getResource();
        Set<String> set = jedis.zrevrange(key,start,end);
        jedis.close();
        return set;
    }
}12345678910111213141516171819202122232425
```

## ２. 文章点击量

利用Spring拦截器拦截多有文章页的请求，并在Redis中修改相应文章页的点击量，用于热门文章排序。

- 实现文章点击量的拦截器

```
@Component
public class ArticleClickInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisService jedisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getServletPath().split("/")[2];
        String uriKey = RedisKeyUntil.getClickCountKey(uri);
        jedisService.zincrby("hotArticles",uriKey);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
```

- 在系统中添加此拦截器

```
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

    @Autowired
    private ArticleClickInterceptor articleClickInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/create");
        registry.addInterceptor(articleClickInterceptor).addPathPatterns("/article/*");
        super.addInterceptors(registry);
    }
}
```

## ３. 热门文章排行榜

利用Redis的有序结合zset存放文章点击量，有序集合可以很方便的获取按照点击量的文章排序。从zset中用函数zrevrange获取点击量最多的10篇文章作为热门文章，显示在主页的热门文章列表。

```
@RequestMapping(path = {"/","/index"})
    public String index(Model model){
   List<ViewObject> vos = new ArrayList<>();
    List<Article> articles = articleService.getLatestArticles(0,4);
    for (Article article:articles){
        ViewObject vo = new ViewObject();
        List<Tag> tags = tagService.getTagByArticleId(article.getId());
        String clickCount = jedisService.get(RedisKeyUntil.getClickCountKey("/article/"+article.getId()));
        if (clickCount==null)
            clickCount = "0";
        vo.set("clickCount",clickCount);
        vo.set("article",article);
        vo.set("tags",tags);
        vos.add(vo);
    }
    model.addAttribute("vos",vos);

    List<Article> hotArticles = new ArrayList<>();
    Set<String> set = jedisService.zrevrange("hotArticles",0,6);
    for (String str : set){
        int articleId = Integer.parseInt(str.split(":")[1]);
        Article article = articleService.getArticleById(articleId);
        hotArticles.add(article);
    }
    model.addAttribute("hotArticles",hotArticles);

    return "index";
}12345678910111213141516171819202122232425262728
```

## ４. 总结

文章点击量的实现可以利用Spring拦截器拦截文章页的访问请求，也可以利用Spring AOP面向切面编程，将改变点击量的代码织入文章页访问请求的controller处理器中。考虑到在并发量很大的时候，如果每次访问页面都要写数据库会很影响系统性能，引入Redis内存型Key-Value数据库存储文章的点击量。同时Redis的丰富的数据结构也为按照点击量排序获取热门文章列表提供了极大的便利。





# 参考

用springboot做一个个人博客

https://blog.csdn.net/u013967175/category_9273727.html

