package com.easyray.teamprovider;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.wyy.*", "com.easyray.*"})
@DubboComponentScan(basePackages = {"com.easyray.*"})
//必须指定只扫描Mapper.class，否者会扫描2次spring的component，导致重复而报错
@MapperScan(annotationClass = Mapper.class, basePackages = {"com.easyray.*"})
@EnableTransactionManagement
public class TeamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamProviderApplication.class, args);
    }

}
