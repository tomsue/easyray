#### 模块说明
1. easyray-framework-core  
    框架核心模块
1. easyray-framework-component  
    框架层组件
1. easyray-framework-util   
    框架层工具
1. easyray-framework-sample  
    第三方组件使用示例        
1. scripts  
    启动服务脚本
#### 框架使用介绍
框架设计为4层结构  
    * api层提供基础接口（interface）  
    * provider层提供基础service（dubbo），操作数据库，权限底层检查  
    * 业务层(consumer)做业务操作，提供service  
    * controller对外提供接口（rest service）  
#### 注意事项
1. 所有的依赖关系全部从根pom文件查找，禁止模块引入特殊依赖
#### 开发步骤
1. 创建api模块（maven）
    1. api模块添加依赖(根据需要)
        ```
        <dependency>
            <groupId>com.easyray</groupId>
            <artifactId>easyray-framework-core-baseapi</artifactId>
            <version>1.0.0</version>
        </dependency>
       <dependency>
           <groupId>com.wyy</groupId>
           <artifactId>actable</artifactId>
           <scope>provided</scope>
       </dependency>
       <dependency>
           <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-boot-starter</artifactId>
           <scope>provided</scope>
       </dependency>
        ```
1. 创建provider模块（springboot）
    1. provider模块修改pom修改parent(参考api模块的parent)
    1. provider模块修改依赖
        ```
        <dependency>
            <!-- Import dependency management from Spring Boot -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    
       <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
       <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>druid</artifactId>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <scope>runtime</scope>
       </dependency>
       <dependency>
           <groupId>com.wyy</groupId>
           <artifactId>actable</artifactId>
       </dependency>
       
        <!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-registry-nacos -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-registry-nacos</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-spring-boot-starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
        </dependency>
    
       <dependency>
           <groupId>com.easyray</groupId>
           <artifactId>easyray-framework-core-properties</artifactId>
           <version>1.0.0</version>
       </dependency>
       <dependency>
           <groupId>com.easyray</groupId>
           <artifactId>easyray-framework-core-extension</artifactId>
           <version>1.0.0</version>
       </dependency>
        ```   
    1. 添加api层的依赖
    1. provider模块添加组件扫描
        ```
        @SpringBootApplication(scanBasePackages = {"com.wyy.*", "com.easyray.*"})
        @DubboComponentScan(basePackages = {"com.easyray.*"})
        //必须指定只扫描Mapper.class，否者会扫描2次spring的component，导致重复而报错
        @MapperScan(annotationClass = Mapper.class, basePackages = {"com.easyray.*"})
        ```    
    1. 修改配置文件
       ```
       #server port
       server:
         port: ${random.int[1000,9999]}
       
       dubbo:
         application:
           name: @project.name@
       
       spring:
         profiles:
           active: common,default
       ```
1. 创建service模块
    1. 添加依赖
        ```
       <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-context</artifactId>
           <scope>provided</scope>
       </dependency>

       <!-- https://mvnrepository.com/artifact/com.alibaba.boot/dubbo-spring-boot-starter -->
       <dependency>
           <groupId>com.alibaba.boot</groupId>
           <artifactId>dubbo-spring-boot-starter</artifactId>
           <exclusions>
               <exclusion>
                   <artifactId>spring-boot-starter</artifactId>
                   <groupId>org.springframework.boot</groupId>
               </exclusion>
           </exclusions>
       </dependency>
       <dependency>
           <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-extension</artifactId>
       </dependency>
       ```  
    1. 添加对api模块的依赖
1. 创建web模块（springboot）
    1. 修改注解
        ```
       @SpringBootApplication(scanBasePackages = "com.easyray.*")
       @DubboComponentScan(basePackages = {"com.easyray.*"})
       ```
    1. 添加依赖
        ```
       <dependency>
            <!-- Import dependency management from Spring Boot -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
   
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
   
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
       
       <dependency>
           <groupId>com.easyray</groupId>
           <artifactId>easyray-framework-core-properties</artifactId>
           <version>1.0.0</version>
       </dependency>
       ```    
    1. 添加认证模块（如果需要）
        1. 添加依赖
           ```
           <dependency>
                <groupId>com.easyray</groupId>
                <artifactId>easyray-framework-core-auth</artifactId>
                <version>1.0.0</version>
            </dependency>
           ```  
    1. 添加配置
        ```
       server:
         servlet:
           context-path: 
         port: 7003
       
       dubbo:
         application:
           name: @project.name@
       
       spring:
         profiles:
           active: common,default
       ```  
#### todo
1. ~~url白名单~~ 20200610       
1. ~~auth组件对web模块的请求做拦截~~ 20200610  
1. ~~auth组件中实现dubbo跨服务器之间的认证上下文环境初始化~~ 20200610
1. 字典模块开发
1. ~~redis组件开发~~ 20200611（添加redis工具类）
1. 登录退出登录日志记录
1. web请求拦截，日志记录组件与模块开发   
1. ~~短信验证码登录~~ 20200611
1. 发短信组件开发     
1. ~~日志打印~~ 20200629        
