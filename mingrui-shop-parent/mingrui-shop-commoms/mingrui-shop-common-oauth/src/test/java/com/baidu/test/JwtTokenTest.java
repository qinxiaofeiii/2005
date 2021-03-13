package com.baidu.test;

import com.baidu.shop.dto.UserInfo;
import com.baidu.shop.utils.JwtUtils;
import com.baidu.shop.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTokenTest {

    //公钥位置
    private static final String pubKeyPath = "D:\\rea.pub";
    //私钥位置
    private static final String priKeyPath = "D:\\rea.pri";
    //公钥对象
    private PublicKey publicKey;
    //私钥对象
    private PrivateKey privateKey;

    @Test
    public void genRsaKey() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "mingrui");
    }

    /**
     * 从文件中读取公钥私钥
     * @throws Exception
     */
    @Before
    public void getKeyByRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    /**
     * 根据用户信息结合私钥生成token
     * @throws Exception
     */
    @Test
    public void genToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(1, "qinxiaofei"),privateKey, 2);
        System.out.println("user-token = " + token);
    }

    /**
     * 结合公钥解析token
     * @throws Exception
     */
    @Test
    public void parseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJxaW54aWFvZmVpIiwiZXhwIjoxNjE1NTI1NTI0fQ.TVlBEYSlL9jovSSaxfDAKosfsG0wnmCy1GmcJ4v7Ggfgx9h5SRjtasJNCYHfocuSmrHlR434gk1SxZTh0_L0Q-bGguEF7vr6NTtX-SNbP0KJ_kZMMPZ1tLIj6r1Gu5Rv8z-jWReBb3vil7dzcZNUJcELNNReKyk_mbCv0BNLESA";
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }

}
