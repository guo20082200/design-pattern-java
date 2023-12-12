package com.zishi.assist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * CtClass对象通过ClassPool创建，一旦创建就永久被ClassPool持有，
 * 这是因为一个编辑器稍后需要访问CtClass对象(当编译源代码并参考CtClass对象的时候)
 * <p>
 * 例如：
 * 设想需要在待变Point类的CtClass对象里面新增getter()方法，然后程序尝试编译源代码，在Point类里面包含了getter()方法
 * 并且使用了编译的代码作为方法体，方法体还会被添加到class的另一行。如果CtClass对象代表的Point丢失了，那么编译器不能编译这个getter()方法
 * 注意：原始的class定义并没有包含getter()方法
 * 因此，为了正确编译这样的一个方法调用，ClassPool必须在程序执行的所有时间段内包含所有的CtClass对象
 */
public class CtClassDemo02 {


    /**
     * 避免内存溢出： ClassPool 可能引起大的内存的消耗，CtClass对象的数量可能会变的惊人的大(很少情况发生，javaassist尝试减少内存消耗)
     * 为了避免这个问题，可以将不必需要的CtClass对象显式的从ClassPool里面移除。
     * 如果CtClass对象调用了detach()方法，那么CtClass对象显式的从ClassPool里面移除。
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Test
    public void test01() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.writeFile();

        /**
         * 1. 从ClassPool中移除CtClass对象
         * 2. 该方法调用后， 不能再调用CtClass对象的任何方法
         * 3. 如果调用了ClassPool的get()方法，那么ClassPool将读取class文件然后构造另一个代表同一个类的CtClass对象
         */
        cc.detach();
    }

    /**
     * 另一个避免内存的思路是：重新创建一个新的ClassPool，旧的ClassPool被gc，那么旧的CtClass对象也会被gc
     */
    @Test
    public void test02() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = new ClassPool(true); // 和 ClassPool.getDefault()一样
        //上一行代码等价于下面的两行代码
        ClassPool cp = new ClassPool();
        cp.appendSystemPath();
    }

    /**
     * 级联的ClassPool
     * 如果是一个web应用，创建多个ClassPool的实例可能是必要的，每一个classloader创建一个ClassPool的实例
     * 不能再通过ClassPool.getDefault()创建，而是通过构造方法
     */
    @Test
    public void test03() throws NotFoundException, CannotCompileException, IOException {

        /**
         * 多个ClassPool实例创建，类似classloader
         * If child.get() is called, the child ClassPool first delegates to the parent ClassPool. If the parent ClassPool fails to find a class file, then the child ClassPool attempts to find a class file under the ./classes directory.
         */
        ClassPool parent = ClassPool.getDefault(); // 和 ClassPool.getDefault()一样
        ClassPool child = new ClassPool(parent); //
        child.insertClassPath("./classes");
        // 调用get()方法，child ClassPool第一时间去委托parent ClassPool
        // 如果 parent ClassPool没有找到这个类，然后child ClassPool才尝试从 ./classes 路径下找这个类
        // 但是有个例外，如果 child.childFirstLookup是true,则child ClassPool在委托parent ClassPool之前尝试去找到这个类
        child.childFirstLookup = true;    // changes the behavior of the child.
        child.get("aaaa");

    }

    /**
     * 改变一个类的名字，重新定义一个新的类
     */
    @Test
    public void test04() throws NotFoundException, CannotCompileException, IOException {

        //可以通过copy一个已经存在的类来定义一个新的类，下面代码说明：
        ClassPool pool = ClassPool.getDefault(); // 获取池对象
        CtClass cc = pool.get("java.lang.String"); // 获取Point的CtClass对象、

        CtClass cc2 = pool.get("java.lang.String");
        System.out.println(cc == cc2); // true
        cc.setName("Pair");// 给CtClass对象设置一个新的名字：Pair
        // 这行代码标志：CtClass对象里面所有遇见Point的地方都修改为了Pair，剩余的class定义的地方没有改变

        // setName改变了ClassPool对象的一个记录。从底层实现的角度来考虑，ClassPool就是CtClass对象的hashtable
        // setName方法是改变了hashtable的key。从原来的名字改变为了新的名字
        CtClass cc3 = pool.get("java.lang.String");
        // 再次调用get方法获取的对象不同于cc，这里是新建了一个对象
        System.out.println(cc == cc3); //false

        CtClass cc4 = pool.get("Pair");
        System.out.println(cc == cc4); //true


        // 注意：ClassPool对象在classes和CtClass对象之间维护了一对一的映射
       // Javassist 不允许两个不同的CtClass对象代表同一个class，除非创建了两个独立的ClassPool
        // 这是一个重要的特征：针对一致性程序转换

        // 复制一个ClassPool
        ClassPool cp = new ClassPool(true);
        CtClass cc5 = cp.get("java.lang.String");
        System.out.println(cc == cc5); //false
    }

    /**
     * 重新命名一个冻结的类来定义一个新的类
     */
    @Test
    public void test05() throws NotFoundException, CannotCompileException, IOException {

        //可以通过copy一个已经存在的类来定义一个新的类，下面代码说明：
        ClassPool pool = ClassPool.getDefault(); // 获取池对象
        CtClass cc = pool.get("java.lang.String"); // 获取Point的CtClass对象、
        cc.writeFile();
        cc.setName("abc"); // java.lang.String class is frozen

        // 重新获取并设置名字
        CtClass cc2 = pool.getAndRename("java.lang.String", "Pair");

    }

}
