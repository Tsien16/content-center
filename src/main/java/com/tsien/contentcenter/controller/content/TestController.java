package com.tsien.contentcenter.controller.content;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tsien.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.tsien.contentcenter.rocketmq.MySource;
import com.tsien.contentcenter.sentineltest.TestControllerBlockHandler;
import com.tsien.contentcenter.sentineltest.TestControllerFallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 1:17
 */

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test-sentinel-api")
    public String testSentinelApi(@RequestParam(required = false) String a) {
        // 定义一个sentinel保护的资源,名称是test-sentinel-api
        Entry entry = null;
        try {
            entry = SphU.entry("test-sentinel-api");
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a 不能为空");
            }
            return a;
        }
        // 如果被保护的资源被限流或者降级了,就会抛出BlockException异常
        catch (BlockException e) {
            log.warn("限流,或者降级了", e);
            return "限流,或者降级了";
        } catch (IllegalArgumentException e2) {
            Tracer.trace(e2);
            return "参数非法";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }


    @GetMapping("/test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-resource",
            blockHandlerClass = TestControllerBlockHandler.class,
            fallbackClass = TestControllerFallback.class)
    public String testSentinelResource(@RequestParam(required = false) String a) {

        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a 不能为空");
        }
        return a;
    }

    @Resource
    private Source source;

    @GetMapping("/test-stream")
    public String testStream() {
        source.output().send(
                MessageBuilder.withPayload(UserAddBonusMsgDTO.builder().userId(1).bonus(100).build())
                        .build()
        );

        return "success";
    }

    @Resource
    private MySource mySource;

    @GetMapping("/test-stream-2")
    public String testStream2() {
        mySource.output().send(
                MessageBuilder.withPayload("消息体")
                        .build()
        );

        return "success";
    }


}
