package com.leyou.search.listener;

import com.leyou.search.service.IndexService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听商品变化
 *
 * @author: cooFive
 * @CreateDate: 2018/8/4 13:40
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Component
public class GoodsListener {

    @Autowired
    private IndexService indexService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.item.add.index.queue", durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"item.insert", "item.update"}))
    public void listenAddAndUpdateIndex(Long id) {
        if (id == null) {
            return;
        }

        // 创建或更新索引
        this.indexService.addIndex(id);

    }
}
