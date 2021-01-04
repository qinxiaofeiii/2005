package com.baidu.singleton;

public class SingletonEHan {
    //饿汉式
    private static SingletonEHan sin = new SingletonEHan();
    private SingletonEHan(){};
    public static SingletonEHan getInstance(){
        return sin;
    }

}
