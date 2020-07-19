package com.tsien.contentcenter;

import com.tsien.contentcenter.config.GlobalFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 2:29
 */

@SpringBootApplication
@MapperScan("com.tsien.contentcenter.dao")
@EnableFeignClients(defaultConfiguration = GlobalFeignConfig.class)
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

}
