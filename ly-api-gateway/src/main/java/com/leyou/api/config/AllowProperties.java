package com.leyou.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 白名单
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 20:56
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@ConfigurationProperties(prefix = "ly.filter")
public class AllowProperties {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
