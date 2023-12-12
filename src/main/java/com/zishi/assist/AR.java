package com.zishi.assist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

import java.lang.reflect.Method;

public class AR {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("C:\\Users\\zishi\\Desktop\\arthas\\");
        CtClass cc = pool.get("com.rickiyang.learn.javassist.Person");
        // 这里不写入文件，直接实例化
        Object person = cc.toClass().newInstance();
        // 设置值
        Method setName = person.getClass().getMethod("setName", String.class);
        setName.invoke(person, "cunhua");
        // 输出值
        Method execute = person.getClass().getMethod("printName");
        execute.invoke(person);
    }
}
