package ribbonconfig;

import com.netflix.loadbalancer.IRule;
import com.tsien.contentcenter.config.NacosSameClusterWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 4:03
 */

@Configuration
public class RibbonConfig {

    @Bean
    public IRule ribbonRule() {
        return new NacosSameClusterWeightedRule();
    }
}
