package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.constant.MyConstansUtil;
import com.baidu.shop.dto.UserDTO;
import com.baidu.shop.entity.UserEntity;
import com.baidu.shop.mapper.UserMapper;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.service.UserService;
import com.baidu.shop.utils.BCryptUtil;
import com.baidu.shop.utils.BaiduBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserServiceImpl extends BaseApiService implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisRepository redisRepository;



    @Override
    public Result<JSONObject> checkCode(String phone, String code) {

        String redisCode = redisRepository.get(MyConstansUtil.REDIS_DUANXIN_CODE_PRE + phone);

        if(code.equals(redisCode)){
            return this.setResultSuccess();
        }

        return this.setResultError("验证码输入错误");
    }

    @Override
    public Result<JSONObject> send(UserDTO userDTO) {

        //生成随机验证码
        String code= (int)((Math.random() * 9 + 1) * 100000) + "";
        //LuosimaoDuanxinUtil.SendCode(userDTO.getPhone(),code); //短信验证
        //LuosimaoDuanxinUtil.sendSpeak(userDTO.getPhone(),code); //语音验证
        redisRepository.set(MyConstansUtil.REDIS_DUANXIN_CODE_PRE + userDTO.getPhone(),code);
        redisRepository.expire(MyConstansUtil.REDIS_DUANXIN_CODE_PRE + userDTO.getPhone(),60L);

        log.info("手机号码为 : {} , 验证为 : {}", userDTO.getPhone(),code);
        return this.setResultSuccess();
    }

    @Override
    public Result<List<UserEntity>> checkUserNameOrPhone(String value, Integer type) {

        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(type == null && value == null) return this.setResultError("type或value不能为空");
        criteria.andEqualTo(type == 1 ? "username" : "phone",value);

        List<UserEntity> userEntityList = userMapper.selectByExample(example);
        return this.setResultSuccess(userEntityList);

    }

    @Override
    public Result<JSONObject> register(UserDTO userDTO) {

        UserEntity userEntity = BaiduBeanUtil.copyProperties(userDTO,UserEntity.class);
        userEntity.setPassword(BCryptUtil.hashpw(userEntity.getPassword(),BCryptUtil.gensalt()));
        userEntity.setCreated(new Date());

        userMapper.insertSelective(userEntity);

        return this.setResultSuccess();
    }
}
