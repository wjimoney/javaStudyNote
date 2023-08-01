package com.hmall.search.service;

import com.hmall.common.dto.PageDTO;
import com.hmall.search.pojo.ItemDoc;
import com.hmall.search.pojo.RequestParams;

import java.util.List;
import java.util.Map;

public interface ISearchService {

    List<String> getsuggestion(String key);

    Map<String,List<String>> getfilters(RequestParams requestParams);


    PageDTO<ItemDoc> getlist(RequestParams requestParams);
}
