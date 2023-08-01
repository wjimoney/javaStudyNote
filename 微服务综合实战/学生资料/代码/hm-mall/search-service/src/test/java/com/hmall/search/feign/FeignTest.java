package com.hmall.search.feign;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmall.common.client.ItemClient;
import com.hmall.common.dto.Item;
import com.hmall.common.dto.PageDTO;
import com.hmall.search.pojo.ItemDoc;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeignTest {

    @Autowired
    private ItemClient itemClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void test() throws IOException {
        int page=1;
        while (true) {
            //用feign远程调用分页查询商品数据
            PageDTO<Item> page1 = itemClient.page(page, 500);
            List<Item> list = page1.getList();
            //判断数据是否查完
            if(list==null){
                //没有数据就结束
                break;
            }
            //把数据保存到es,批量导入
            BulkRequest bulkRequest = new BulkRequest();
            for (Item item : list) {
                if(item.getStatus()==2){
                    continue;
                }
                ItemDoc itemDoc = new ItemDoc(item);
                bulkRequest.add(new IndexRequest("hmall_item")
                        .id(item.getId().toString())
                        .source(JSON.toJSONString(itemDoc),XContentType.JSON));
            }
           restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            page++;
        }
    }
/*
    @Test
    public void testQueryItem() throws IOException {
        // http://itemservice/item/list?page=1&size=5
        int page = 1;
        while (true) {
            PageDTO<Item> pageDTO = itemClient.queryItemByPage(page, 500);
            List<Item> list = pageDTO.getList();
            if (list.size() <= 0) {
                break;
            }
            // 1.准备BulkRequest
            BulkRequest request = new BulkRequest();
            // 2.准备DSL
            // 遍历
            for (Item item : list) {
                if(item.getStatus() == 2){
                    // 下架商品直接跳过
                    continue;
                }
                // 把 Item 转为 ItemDoc
                ItemDoc itemDoc = new ItemDoc(item);
                // 添加新增请求
                request.add(new IndexRequest("item")
                        .id(itemDoc.getId().toString())
                        .source(MAPPER.writeValueAsString(itemDoc), XContentType.JSON)
                );
            }
            // 3.发请求，批量处理
            BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

            page++;
        }
    }*/
}
