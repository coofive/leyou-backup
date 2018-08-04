package com.leyou.page.listener;

import com.leyou.page.service.FileService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听商品变化，更改静态页
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 13:53
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Component
public class GoodsListener {

    @Autowired
    private FileService fileService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "ly.item.add.page.queue", durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void listenAddAndUpdateThymeleafPage(Long spuId) {
        if (spuId == null) {
            return;
        }

        try {
            this.fileService.createHtml(spuId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加静态页失败");
        }
    }
}
