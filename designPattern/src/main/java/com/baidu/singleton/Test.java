package com.baidu.singleton;

import com.baidu.prototype.Student;
import com.baidu.singleton.SingletonEHan;
import com.baidu.singleton.SingletonLanHan;

public class Test {
    public static void main(String[] args) {
        //饿汉式
        System.out.println("==========饿汉============");
        System.out.println(SingletonEHan.getInstance());
        System.out.println(SingletonEHan.getInstance());
        System.out.println(SingletonEHan.getInstance());
        System.out.println(SingletonEHan.getInstance());
        System.out.println(SingletonEHan.getInstance());

        //懒汉式(线程不安全)
        System.out.println("==========懒汉============");
        System.out.println(SingletonLanHan.getInstance());
        System.out.println(SingletonLanHan.getInstance());
        System.out.println(SingletonLanHan.getInstance());
        System.out.println(SingletonLanHan.getInstance());
        System.out.println(SingletonLanHan.getInstance());

    }


}
