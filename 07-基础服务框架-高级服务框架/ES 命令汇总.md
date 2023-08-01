## 分词器

```json
# 自带标准分析器
GET /_analyze
{
  "analyzer": "standard",
  "text": "黑马程序员学习java太棒了"
}

#ik 分析器
GET /_analyze
{
  "analyzer": "ik_max_word",
  "text": "黑马程序员学习java太棒了"
}
```

## 索引

```json
# 创建索引库
PUT /heima
{
  "mappings": {
     "properties": {
        "info":{
        "type": "text",
          "analyzer": "ik_smart"
        },
        "email":{
          "type": "keyword",
          "index": "false"
        },
        "name":{
          "properties": {
            "firstName": {
              "type": "keyword"
            },
            "lastName": {
              "type": "keyword"
            }
          }
        }
      }
  }
}

# 修改索引库
PUT /heima/_mapping
{
  "properties":{
    "age":{
       "type": "integer"
    }
  }
}

# 删除
DELETE /heima

```

## 文档

```json

# 查询
GET /heima/_doc/1


# 修改 /新增 (覆盖)
PUT /heima/_doc/1
{
    "info": "黑马程序员高级Java讲师",
    "email": "zy@itcast.cn",
    "name": {
        "firstName": "云",
        "lastName": "赵"
    }
}
# 新增/新增 (覆盖)
POST /heima/_doc/1
{
  "info": "黑马程序员Java讲师",
  "name": {
    "firstName": "云",
    "lastName": "赵"
    }
}

# 删除
DELETE /heima/_doc/1


# 更新(增量修改)

POST /heima/_update/1
{
  "doc": {
    "email": "ZhaoYun@itcast.cn"
  }
}

```

## day01案例脚本

```json

PUT /hotel
{
  "mappings": {
    "properties": {
      "id": {
        "type": "keyword"
      },
      "name":{
        "type": "text",
        "analyzer": "ik_max_word",
        "copy_to": "all"
      },
      "address":{
        "type": "keyword",
        "index": false
      },
      "price":{
        "type": "integer"
      },
      "score":{
        "type": "integer"
      },
      "brand":{
        "type": "keyword",
        "copy_to": "all"
      },
      "city":{
        "type": "keyword",
        "copy_to": "all"
      },
      "starName":{
        "type": "keyword"
      },
      "business":{
        "type": "keyword"
      },
      "location":{
        "type": "geo_point"
      },
      "pic":{
        "type": "keyword",
        "index": false
      },
      "all":{
        "type": "text",
        "analyzer": "ik_max_word"
      }
    }
  }
}
```

## 查询match/term/range

```json
# day 02 脚本
# 查询结构
GET /hotel

# 查询所有
GET /hotel/_search
{
  "query": {
    "match_all": {}
  }
}
#match 查询
# all 是一个字段, 根据字段查询

GET /hotel/_search
{
  "query": {
    "match": {
      "all": "外滩如家"
    }
  }
}
# multi_match 允许多字段查询
GET /indexName/_search
{
  "query": {
    "multi_match": {
      "query": "TEXT",
      "fields": ["FIELD1", " FIELD12"]
    }
  }
}


GET /hotel/_search
{
  "query": {
    "multi_match": {
      "query": "外滩如家",
      "fields": ["brand", "name","business"]
    }
  }
}
# term 查询
# 格式
GET /indexName/_search
{
  "query": {
    "term": {
      "FIELD": {
        "value": "VALUE"
      }
    }
  }
}

# term 查询
# 精确查询
GET /hotel/_search
{
  "query": {
    "term": {
      "city": {
        "value": "北京"
      }
    }
  }
}
# range查询

GET /indexName/_search
{
  "query": {
    "range": {
      "FIELD": {
        "gte": 10, //# 这里的gte代表大于等于，gt则代表大于
        "lte": 20 // # lte代表小于等于，lt则代表小于
      }
    }
  }
}
# 范围查询 ,适用于数字类型
GET /hotel/_search
{
  "query": {
    "range": {
      "price": {
        "gte": 10, //# 这里的gte代表大于等于，gt则代表大于
        "lte": 200 // # lte代表小于等于，lt则代表小于
      }
    }
  }
}

# geo_bounding_box 搜附近 (矩形)
GET /indexName/_search
{
  "query": {
    "geo_bounding_box": {
      "FIELD": {
        "top_left": { // 左上点
          "lat": 31.1,
          "lon": 121.5
        },
        "bottom_right": { // 右下点
          "lat": 30.9,
          "lon": 121.7
        }
      }
    }
  }
}


# geo_distance 查询 搜附近 原型
GET /indexName/_search
{
  "query": {
    "geo_distance": {
      "distance": "15km", // 半径
      "FIELD": "31.21,121.5" // 圆心
    }
  }
}

GET /hotel/_search
{
  "query": {
    "geo_distance": {
      "distance": "5km", // 半径 km ,m  mi (英里)
      "location": "31.21,121.5" // 圆心
    }
  }
}

```

## 加权

```json
# 结果加权

GET /hotel/_search
{
  "query": {
    "function_score": {
      "query": {
        "match": {
          "all": "外滩"
        }
              }, // 原始查询，可以是任意条件
      "functions": [ // 算分函数
        {
          "filter": { // 满足的条件，品牌必须是如家
            "term": {
              "brand": "如家"
            }
          },
          "weight": 1 // 算分权重为2
        }
      ],
      "boost_mode": "multiply" // 加权模式，sum求和,multiply 乘法
    }
  }
}
```

## bool 查询

```json
# bool 查询
GET /hotel/_search
{
  "query": {
    "bool": {
      "must": [
        {"term": {"city": "上海" }}
       ],
      "should": [
        {"term": {"brand": "皇冠假日" }},
        {"term": {"brand": "华美达" }}
       ],
      "must_not": [
        { "range": { "price": { "lte": 500 } }}
       ],
      "filter": [
        { "range": {"score": { "gte": 45 } }}
       ]
    }
  }
}
# 索名字包含“如家”，价格不高于400，在坐标31.21,121.5周围10km范围内的酒店
GET /hotel/_search
{
  "query": {
    "bool": {
      "must": [
        {"match": {"name": "如家" }}
       ],
      "must_not": [
        { "range": { "price": { "gt": 400 } }}
       ],
      "filter": [
        { 
          "geo_distance": 
          {
            "distance":"10km",
             "location": {
                 "lat": 31.21,
                 "lon": 121.5
              }
          }
        }
       ]
    }
  }
}
```

## 排序

```json
# 排序
GET /hotel/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "score":"desc"
    },
    {
      "price":"asc"
    }
  ]
}


GET /hotel/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "_geo_distance" : {
          "location" : "31.21,121.5",
          "order" : "asc", // 排序方式
          "unit" : "km" // 排序的距离单位
      }
    }
  ]
}
```

## 分页

```json
# 分页
GET /hotel/_search
{
  "query": {
    "match_all": {}
  },
  "from": 0, // 分页开始的位置，默认为0
  "size": 10, // 期望获取的文档总数
  "sort": [
    {"price": "asc"}
  ]
}


#  深度分页 问题
# 每次查询 按照 _id 排序
GET /hotel/_search
{
    "size": 20,
    "query": {
        "match" : {
            "name" : "酒店"
        }
    },
    "sort": [
        {"_id": "asc"}
    ]
}
# 下次查询时 查询 在 id =xxx 之后的10 条
GET /hotel/_search
{
    "size": 10,
    "query": {
        "match" : {
            "name" : "酒店"
        }
    },
    "search_after":[1557997004],
    "sort": [
        {"_id": "asc"}
    ]
}
```

## 高亮

```json
#高亮
GET /hotel/_search
{
  "query": {
    "match": {
      "all" : "酒店"
    }
  },
  "highlight": {
    "fields": {
      "name": {
        "require_field_match": "false",
         "pre_tags": "<en>",
         "post_tags": "<en>"
      }
    }
  }
}
# 高亮 方式2 
GET /hotel/_search
{
  "query": {
    "match": {
      "all" : "酒店"
    }
  },
  "highlight": {
    "require_field_match": "false",
    "fields": {
       "name": {
        "pre_tags": "<en>",
        "post_tags": "<en>"
      }
    }
  }
}
```

## 黑马旅游案例

```json
# 案例, 给数据增加 isAD 字段

POST /hotel/_update/2056126831
{
    "doc": {
        "isAD": true
    }
}
POST /hotel/_update/1989806195
{
    "doc": {
        "isAD": true
    }
}
POST /hotel/_update/2056105938
{
    "doc": {
        "isAD": true
    }
}

```

## 聚合

```json
# 聚合 
GET /hotel/_search
{
  "size": 0,  // 设置size为0，结果中不包含文档，只包含聚合结果
  "aggs": { // 定义聚合
    "brandAgg": { //给聚合起个名字
      "terms": { // 聚合的类型，按照品牌值聚合，所以选择term
        "field": "brand", // 参与聚合的字段
        "size": 20 // 希望获取的聚合结果数量
      }
    }
  }
}

# 聚合排序
GET /hotel/_search
{
  "size": 0, 
  "aggs": {
    "brandAgg": {
      "terms": {
        "field": "brand",
        "order": {
          "_count": "asc" // 按照_count升序排列
        },
        "size": 20
      }
    }
  }
}

# 限定条件聚合
GET /hotel/_search
{
  "query": {
    "range": {
      "price": {
        "lte": 200 // 只对200元以下的文档聚合
      }
    }
  }, 
  "size": 0, 
  "aggs": {
    "brandAgg": {
      "terms": {
        "field": "brand",
        "size": 20
      }
    }
  }
}

# Metric 聚合 
GET /hotel/_search
{
  "size": 0, 
  "aggs": {
    "brandAgg": { 
      "terms": { 
        "field": "brand", 
        "size": 20
      },
      "aggs": { // 是brands聚合的子聚合，也就是分组后对每组分别计算
        "score_stats": { // 聚合名称
          "stats": { // 聚合类型，这里stats可以计算min、max、avg等
            "field": "score" // 聚合字段，这里是score
          }
        }
      }
    }
  }
}

# 多个字段聚合
GET /hotel/_search
{
  "size": 0, 
  "aggs": {
    "brandAgg": {
      "terms": {
        "field": "brand",
        "size": 20
      }
    },
    "cityAgg": {
      "terms": {
        "field": "city",
        "size": 20
      }
    },
    "starAgg": {
      "terms": {
        "field": "star",
        "size": 20
      }
    }
  }
}
```

## 分词器

```json

# 拼音分词器, 分词后只能按照拼音搜索
POST /_analyze
{
  "text": "如家酒店还不错",
  "analyzer": "pinyin"
}

# 创建 test 索引, 并自定义分词器
PUT /test
{
  "settings": {
    "analysis": {
      "analyzer": { 
        "my_analyzer": { 
          "tokenizer": "ik_max_word",
          "filter": "py"
        }
      },
      "filter": {
        "py": { 
          "type": "pinyin",
          "keep_full_pinyin": false, // 是否保留全部拼音
          "keep_joined_full_pinyin": true,
          "keep_original": true,
          "limit_first_letter_length": 16,
          "remove_duplicated_term": true, // 删除重复 
          "none_chinese_pinyin_tokenize": false // 
        }
      }
    }
  }
}

DELETE test

PUT /test
{
  "settings": {
    "analysis": {
      "analyzer": { // 自定义分词器
        "my_analyzer": {  // 分词器名称
          "tokenizer": "ik_max_word",
          "filter": "py"
        }
      },
      "filter": { // 自定义tokenizer filter
        "py": { // 过滤器名称
          "type": "pinyin", // 过滤器类型，这里是pinyin
		      "keep_full_pinyin": false,
          "keep_joined_full_pinyin": true,
          "keep_original": true, // 是否保留原本的汉字
          "limit_first_letter_length": 16,
          "remove_duplicated_term": true, // 移除重复
          "none_chinese_pinyin_tokenize": false
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "text",
        "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart"
      }
    }
  }
}


POST /test/_analyze
{
  "text": "如家酒店还不错",
  "analyzer": "my_analyzer"
}

```

## 自动补全

```json
# 自动补全
PUT test2
{
  "mappings": {
    "properties": {
      "title":{
        "type": "completion"
      }
    }
  }
}

// 示例数据
POST test2/_doc
{
  "title": ["Sony", "WH-1000XM3"]
}
POST test2/_doc
{
  "title": ["SK-II", "PITERA"]
}
POST test2/_doc
{
  "title": ["Nintendo", "switch"]
}

// 自动补全查询
GET /test2/_search
{
  "suggest": {
    "title_suggest": {
      "text": "s", // 关键字
      "completion": {
        "field": "title", // 补全查询的字段
        "skip_duplicates": true, // 跳过重复的
        "size": 10 // 获取前10条结果
      }
    }
  }
}


# 自动补全 
PUT /hotelday03
{
  "settings": {
    "analysis": {
      "analyzer": {
        "text_anlyzer": {
          "tokenizer": "ik_max_word",
          "filter": "py"
        },
        "completion_analyzer": {
          "tokenizer": "keyword",
          "filter": "py"
        }
      },
      "filter": {
        "py": {
          "type": "pinyin",
          "keep_full_pinyin": false,
          "keep_joined_full_pinyin": true,
          "keep_original": true,
          "limit_first_letter_length": 16,
          "remove_duplicated_term": true,
          "none_chinese_pinyin_tokenize": false
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "id":{
        "type": "keyword"
      },
      "name":{
        "type": "text",
        "analyzer": "text_anlyzer",
        "search_analyzer": "ik_smart",
        "copy_to": "all"
      },
      "address":{
        "type": "keyword",
        "index": false
      },
      "price":{
        "type": "integer"
      },
      "score":{
        "type": "integer"
      },
      "brand":{
        "type": "keyword",
        "copy_to": "all"
      },
      "city":{
        "type": "keyword"
      },
      "starName":{
        "type": "keyword"
      },
      "business":{
        "type": "keyword",
        "copy_to": "all"
      },
      "location":{
        "type": "geo_point"
      },
      "pic":{
        "type": "keyword",
        "index": false
      },
      "all":{
        "type": "text",
        "analyzer": "text_anlyzer",
        "search_analyzer": "ik_smart"
      },
      "suggestion":{
          "type": "completion",
          "analyzer": "completion_analyzer"
      }
    }
  }
}


GET /hotelday03/_search
{
  "suggest": {
    "title_suggest": {
      "text": "上", // 关键字
      "completion": {
        "field": "suggestion", // 补全查询的字段
        "skip_duplicates": true, // 跳过重复的
        "size": 10 // 获取前10条结果
      }
    }
  }
}






```

