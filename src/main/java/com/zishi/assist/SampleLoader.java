package com.zishi.assist;

import javassist.*;

import java.io.IOException;






/**
 *
 * 一个简单的使用Javassist的类加载器
 *
 * 类MyApp是一个应用程序，为了执行该程序，将该类的字节码放到  ./class 目录下，这个路径不能被搜索路径包括
 * 否则，MyApp.class将会被默认的系统类加载器（是SampleLoader的父类加载器）加载
 *
 * ./class 这个目录的名称是构造方法里面的insertClassPath()方法指定的，你可以自行修改这个目录的名称
 *
 * 然后命令行执行：% java SampleLoader
 *
 * 这个类加载器就会加载  MyApp（./class/MyApp.class），然后调用MyApp.main()方法（伴随着命令行的参数），
 *
 *
 * 这是最简单的使用Javassist加载器的方式，然而，如果你想写一个更加复杂的类加载器，你可能需要详细的类加载机制的知识
 * 例如：
 * 因为MyApp 类的加载和SampleLoader类加载使用了不同的类加载器，那么
 * 这个程序将MyApp类所属的命名空间和SampleLoader所属的命名空间是完全独立的
 * 因此：MyApp类不能直接访问SampleLoader
 *
 */
public class SampleLoader extends ClassLoader {
    /* Call MyApp.main().
     */
    public static void main(String[] args) throws Throwable {
        SampleLoader s = new SampleLoader();
        Class c = s.loadClass("MyApp");
        c.getDeclaredMethod("main", new Class[] { String[].class })
         .invoke(null, new Object[] { args });
    }

    private ClassPool pool;

    public SampleLoader() throws NotFoundException {
        pool = new ClassPool();
        pool.insertClassPath("./class"); // MyApp.class must be there.
    }

    /* Finds a specified class.
     * The bytecode for that class can be modified.
     */
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            CtClass cc = pool.get(name);
            // modify the CtClass object here
            byte[] b = cc.toBytecode();
            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException | IOException | CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }
}