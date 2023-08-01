package com.hmall.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.item.mapper.ItemMapper;
import com.hmall.item.pojo.Item;
import com.hmall.item.service.IItemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public void updateByIds(long id, Integer status) {
        Item item = new Item();
        item.setId(id);
        item.setStatus(status); //  1-正常，2-下架，3-删除
        item.setUpdateTime(new Date());
        // mybatisplus:  updateById 方法,只会更新 不为空的字段
        this.updateById(item);
        String routingKey="item.up";
        if(status!=1){
            routingKey="item.down";
        }
        rabbitTemplate.convertAndSend("item.topic",routingKey,id);
    }
}
