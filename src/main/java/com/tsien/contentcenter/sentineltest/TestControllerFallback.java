package com.tsien.contentcenter.sentineltest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 1:54
 */

public class TestControllerFallback {

    /**
     * 可以处理Throwable
     *
     * @param a a
     * @return a
     */
    public static String fallback(String a) {
        return "限流,或者降级了 fallback";
    }

}
