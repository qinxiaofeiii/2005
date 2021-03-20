package com.baidu.shop.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.business.OrderService;
import com.baidu.shop.config.JwtConfig;
import com.baidu.shop.dto.*;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.AddressMapper;
import com.baidu.shop.mapper.OrderDetailMapper;
import com.baidu.shop.mapper.OrderMapper;
import com.baidu.shop.mapper.OrderStatusMapper;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderServiceImpl extends BaseApiService implements OrderService {

    private final String GOODS_CAR_PRE = "goods-car-";

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;
    @Resource
    private AddressMapper addressMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private JwtConfig jwtConfig;
    @Resource
    private RedisRepository redisRepository;

    @Override
    public Result<List<OrderInfo>> getOrderInfo(String token,Integer page,Integer rows) {

        if(ObjectUtil.isNotNull(page) && ObjectUtil.isNotNull(rows))
            PageHelper.startPage(page,rows);
        List<OrderInfo> OrderInfoList = null;
        List<OrderEntity> orderList = null;
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            Example example = new Example(OrderEntity.class);
            example.createCriteria().andEqualTo("userId",userInfo.getId() + "");
            orderList = orderMapper.selectByExample(example);

            OrderInfoList = orderList.stream().map(order -> {
                //order信息
                OrderInfo orderInfo = BaiduBeanUtil.copyProperties(order, OrderInfo.class);

                //orderDetail信息
                Example example1 = new Example(OrderDetailEntity.class);
                example1.createCriteria().andEqualTo("orderId", orderInfo.getOrderId().longValue());
                List<OrderDetailEntity> orderDetailList = orderDetailMapper.selectByExample(example1);
                orderInfo.setOrderDetailList(orderDetailList);

                //orderStatus信息
                OrderStatusEntity orderStatusEntity = orderStatusMapper.selectByPrimaryKey(orderInfo.getOrderId());
                orderInfo.setOrderStatusEntity(orderStatusEntity);

                return orderInfo;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageInfo<OrderEntity> pageInfo = new PageInfo<>(orderList);
        return this.setResult(HTTPStatus.OK,pageInfo.getTotal() +  "",OrderInfoList);
    }

    //用户地址回显
    @Override
    public Result<AddressEntity> editAddress(Integer id) {
        if (ObjectUtil.isNull(id)) return this.setResultError("id不能为空");
        AddressEntity addressEntity = addressMapper.selectByPrimaryKey(id);
        return this.setResultSuccess(addressEntity);
    }

    //删除地址
    @Override
    public Result<JSONObject> deleteAddress(Integer id) {
        if(ObjectUtil.isNull(id)) return this.setResultError("id不能为空");
        addressMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    //新增收货地址
    @Override
    public Result<JSONObject> addAddress(AddressDto address, String token) {
        AddressEntity addressEntity = BaiduBeanUtil.copyProperties(address, AddressEntity.class);

        try {
                UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
                addressEntity.setUserId(userInfo.getId());

                if(ObjectUtil.isNull(addressEntity.getId()))
                    addressMapper.insertSelective(addressEntity);
                else
                    addressMapper.updateByPrimaryKey(addressEntity);

            } catch (Exception e) {
                e.printStackTrace();
            }

        return this.setResultSuccess();
    }

    //查询用户收货地址
    @Override
    public Result<List<AddressEntity>> getAddress(String token) {
        List<AddressEntity> addressList = null;
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            AddressEntity address = new AddressEntity();
            address.setUserId(userInfo.getId());
            addressList = addressMapper.select(address);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.setResultSuccess(addressList);
    }

    @Override
    public Result<OrderInfo> getOrderInfoByOrderId(Long orderId) {
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        OrderInfo orderInfo = BaiduBeanUtil.copyProperties(orderEntity, OrderInfo.class);

        Example example = new Example(OrderDetailEntity.class);
        example.createCriteria().andEqualTo("orderId",orderId);
        List<OrderDetailEntity> orderDetailList = orderDetailMapper.selectByExample(example);
        orderInfo.setOrderDetailList(orderDetailList);

        OrderStatusEntity orderStatusEntity = orderStatusMapper.selectByPrimaryKey(orderId);
        orderInfo.setOrderStatusEntity(orderStatusEntity);

        return this.setResultSuccess(orderInfo);
    }

    @Override
    @Transactional
    public Result<String> createOrder(OrderDTO orderDTO, String token) {

        long orderId = idWorker.nextId();
        Date date = new Date();
        //获取用户信息
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            //准备orderEntity
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId(orderId);
            orderEntity.setPromotionIds("1,2,3");
            orderEntity.setPaymentType(2);
            orderEntity.setCreateTime(date);
            orderEntity.setUserId(userInfo.getId() + "");
            orderEntity.setBuyerMessage("什么玩意儿");
            orderEntity.setBuyerNick("玩意儿都不是");
            orderEntity.setBuyerRate(2);
            orderEntity.setInvoiceType(1);
            orderEntity.setSourceType(1);

            ArrayList<Long> priceList = new ArrayList<>();

            //准备orderDetailEntity
            List<OrderDetailEntity> orderDetailEntityList = Arrays.asList(orderDTO.getSkuIds().split(","))
                    .stream().map(strSkuId -> {
                Car redisCar = redisRepository.getHash(GOODS_CAR_PRE + userInfo.getId(), strSkuId, Car.class);

                OrderDetailEntity orderDetailEntity = BaiduBeanUtil.copyProperties(redisCar, OrderDetailEntity.class);
                orderDetailEntity.setOrderId(orderId);
                priceList.add(orderDetailEntity.getPrice() * orderDetailEntity.getNum());
                return orderDetailEntity;
            }).collect(Collectors.toList());
            //准备orderStatusEntity
            OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
            orderStatusEntity.setOrderId(orderId);
            orderStatusEntity.setCreateTime(date);
            orderStatusEntity.setStatus(1);
            //计算商品价格
            Long allPrice = priceList.stream().reduce(0L, (oldVal, currentVal) -> oldVal + currentVal);
            orderEntity.setActualPay(allPrice);
            orderEntity.setTotalPay(allPrice);
            //入库
            orderMapper.insertSelective(orderEntity);
            orderDetailMapper.insertList(orderDetailEntityList);
            orderStatusMapper.insert(orderStatusEntity);

            //将当前商品从购物车中(redis)删除
            Arrays.asList(orderDTO.getSkuIds().split(",")).stream().forEach(strSkuId -> {
                redisRepository.delHash(GOODS_CAR_PRE + userInfo.getId(), strSkuId);
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.setResultError("用户失效");
        }
        return this.setResult(HTTPStatus.OK,"",orderId + "");
    }
}
