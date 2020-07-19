package com.tsien.contentcenter.feignclient;

import com.tsien.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 13:51
 */

@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {

    /**
     * 测试
     * @param userDTO userDTO
     * @return userDTO
     */
    @GetMapping("/q")
    UserDTO query(@SpringQueryMap UserDTO userDTO);
}
