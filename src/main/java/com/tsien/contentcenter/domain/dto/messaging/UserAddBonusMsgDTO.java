package com.tsien.contentcenter.domain.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/3 0003 1:41
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDTO {


    /**
     * 为谁加积分
     */
    private Integer userId;

    /**
     * 加多少积分
     */
    private Integer bonus;
}
