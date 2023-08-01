package com.hmall.search.web;

import com.hmall.common.dto.PageDTO;
import com.hmall.search.pojo.ItemDoc;
import com.hmall.search.pojo.RequestParams;
import com.hmall.search.service.ISearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    public ISearchService searchService;

    @GetMapping("/suggestion")
    /**
     * @description:自动补全查询
     * @return: java.util.List<java.lang.String>
     * @Created by: wangjie
     * @time: 2023/2/5 20:42
     */
    public List<String> suggestion(String key){
        return searchService.getsuggestion(key);
    }
    @PostMapping("/filters")
    /**
     * @description:过滤项聚合查询
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @Created by: wangjie
     * @time: 2023/2/6 9:52
     */
    public Map<String,List<String>> filter(@RequestBody RequestParams requestParams){
        return searchService.getfilters(requestParams);
    }
    @PostMapping("/list")
    /**
     * @description:过滤项聚合查询
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @Created by: wangjie
     * @time: 2023/2/6 9:52
     */
    public PageDTO<ItemDoc> list(@RequestBody RequestParams requestParams){
        return searchService.getlist(requestParams);
    }
}
