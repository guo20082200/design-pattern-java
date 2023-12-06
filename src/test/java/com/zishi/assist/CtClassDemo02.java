package com.zishi.assist;

import javassist.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * CtClass对象通过ClassPool创建，一旦创建就永久被ClassPool持有，
 * 这是因为一个编辑器稍后需要访问CtClass对象(当编译源代码并参考CtClass对象的时候)
 *
 * 例如：
 * 设想需要在待变Point类的CtClass对象里面新增getter()方法，然后程序尝试编译源代码，在Point类里面包含了getter()方法
 * 并且使用了编译的代码作为方法体，方法体还会被添加到class的另一行。如果CtClass对象代表的Point丢失了，那么编译器不能编译这个getter()方法
 * 注意：原始的class定义并没有包含getter()方法
 * 因此，为了正确编译这样的一个方法调用，ClassPool必须在程序执行的所有时间段内包含所有的CtClass对象
 */
public class CtClassDemo02 {


    /**
     * 避免内存溢出：
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Test
    public void test01() throws NotFoundException, CannotCompileException, IOException {

        /**
         * 1. ClassPool: 一个存储CtClass对象的集合, CtClass对象必须从ClassPool获取
         * 当ClassPool对象调用get()方法，搜索所有的代表ClassPath的源来找到一个类文件，然后创建表征类文件的CtClass对象
         * 创建的对象返回给调用者
         *
         * 2. 内存消耗：
         * ClassPool对象持有了所有的该对象创建的CtClasses对象，这是为了保证修改classes的一致性
         * 因此，如果要处理大量的CtClasses，ClassPool将会消耗大量的内存
         * 为了避免这一点，假如处理100个classes，那么一个ClassPool对象应该被重新创建，
         * 注意到：getDefault()方法是一个单例模式，因此，CtClass对象的 detach() 应该被调用来避免大内存的消耗
         *
         * 3. ClassPool类层级结构
         * ClassPools可以make一个parent-child层级结构，就类似java.lang.ClassLoaders
         * 如果一个ClassPool是一个parentpool
         * get() 方法第一时间会去parent pool 里面找类文件，
         * 只有在parent pool没有找到类文件，get()方法才会在the child ClassPool的ClassPaths里搜索.
         * 如果 ClassPath.childFirstLookup参数为true,那么这个搜索的顺序保持不变
         *
         */
        ClassPool pool = ClassPool.getDefault();
        System.out.println(pool);

        /**
         * Hashtable<String, String> hashtable = new Hashtable<>(); 类似HashMap
         *
         * 1. ClassPool底层的实现是 Hashtable，用来存放CtClass对象的集合，使用类名作为key。
         * 2. 通过get()方法在ClassPool的哈希表里面搜索指定key的CtClass
         * 3. 如果这样的CtClass没有找到，那么就创建一个新的CtClass对象，并记录在哈希表里面，然后将结果返回。
         * 4. CtClass对象可以用来修改
         */
        CtClass cc = pool.get("test.Rectangle");// 从pool中获取test.Rectangle
        System.out.println(cc.getClass()); //CtPrimitiveType
        System.out.println(cc.getSimpleName()); //CtPrimitiveType

        cc.setSuperclass(pool.get("test.Point")); // 设置父类
        cc.writeFile(); // 将CtClass对象转换成为类文件写入到本地磁盘上
        byte[] bytecode = cc.toBytecode(); // 直接获取修改的字节码
        Class<?> aClass = cc.toClass();// 需要代表当前CtClass对象的当前线程的上下文类加载器


    }


}
