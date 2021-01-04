package com.baidu.prototype;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        test();
    }
    public static void test() throws CloneNotSupportedException {
        Student student = new Student();
        Student clone = (Student) student.clone();
        Student clone1 = (Student) student.clone();

        System.out.println(student);
        System.out.println(clone);
        System.out.println(clone1);
    }
}
