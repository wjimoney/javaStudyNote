package com.hmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmall.common.client.ItemClient;
import com.hmall.common.dto.Item;
import com.hmall.common.dto.PageDTO;
import com.hmall.search.pojo.ItemDoc;
import com.hmall.search.pojo.RequestParams;
import com.hmall.search.service.ISearchService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queries.function.FunctionScoreQuery;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService implements ISearchService {
    @Autowired
    private RestHighLevelClient client;

    @Override
    /**
     * @description:自动补全查询
     * @return: java.util.List<java.lang.String>
     * @Created by: wangjie
     * @time: 2023/2/5 20:49
     */
    public List<String> getsuggestion(String key) {
        try {
            SearchRequest request = new SearchRequest("hmall");
            request.source().suggest(new SuggestBuilder().addSuggestion("suggestions",
                    SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(key)
                            .skipDuplicates(true)
                            .size(10)));
            SearchResponse search = client.search(request, RequestOptions.DEFAULT);
            //解析结果
            Suggest suggest = search.getSuggest();
            //根据补全查询名称获取结果
            CompletionSuggestion suggestion = suggest.getSuggestion("suggestion");
            List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
            List<String> list = new ArrayList<>();
            for (CompletionSuggestion.Entry.Option option : options) {
                String text = option.getText().toString();
                list.add(text);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    /**
     * @description:聚合查询
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @Created by: wangjie
     * @time: 2023/2/5 20:49
     */
    public Map<String, List<String>> getfilters(RequestParams requestParams) {
        try {
            SearchRequest request = new SearchRequest("hmall");
            //查询条件
            getfunctionScoreQuery(requestParams, request);
            //size
            request.source().size(0);
            //聚合
            getAggregation(request);
            //发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //解析结果
            Map<String, List<String>> map = new HashMap<>();
            List<String> brand_agg = extracted(response, "brand_agg");
            map.put("brand",brand_agg);
            List<String> category_agg = extracted(response, "category_agg");
            map.put("category",category_agg);
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageDTO<ItemDoc> getlist(RequestParams requestParams) {
        try {
            SearchRequest request = new SearchRequest("hmall");
            //查询条件
            getfunctionScoreQuery(requestParams, request);
            //分页
            Integer page = requestParams.getPage();
            Integer size = requestParams.getSize();
            request.source().from((page-1)*size).size(size);
            //排序
            if(!"default".equals(requestParams.getSortBy())&&requestParams.getSortBy()!=null){
                request.source().sort(requestParams.getSortBy(),SortOrder.DESC);
            }
            //发送请求
            SearchResponse search = client.search(request, RequestOptions.DEFAULT);
            //解析结果
            PageDTO pageDTO = handleResponse(search);
            return pageDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> extracted(SearchResponse response, String aggName) {
        Aggregations aggregations = response.getAggregations();
        //根据聚合名称获取聚合结果
        Terms brand_agg = aggregations.get(aggName);
        //根据桶获取
        List<? extends Terms.Bucket> buckets = brand_agg.getBuckets();
        //遍历
        ArrayList<String> list = new ArrayList<>(buckets.size());
        for (Terms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString();
            list.add(keyAsString);
        }
        return list;
    }

    private void getAggregation(SearchRequest request) {
        //品牌聚合
        request.source().aggregation(AggregationBuilders
                .terms("brand_agg")
                .field("brand")
                .size(20));
        //分类聚合
        request.source().aggregation(AggregationBuilders
                .terms("category_agg")
                .field("category")
                .size(20));
    }

    private void getfunctionScoreQuery(RequestParams requestParams, SearchRequest request) {
        //关键字查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String key = requestParams.getKey();
        //判断key是否为空
        if(StringUtils.isBlank(key)){
            //为空,查询查询所有
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }else {
            //不为空,查询all字段
            boolQueryBuilder.must(QueryBuilders.matchQuery("all",key));
        }
        //判断品牌是否为空 用filter
        String brand = requestParams.getBrand();
        if(!StringUtils.isBlank(brand)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("brand",brand));
        }
        //判断分类是否为空
        String category = requestParams.getCategory();
        if (!StringUtils.isBlank(category)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("category",category));
        }
        //判断价格范围
        Long maxPrice = requestParams.getMaxPrice();
        Long minPrice = requestParams.getMinPrice();
        if(maxPrice!=null&&minPrice!=null){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(maxPrice).lte(minPrice));
        }
        //算分函数查询
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(boolQueryBuilder, new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("isAD", true),
                        ScoreFunctionBuilders.weightFactorFunction(10))
        });
        request.source().query(functionScoreQuery);
    }
    private PageDTO handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        // 4.1.总条数
        long total = searchHits.getTotalHits().value;
        // 4.2.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        List<ItemDoc> hotels = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            // 4.4.获取source
            String json = hit.getSourceAsString();
            // 4.5.反序列化，非高亮的
            ItemDoc hotelDoc = JSON.parseObject(json, ItemDoc.class);
            // 4.6.处理高亮结果
            // 1)获取高亮map
            Map<String, HighlightField> map = hit.getHighlightFields();
            if (map != null && !map.isEmpty()) {
                // 2）根据字段名，获取高亮结果
                HighlightField highlightField = map.get("name");
                if (highlightField != null) {
                    // 3）获取高亮结果字符串数组中的第1个元素
                    String hName = highlightField.getFragments()[0].toString();
                    // 4）把高亮结果放到HotelDoc中
                    hotelDoc.setName(hName);
                }
            }
            // 4.9.放入集合
            hotels.add(hotelDoc);
        }
        return new PageDTO(total, hotels);
    }
}
