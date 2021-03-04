package com.baidu.test;

import com.baidu.RunTestEsApplication;
import com.baidu.entity.GoodsEntity;
import com.baidu.repository.GoodsEsRepository;
import com.baidu.utils.HighlightUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunTestEsApplication.class)
public class TestGoodsEs {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private GoodsEsRepository goodsEsRepository;

    //创建索引
    @Test
    public void createIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("lisi"));
        indexOperations.create();
        //indexOperations.exists() 判断索引是否存在
        System.out.println(indexOperations.exists()?"索引创建成功":"索引创建失败");
    }

    //创建映射
    @Test
    public void createGoodsMapping(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
        indexOperations.createMapping();
        System.out.println("映射创建成功"+indexOperations.getMapping());
    }

    //删除索引
    @Test
    public void deleteIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
        indexOperations.delete();
        System.out.println("删除索引成功");
    }

    //新增/修改文档
    @Test
    public void saveGoods(){

        GoodsEntity goods = new GoodsEntity();
        goods.setId(1L);
        goods.setBrand("小米");
        goods.setCategory("手机");
        goods.setImages("xiaomi.jpg");
        goods.setPrice(1000D);
        goods.setTitle("小米3");

        goodsEsRepository.save(goods);

        System.out.println("新增成功");

    }

    //批量新增文档
    @Test
    public void saveAllGoods(){
        GoodsEntity entity = new GoodsEntity();
        entity.setId(2L);
        entity.setBrand("苹果");
        entity.setCategory("手机");
        entity.setImages("pingguo.jpg");
        entity.setPrice(5000D);
        entity.setTitle("iphone11手机");

        GoodsEntity entity2 = new GoodsEntity();
        entity2.setId(3L);
        entity2.setBrand("三星");
        entity2.setCategory("手机");
        entity2.setImages("sanxing.jpg");
        entity2.setPrice(3000D);
        entity2.setTitle("w2019手机");

        GoodsEntity entity3 = new GoodsEntity();
        entity3.setId(4L);
        entity3.setBrand("华为");
        entity3.setCategory("手机");
        entity3.setImages("huawei.jpg");
        entity3.setPrice(4000D);
        entity3.setTitle("华为mate30手机");

        goodsEsRepository.saveAll(Arrays.asList(entity,entity2,entity3));

        System.out.println("新增成功");
    }

    //删除文档
    @Test
    public void deleteGoods(){
        GoodsEntity entity = new GoodsEntity();
        entity.setId(1L);

        goodsEsRepository.delete(entity);

        System.out.println("删除成功");
    }

    //查询所有
    @Test
    public void searchAllGoods(){
        long count = goodsEsRepository.count();
        System.out.println(count+" 个文档---------");

        Iterable<GoodsEntity> all = goodsEsRepository.findAll();
        all.forEach(goodsEntity -> {
            System.out.println(goodsEntity);
        });
    }

    //条件查询
    @Test
    public void conditionSearch(){
        List<GoodsEntity> huawei = goodsEsRepository.findAllByTitle("华为");
        System.err.println(huawei);

        System.out.println("========================");

        List<GoodsEntity> priceBetween = goodsEsRepository.findByAndPriceBetween(4000D, 5000D);
        System.err.println(priceBetween);
    }

    //自定义查询
    @Test
    public void customizeSearch(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //条件查询
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","华为手机"));

        //bool查询
        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","华为手机"))
                        .mustNot(QueryBuilders.matchQuery("brand","三星"))
                        .must(QueryBuilders.rangeQuery("price").gte(3000D).lte(5000D))
                    );
        //排序
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));

        //分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,2));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();
        searchHits.stream().forEach(searchHit -> {
            System.err.println(searchHit);
        });
    }

    //高亮
    @Test
    public void customizeSearchHightLight(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //构建高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field title = new HighlightBuilder.Field("title");
        title.preTags("<span style='color:red'>");
        title.postTags("</span>");
        highlightBuilder.field(title);

        HighlightBuilder.Field brand = new HighlightBuilder.Field("brand");
        brand.preTags("<span style='color:red'>");
        brand.postTags("</span>");
        highlightBuilder.field(brand);

        //高亮设置
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);


        //bool查询
        nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title","华为手机"))
                .must(QueryBuilders.matchQuery("brand","华为"))
                .mustNot(QueryBuilders.matchQuery("brand","三星"))
                .must(QueryBuilders.rangeQuery("price").gte(3000D).lte(5000D))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        List<SearchHit<GoodsEntity>> result = searchHits.stream().map(hit -> {
            Map<String, List<String>> highlightFields = hit.getHighlightFields();

            hit.getContent().setTitle(highlightFields.get("title").get(0));
            hit.getContent().setBrand(highlightFields.get("brand").get(0));
            return hit;
        }).collect(Collectors.toList());

        System.err.println(result);
    }

    //高亮工具类
    @Test
    public void hightLight(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //条件查询

        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","华为手机"))
                        .must(QueryBuilders.matchQuery("brand","华为"))
                        .must(QueryBuilders.rangeQuery("price").gte(3000D).lte(4000D))
        );
        nativeSearchQueryBuilder.withHighlightBuilder(HighlightUtil.getHighlightBuilder("title", "brand"));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();
        List<GoodsEntity> goodsList = HighlightUtil.getHighlightList(searchHits);

        System.out.println(goodsList);
    }

    //聚合为桶
    @Test
    public void searchAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.addAggregation(
                AggregationBuilders.terms("agg_brand")
                        .field("brand")
                        .subAggregation(
                                AggregationBuilders
                                        .max("max_price")
                                        .field("price"))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);
        Aggregations aggregations = search.getAggregations();

        Terms terms = aggregations.get("agg_brand");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + ":" +bucket.getDocCount());

            Aggregations aggregations1 = bucket.getAggregations();

            Map<String, Aggregation> aggMap = aggregations1.asMap();
            aggMap.forEach((key,value) ->{
                Max v = (Max)value;
                System.out.println(key+" : "+v.getValue());
            });
        });

        System.out.println(search);
    }

}










