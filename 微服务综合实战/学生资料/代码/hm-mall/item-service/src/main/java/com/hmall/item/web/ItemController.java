package com.hmall.item.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.common.dto.PageDTO;
import com.hmall.item.pojo.Item;
import com.hmall.item.service.IItemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;


    @GetMapping("/list")
    /**
     * 分页查询
     */
    public PageDTO<Item> page(int page,int size){
        Page<Item> pageresult = itemService.page(new Page<>(page, size));
        return new PageDTO<>(pageresult.getTotal(),pageresult.getRecords());
    }

    @GetMapping("/{id}")
    /**
     * 根据id查询商品
     */
    public Item getByid(@PathVariable long id){
        return itemService.getById(id);
    }
    @PostMapping
    /**
     * 新增商品
     */
    public void save(@RequestBody Item item){
        item.setStatus(2);
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        itemService.save(item);
    }
    @PutMapping("/status/{id}/{status}")
    /**
     * 商品上架、下架
     */
    public void status(@PathVariable long id,@PathVariable Integer status){
        itemService.updateByIds(id, status);

    }
    @PutMapping
    /**
     * 编辑商品
     */
    public void update(@RequestBody Item item){
        item.setStatus(2);//默认下架状态
        item.setUpdateTime(new Date());
        itemService.updateById(item);
    }
    @DeleteMapping("/{id}")
    /**
     * 根据id删除商品
     */
    public void delete(@PathVariable long id){
        itemService.removeById(id);
    }

}
