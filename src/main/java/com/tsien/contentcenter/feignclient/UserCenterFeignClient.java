package com.tsien.contentcenter.feignclient;

import com.tsien.contentcenter.config.UserCenterFeignConfig;
import com.tsien.contentcenter.domain.dto.user.UserDTO;
import com.tsien.contentcenter.feignclient.fallback.UserCenterFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 12:45
 */

//@FeignClient(name = "user-center", configuration = UserCenterFeignConfig.class)
@FeignClient(name = "user-center", fallback = UserCenterFeignClientFallback.class)
public interface UserCenterFeignClient {

    /**
     * 根据ID查找用户
     *
     * @param id id
     * @return UserDTO
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
