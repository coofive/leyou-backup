package com.leyou.api.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * jwt配置文件
 *
 * @author: cooFive
 * @CreateDate: 2018/8/6 11:31
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@ConfigurationProperties(prefix = "ly.jwt")
@Slf4j
@Data
public class JwtProperties {
    /**
     * 公钥
     */
    private String pubKeyPath;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * cookie名称
     */
    private String cookieName;

    @PostConstruct
    public void init() {
        try {
            // 获取公钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("读取公钥失败", e);
            e.printStackTrace();
        }
    }

}
