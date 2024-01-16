package com.zishi.assist;

import javassist.ClassPool;
import javassist.CtClass;

import java.lang.reflect.Method;

public class AR02 {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath("D:\\my-learn\\design-pattern-java\\src\\main\\java\\");
        pool.insertClassPath("C:\\Users\\zishi\\Desktop\\arthas\\");

        // 获取接口
        CtClass codeClassI = pool.get("com.zishi.assist.PersonI");
        // 获取上面生成的类
        CtClass ctClass = pool.get("com.rickiyang.learn.javassist.Person");
        // 使代码生成的类，实现 PersonI 接口
        ctClass.setInterfaces(new CtClass[]{codeClassI});

        // 以下通过接口直接调用 强转
        PersonI person = (PersonI)ctClass.toClass().newInstance();
        System.out.println(person.getName());
        person.setName("xiaolv");
        person.printName();
    }
}
