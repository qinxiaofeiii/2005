package com.mr.qxf.user.controller;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String s = "asdfawtawekltyugawklthkl";
        Map<Object, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (map.containsKey(chars[i])){
                map.put(chars[i],map.get(chars[i])+1);
            }else{
                map.put(chars[i],1);
            }
        }
        map.forEach((key,value)->{
            System.err.println(key + ":" + value);
        });
    }
}
