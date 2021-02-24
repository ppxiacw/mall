package com.atguigu.elasticsearch;

import com.atguigu.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.directory.SearchResult;
import java.io.IOException;

@SpringBootTest
class ElasticsearchApplicationTests {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void searchdata() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user","kimchy"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(search.toString());
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for(SearchHit hit : hits1){
            System.out.println(hit);
        }

    }

    @Test
    void contextLoads() throws IOException {
        IndexRequest user = new IndexRequest("user");
        user.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        user.source(jsonString, XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(user, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

}
