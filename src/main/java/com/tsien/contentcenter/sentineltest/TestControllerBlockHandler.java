package com.tsien.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 1:52
 */

@Slf4j
public class TestControllerBlockHandler {

    /**
     * 处理限流或者降级
     *
     * @param a a
     * @param e e
     * @return a e
     */
    public static String block(String a, BlockException e) {
        log.warn("限流,或者降级了", e);
        return "限流,或者降级了 block";
    }
}
