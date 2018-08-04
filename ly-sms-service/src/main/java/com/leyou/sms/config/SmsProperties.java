package com.leyou.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 16:25
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@ConfigurationProperties(prefix = "ly.sms")
public class SmsProperties {
    /**
     * 你自己的accessKeyId
     */
    private String accessKeyId;
    /**
     * 你自己的AccessKeySecret
     */
    private String accessKeySecret;
    /**
     * 签名名称
     */
    private String signName;
    /**
     * 模板名称
     */
    private String registerCodeTemplate;
    /**
     * 模板名称
     */
    private String loginCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRegisterCodeTemplate() {
        return registerCodeTemplate;
    }

    public void setRegisterCodeTemplate(String registerCodeTemplate) {
        this.registerCodeTemplate = registerCodeTemplate;
    }

    public String getLoginCodeTemplate() {
        return loginCodeTemplate;
    }

    public void setLoginCodeTemplate(String loginCodeTemplate) {
        this.loginCodeTemplate = loginCodeTemplate;
    }
}
