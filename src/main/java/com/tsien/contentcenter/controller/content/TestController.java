package com.tsien.contentcenter.controller.content;

import com.tsien.contentcenter.domain.dto.user.UserDTO;
import com.tsien.contentcenter.feignclient.TestUserCenterFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 17:17
 */

@RestController
public class TestController {

    @Resource
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @GetMapping("test-get")
    public UserDTO query(UserDTO userDTO) {
        return testUserCenterFeignClient.query(userDTO);
    }
}
