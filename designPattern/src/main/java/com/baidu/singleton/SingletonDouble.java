package com.baidu.singleton;

public class SingletonDouble {
    private static SingletonDouble sin = null;

    private static Object o = new Object();

    private SingletonDouble(){
        super();
    }
    public static SingletonDouble getInstance(){
        if(sin == null){
            synchronized (o){
                if(sin == null){
                    sin = new SingletonDouble();
                }
            }
        }
        return sin;
    }
}
