package com.hmall.common.client;

import com.hmall.common.dto.Item;
import com.hmall.common.dto.PageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "itemservice")
public interface ItemClient {
    @GetMapping("/list")
    /**
     * 分页查询
     */
    PageDTO<Item> page(@RequestParam int page,@RequestParam int size);
    @GetMapping("/{id}")
    /**
     * 根据id查询商品
     */
    public Item getByid(@PathVariable long id);
}
