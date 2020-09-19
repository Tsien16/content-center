package com.tsien.contentcenter.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/20 0020 4:29
 */

public interface MySource {

    String MY_OUTPUT = "my-output";

    /**
     * 自定义消息
     *
     * @return MessageChannel
     */
    @Output(MY_OUTPUT)
    MessageChannel output();
}
