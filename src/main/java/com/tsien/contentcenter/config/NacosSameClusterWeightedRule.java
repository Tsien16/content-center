package com.tsien.contentcenter.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 11:03
 */

@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }


    @Override
    public Server choose(Object key) {

        try {
            // 拿到配置文件的集群名称
            String clusterName = nacosDiscoveryProperties.getClusterName();

            // ribbon的入口
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) getLoadBalancer();

            // 想要请求的微服务名称
            String name = loadBalancer.getName();

            // 拿到服务发现的相关API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // 1.找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);

            // 2.过滤出相同集群下的所有实例 B
            List<Instance> sameClusterInstances = instances.stream()
                    .filter(instance -> Objects.equals(instance.getClusterName(), clusterName))
                    .collect(Collectors.toList());

            // 3.如果B是空，就用 Ａ
            List<Instance> instancesToBeChosen = new ArrayList<>();
            if (CollectionUtils.isEmpty(sameClusterInstances)) {
                instancesToBeChosen = instances;
                log.warn("发生跨集群的调用，name={}, clusterName={}, instances={}", name, clusterName, instances);
            } else {
                instancesToBeChosen = sameClusterInstances;
            }

            // ４.基于权重的负载均衡算法，返回１个实例
            Instance instance = ExtendBalancer.getMyHostByRandomWeight(instancesToBeChosen);
            log.info("选择的实例是：Port = {}, Instance = {}", instance.getPort(), instance);

            return new NacosServer(instance);

        } catch (NacosException e) {
            log.error("发生异常了", e);
            return null;
        }
    }
}

class ExtendBalancer extends Balancer {

    public static Instance getMyHostByRandomWeight(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }

}
