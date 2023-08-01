package com.heima.article.stream;

import com.alibaba.fastjson.JSON;
import com.heima.common.constants.HotArticleConstants;
import com.heima.model.mess.ArticleVisitStreamMess;
import com.heima.model.mess.UpdateArticleMess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class HotArticleStreamHandler {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        //1.接收消息
        KStream<String, String> stream =
                streamsBuilder.stream(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC);

        //2.聚合流式处理
        stream.map((key, value) -> {
            //将json字符串转成对象
            UpdateArticleMess mess = JSON.parseObject(value, UpdateArticleMess.class);
            System.out.println("接收到消息：" + mess);

            //重置消息的key和value: likes:1
            //key = 文章的ID， value = 操作类型:likes
            return new KeyValue<>(
                    mess.getArticleId().toString(), //key
                    mess.getType().name() + ":" + mess.getAdd()); //value
        }).groupBy((key, value) -> key) //按照文章id进行聚合
                .windowedBy(TimeWindows.of(Duration.ofSeconds(30))) //时间窗口
        .aggregate(new Initializer<String>() {
            @Override
            public String apply() {
                //初始化指定数据格式
                ArticleVisitStreamMess mess = new ArticleVisitStreamMess();
                return JSON.toJSONString(mess);
            }
        }, new Aggregator<String, String, String>() {
            @Override
            public String apply(String key, String value, String aggregateValue) {
                if (StringUtils.isBlank(value)) {
                    //如果value中没有用户行为，直接返回上一次聚合的结果
                    return aggregateValue;
                }
                //取出上一次聚合的结果
                ArticleVisitStreamMess mess =
                        JSON.parseObject(aggregateValue, ArticleVisitStreamMess.class);
                if (mess.getArticleId() == null) {
                    //如果是第一个进行聚合，从key中取出文章id
                    mess.setArticleId(Long.valueOf(key));
                }

                /**
                 * 进行累加：当前消息 + 上一次聚合结果
                 */
                //从value中取出用户行为+增量: [LIKES, 1]
                String[] split = value.split(":");

                switch (UpdateArticleMess.UpdateArticleType.valueOf(split[0])) {
                    case LIKES:
                        mess.setLike(mess.getLike() + Integer.parseInt(split[1]));
                        break;
                    case VIEWS:
                        mess.setView(mess.getView() + Integer.parseInt(split[1]));
                        break;
                    case COMMENT:
                        mess.setComment(mess.getComment() + Integer.parseInt(split[1]));
                        break;
                    case COLLECTION:
                        mess.setCollect(mess.getCollect() + Integer.parseInt(split[1]));
                        break;
                }
                System.out.println("聚合的结果：" + mess);
                return JSON.toJSONString(mess);
            }
        }, Materialized.as("hot-atricle-stream-count-001"))
        .toStream()
        .map((key, value) -> {
            System.out.println("key: " + key.key() + ", value: " + value);
            return new KeyValue<>(key.key(), value);
        })
        .to(HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC);
        //3.发送消息
        return stream;
    }
}