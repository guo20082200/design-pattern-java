package com.zishi.assist;

import javassist.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Javassist是一个用于处理Java字节码的类库。Java 字节码存储在称为类文件的二进制文件中。
 * 每个类文件包含一个 Java 类或接口。
 * 类 Javassist.CtClass是类文件的抽象表示。（编译时类）对象 CtClass是处理类文件的句柄。
 */
public class ClassPoolDemo01 {


    @Test
    public void testPath() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        LoaderClassPath loaderClassPath = new LoaderClassPath(cl);
        System.out.println(loaderClassPath); //ClassLoaders$AppClassLoader
        //System.out.println(loaderClassPath.toString());
    }


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

    /**
     * 定义一个新类，通过makeClass()方法
     *   Note that an interface method is an abstract method.
     */
    @Test
    public void test02() throws NotFoundException, CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        /**
         * 定义了一个心累，没有任何成员变量和成员方法
         * makeClass()方法不能创建interface
         */
        CtClass cc = pool.makeClass("Point");

        // 生成setter方法
        CtField param = new CtField(pool.get("java.lang.String"), "name", cc); // 字段参数
        /**
         * 通过CtNewMethod来创建成员方法
         * 通过addMethod()方法将成员方法加入到CtClass对象中
         */
        cc.addMethod(CtNewMethod.setter("setName", param));

        /**
         * 通过makeInterface创建一个interface
         */
        CtClass ctClass = pool.makeInterface("com.zishi.test.A");

        /**
         *
         * 创建接口里面的抽象方法 CtNewMethod.abstractMethod()
         * 注意：接口里面的方法都是抽象方法
         */
        CtClass[] exceptions = new CtClass[3];
        CtClass[] parameters = new CtClass[3];

        /**
         * CtClass returnType 返回值类型
         * String mname 方法名
         * CtClass[] parameters 方法参数列表
         * CtClass[] exceptions 方法抛出的异常
         * CtClass declaring 方法的声明对象
         */
        CtMethod ctMethod = CtNewMethod.abstractMethod(CtClass.intType, "abc", parameters, exceptions, ctClass);
        ctClass.addMethod(ctMethod);
    }

    /**
     * Frozen classes： 冻结的类
     * 1. 如果一个CtClass通过writeFile()/toClass()/toBytecode()方法转换成一个类文件，那么Javassist将冻结该CtClass对象
     *      进一步的修改该CtClass对象是不被允许的，这也是针对开发者试图去修改一个已经加载到JVM的类的警告，因为JVM不允许重新加载一个类
     *
     * 2. 通过调用defrost()方法可以将已经冻结的类解冻，进而对CtClass对象进行修改
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Test
    public void test03() throws NotFoundException, CannotCompileException, IOException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Point");
        cc.writeFile("D:\\my-learn\\design-pattern-java\\");
        cc.defrost(); // 解冻
        cc.setSuperclass(pool.makeClass("Point2")); // 不解冻：Point class is frozen， 解冻之后可以再次修改


        /**
         * 1. 如果ClassPool.doPruning设置为true,那么在Javassist冻结的CtClass对象情况下，Javassist可以剪切CtClass对象的数据结构
         * 2. 为了煎炒内存的消耗，需要剪切掉CtClass对象的无关紧要的属性，例如：Code_attribute属性（方法体）是可以无视的
         * 3. 因此，一个CtClass对象被剪切之后, 一个方法的字节码无法访问，除了方法名，方法签名和注解
         * 4. 剪切之后的CtClass对象不能被再次解冻。
         * 5. ClassPool.doPruning的默认值被设置为false
         * 6. 如果设置CtClass对象不允许剪切，需要提前调用stopPruning() 方法
         * 7.  注意：在debugging期间，你可能只想临时的停止pruning and freezing，并且写一个修改的类文件到本地磁盘，debugWriteFile()非常适合
         * 8. debugWriteFile()方法停止 pruning, 写一个类文件到磁盘, 解冻它，, 然后再次剪切 (if it was initially on).
         */
        ClassPool pool2 = ClassPool.getDefault();
        CtClass cc2 = pool2.makeClass("Pointxxx");
        cc2.stopPruning(true); // 该设置不允许cc2被剪切
        cc2.prune(); // 忽略掉必要的属性
        cc2.writeFile("D:\\my-learn\\design-pattern-java\\");
        cc2.defrost();


    }

    /**
     * Class search path： 类的搜索路径
     * 1. 默认的 ClassPool（通过ClassPool.getDefault()）搜索的相同的路径（JVM加载的路径）
     * 2. 如果是一个web应用，例如jboss或者tomcat, ClassPool的对象不会找到用户的类，是因为web应用有多个类加载器（和系统类加载器一样）
     * 3. 因此一个外部的类路径需要被注册到ClassPool中
     */
    @Test
    public void test04() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();

        /**
         * 这里可以将 this.getClass() 替换为任何一个类的字节码对象
         */
        pool.insertClassPath(new ClassClassPath(this.getClass()));//指定ClassPath加载
        pool.insertClassPath("/usr/local/javalib");//指定一个目录

        /**
         * 1. 将 "http://www.javassist.org:80/java/" 添加到搜索路径
         * 2. 仅仅搜索的package的名字为 org.javassist.下的类
         * 3. 例如加载一个类：org.javassist.test.Main， 类文件应该从下面的url获取：
         *  http://www.javassist.org:80/java/org/javassist/test/Main.class
         */
        ClassPath cp = new URLClassPath("www.javassist.org", 80, "/java/", "org.javassist.");
        pool.insertClassPath(cp);// 加载指定的URL路径


        /**
         * 用户自定义的search path
         */
        pool.insertClassPath(new ClassPath() {
            @Override
            public InputStream openClassfile(String classname) throws NotFoundException {
                return null;
            }

            @Override
            public URL find(String classname) {
                return null;
            }
        });

    }

    @Test
    public void test05() throws NotFoundException, CannotCompileException, IOException {
        /**
         * 如果你不知道一个类的全限定名，可以通过makeClass来确定
         *
         */
        ClassPool cp = ClassPool.getDefault();
        InputStream ins = new FileInputStream(new File(""));//an input stream for reading a class file;
        CtClass cc = cp.makeClass(ins);
    }
}
