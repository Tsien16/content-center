package com.tsien.contentcenter.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 13:12
 */

public class UserCenterFeignConfig {

    @Bean
    public Logger.Level level() {
        // 让Feign打印所有请求的细节
        return Logger.Level.FULL;
    }
}
