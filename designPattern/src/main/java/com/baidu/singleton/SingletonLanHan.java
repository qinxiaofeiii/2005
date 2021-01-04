package com.baidu.singleton;

public class SingletonLanHan {
    private static SingletonLanHan sin = null;

    private SingletonLanHan(){};

    public static SingletonLanHan getInstance(){
        if(sin == null){
            sin = new SingletonLanHan();
        }
        return sin;
    }
}
