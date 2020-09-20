package com.tsien.contentcenter.domain.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/20 0020 2:27
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RocketmqTransactionLog {
    /**
     * id
     */
    private Integer id;

    /**
     * 事务id
     */
    private String transactionId;

    /**
     * 日志
     */
    private String log;
}