package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.config.JwtConfig;
import com.baidu.shop.dto.Car;
import com.baidu.shop.dto.UserInfo;
import com.baidu.shop.entity.SkuEntity;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.service.CarService;
import com.baidu.shop.utils.JSONUtil;
import com.baidu.shop.utils.JwtUtils;
import com.baidu.shop.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class CarServiceImpl extends BaseApiService implements CarService {

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private GoodsFeign goodsFeign;

    private final String GOODS_CAR_PRE = "goods-car-";

    private final Integer Good_CAR_ADD = 1;

    @Override
    public Result<JSONObject> operationGoods(String token, Long skuId, Integer type) {

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            Car carRedis = redisRepository.getHash(GOODS_CAR_PRE + userInfo.getId(), skuId + "", Car.class);

            if(ObjectUtil.isNull(carRedis)) return this.setResultError("没有选择商品");
            carRedis.setNum( type == Good_CAR_ADD ? carRedis.getNum() + 1 : carRedis.getNum() -1 );

            redisRepository.setHash(GOODS_CAR_PRE + userInfo.getId(),skuId + "",JSONUtil.toJsonString(carRedis));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.setResultSuccess();
    }

    @Override
    public Result<List<Car>> getUserCar(String token) {

        List<Car> cars = new ArrayList<>();
        try {
            //获取当前用户信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            //通过登录用户的id从redis获取购物车数据
            Map<String, String> map = redisRepository.getHash(GOODS_CAR_PRE + userInfo.getId());

            map.forEach((key,value) -> cars.add(JSONUtil.toBean(value,Car.class)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.setResultSuccess(cars);
    }

    @Override
    public Result<JSONObject> mergeCar(String carList, String token) {

        //将字符串转为json类型
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(carList);
        //通过key获取数据 json类型的数组
        JSONArray carListArray = jsonObject.getJSONArray("carList");
        //将json数组转换为list集合
        List<Car> carsList = carListArray.toJavaList(Car.class);

        carsList.stream().forEach(car -> this.addCar(car,token));
        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> addCar(Car car, String token) {

        //redis Hash Map<userId, Map<skuId, goods>> map = new HashMap<>();
        try {

            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            //通过用户id和skuId查询用户信息
            Car redisCar = redisRepository.getHash(GOODS_CAR_PRE + userInfo.getId(), car.getSkuId() + "", Car.class);
            log.info("通过用户id : {} ,skuId : {} 从redis中获取到的数据为 : {}",userInfo.getId(),car.getSkuId(),redisCar);

            if(ObjectUtil.isNotNull(redisCar)){//

                redisCar.setNum(redisCar.getNum() + car.getNum());
                redisRepository.setHash(GOODS_CAR_PRE + userInfo.getId(),car.getSkuId() + "", JSONUtil.toJsonString(redisCar));
                log.info("redis中有当前商品 , 重新设置redis中该商品的数量 : {}",redisCar.getNum());
            }else{//购物车中没有该商品
                System.out.println(car);
                //skuEntity.getOwnSpec() 转换成map --> 遍历map --> 通过key查询spec_param表中的name值 --> 重新拼一个map集合 --> 将map集合转string字符串
                Result<SkuEntity> skuResult = goodsFeign.getSKuById(car.getSkuId());
                if(skuResult.isSuccess()){

                    SkuEntity skuEntity = skuResult.getData();
                    car.setTitle(skuEntity.getTitle());
                    car.setImage(StringUtils.isEmpty(skuEntity.getImages()) ? "" : skuEntity.getImages().split(",")[0]);
                    car.setPrice(skuEntity.getPrice().longValue());

                    car.setOwnSpec(skuEntity.getOwnSpec());

                    redisRepository.setHash(GOODS_CAR_PRE + userInfo.getId(),car.getSkuId() + "",JSONUtil.toJsonString(car));
                    log.info("redis中没有当前商品 , 新增商品到购物车中 userId : {} , skuId : {} ,商品数据 : {}",userInfo.getId(),car.getSkuId(),JSONUtil.toJsonString(car));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.setResultSuccess();
    }
}
