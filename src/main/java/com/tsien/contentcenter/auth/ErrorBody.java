package com.tsien.contentcenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/26 0026 15:26
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBody {

    /**
     * 消息体
     */
    private String body;

    /**
     * 状态码
     */
    private Integer status;
}
