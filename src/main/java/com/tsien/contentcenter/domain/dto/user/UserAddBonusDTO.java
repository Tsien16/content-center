package com.tsien.contentcenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/27 0027 15:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusDTO {

    /**
     * userId
     */
    private Integer userId;

    /**
     * 积分
     */
    private Integer bonus;

}
