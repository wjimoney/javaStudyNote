package com.hmall.search.mq;

import com.alibaba.fastjson.JSON;
import com.hmall.common.client.ItemClient;
import com.hmall.common.dto.Item;
import com.hmall.search.service.ISearchService;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.events.Event;

import java.io.IOException;

@Component
public class ItemListener {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ItemClient itemClient;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.up.queue"),
            exchange = @Exchange(name = "item.topic", type = ExchangeTypes.TOPIC),
            key = "item.up"
    ))
    /**
     * @description:商品上架监听器
     * @return: void
     * @Created by: wangjie
     * @time: 2023/2/6 11:16
     */
    public void itemlistenup(Long id){
        try {
            Item byid = itemClient.getByid(id);
            String jsonString = JSON.toJSONString(byid);
            IndexRequest request = new IndexRequest("hmall");
            request.source(jsonString, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "item.down.queue"),
            exchange = @Exchange(name = "item.topic", type = ExchangeTypes.TOPIC),
            key = "item.down"
    ))
    /**
     * @description:商品下架监听器
     * @return: void
     * @Created by: wangjie
     * @time: 2023/2/6 11:16
     */
    public void itemlistendown(Long id) throws IOException {
        DeleteRequest request = new DeleteRequest("hmall").id(id.toString());
        client.delete(request,RequestOptions.DEFAULT);
    }
}
