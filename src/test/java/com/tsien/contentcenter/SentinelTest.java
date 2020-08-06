package com.tsien.contentcenter;

import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/20 0020 23:51
 */

public class SentinelTest {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 10000; i++) {
            restTemplate.getForObject("http://localhost:8010/actuator/sentinel", String.class);
            Thread.sleep(200);
        }
    }
}
