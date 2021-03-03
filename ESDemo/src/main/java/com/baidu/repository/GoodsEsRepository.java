package com.baidu.repository;

import com.baidu.entity.GoodsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GoodsEsRepository extends ElasticsearchRepository<GoodsEntity,Long> {

    List<GoodsEntity> findAllByTitle(String title);

    List<GoodsEntity> findByAndPriceBetween(Double startPrice,Double endPrice);
}
