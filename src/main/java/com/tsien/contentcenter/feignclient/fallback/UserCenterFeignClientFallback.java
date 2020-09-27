package com.tsien.contentcenter.feignclient.fallback;

import com.tsien.contentcenter.domain.dto.user.UserAddBonusDTO;
import com.tsien.contentcenter.domain.dto.user.UserDTO;
import com.tsien.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 2:08
 */

@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient {
    /**
     * 根据ID查找用户
     *
     * @param id id
     * @return UserDTO
     */
    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("一个默认用户");
        return userDTO;
    }

    /**
     * 增加积分
     *
     * @param userAddBonusDTO userAddBonusDTO
     * @return userDTO
     */
    @Override
    public UserDTO addBonus(UserAddBonusDTO userAddBonusDTO) {
        return null;
    }
}
