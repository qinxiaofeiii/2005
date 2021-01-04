package com.baidu.singleton;

public class SingletonLanHan2 {
    private static SingletonLanHan2 sin = null;

    private SingletonLanHan2(){};

    public static synchronized SingletonLanHan2 getInstance() {
        if (sin == null) {
            sin = new SingletonLanHan2();
        }
        return sin;
    }
}
