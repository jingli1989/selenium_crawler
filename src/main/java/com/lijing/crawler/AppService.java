package com.lijing.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务启动类
 * Created by Lijing on 2017/2/21.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, //（如果使用Hibernate时，需要加）
        DataSourceTransactionManagerAutoConfiguration.class,
})
@RestController
@ImportResource({"classpath:spring.xml"})
@RequestMapping(value = "/app")
public class AppService {

    @ResponseBody
    @RequestMapping(value = "/check")
    public String testConnection(){
        return "success";
    }

    public static void main(String[] args) {
        final ApplicationContext applicationContext = SpringApplication.run(AppService.class, args);
    }
}
