package com.baidu.passwordTest;

import com.baidu.util.MD5Util;

public class passwordTest {

    public static void main(String[] args) {
        String s = "123456";
        String salt = "你是什么东西";
        String s1 = MD5Util.md5(MD5Util.md5(s) + salt);
        System.out.println(MD5Util.md5(MD5Util.md5(s) + salt));

        System.out.println(s1.equals(MD5Util.md5(MD5Util.md5(s) + salt)) );
    }

}
