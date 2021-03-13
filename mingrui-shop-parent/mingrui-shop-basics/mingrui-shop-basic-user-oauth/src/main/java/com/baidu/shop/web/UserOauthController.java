package com.baidu.shop.web;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.business.UserOauthService;
import com.baidu.shop.config.JwtConfig;
import com.baidu.shop.dto.UserInfo;
import com.baidu.shop.entity.UserEntity;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.CookieUtils;
import com.baidu.shop.utils.JwtUtils;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "oauth")
public class UserOauthController extends BaseApiService {

    @Resource
    private UserOauthService userOauthService;

    @Resource
    private JwtConfig jwtConfig;

    @GetMapping(value = "verify")
    public Result<UserInfo> userStatusVerify(@CookieValue(value = "MRSHOP_TOKEN") String token,
                                             HttpServletRequest request,HttpServletResponse response){

        UserInfo userInfo = null;
        try {
            userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());//根据token获取用户信息

            //生成一个新的token并重新放到cookie中
            String newToken = JwtUtils.generateToken(userInfo, jwtConfig.getPrivateKey(), jwtConfig.getExpire());
            CookieUtils.setCookie(request,response,jwtConfig.getCookieName(),newToken,jwtConfig.getCookieMaxAge(),true);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setResultError(HTTPStatus.USER_NOT_LOGIN,"用户还没有登陆");
        }

        return this.setResultSuccess(userInfo);
    }

    @PostMapping(value = "login")
    public Result<JSONObject> login(@RequestBody UserEntity userEntity, HttpServletRequest request,
                                    HttpServletResponse response){

        String token = userOauthService.login(userEntity,jwtConfig);
        if(StringUtils.isEmpty(token)) return this.setResultError("账号或密码错误");

        CookieUtils.setCookie(request,response,jwtConfig.getCookieName(),token,jwtConfig.getCookieMaxAge(),true);

        return this.setResultSuccess();
    }

}
