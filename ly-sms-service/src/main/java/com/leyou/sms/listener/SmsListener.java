package com.leyou.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听短信发送
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 16:17
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    @Autowired
    private SmsProperties props;

    @Autowired
    private SmsUtils smsUtils;

    static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "ly.sms.register.code.queue", durable = "true"),
            exchange = @Exchange(value = "ly.sms.exchange", ignoreDeclarationExceptions = "true"),
            key = "sms.register.code"))
    public void sendRegisterCodeMsg(Map<String, String> msg) {
        String phone = msg.get("phone");
        String code = msg.get("code");
        try {
            if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)) {
                smsUtils.sendSms(phone, code, props.getSignName(), props.getRegisterCodeTemplate());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            // 因为这里发送短信不作重试，故用try包裹
            logger.error("注册短信发送失败，phone：{}，code：{}", phone, code);
        }
    }
}
