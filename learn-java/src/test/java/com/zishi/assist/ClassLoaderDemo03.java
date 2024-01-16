package com.zishi.assist;

import javassist.*;
import org.junit.jupiter.api.Test;


/**
 * 如果提前知道了怎样去修改一个类，那么最容易的方式就是：
 * 1. 通过ClassPool的get方法获取到CtClass对象
 * 2. 修改对象
 * 3. 调用writeFile方法或者toBytecode()方法来获取一个修改过的class文件
 * <p>
 * 但是如果不能提前知道如何修改一个类对象，那么javaassist必须和classloader协作。
 * java assist和 classloader 协作，那么可以在类加载的时候修改class文件
 * java assist的使用者可以定义自己的classloader的版本，
 */
public class ClassLoaderDemo03 {


    /**
     * 避免内存溢出： ClassPool 可能引起大的内存的消耗，CtClass对象的数量可能会变的惊人的大(很少情况发生，javaassist尝试减少内存消耗)
     * 为了避免这个问题，可以将不必需要的CtClass对象显式的从ClassPool里面移除。
     * 如果CtClass对象调用了detach()方法，那么CtClass对象显式的从ClassPool里面移除。
     */
    @Test
    public void test01() throws Exception {
        //Hello hello = new Hello(); // 这里加载了Hello类，后面toClass再次加载，就会报错，jvm不能加载两个不同版本的类
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.zishi.assist.Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }"); // 在方法体前面插入代码
        // 在 .toClass()方法调用之前，class从来没有被加载
        Class c = cc.toClass(); // 获取类的字节码对象
        Hello h = (Hello) c.newInstance();
        h.say();

        // 如果是再web容器中运行的应用，例如Tomcat
        // toClass()方法中使用context类加载器似乎不太合适
        // 这种情况下，你可能会看到一个不期待的异常：ClassCastException
        // 为了避免这个异常，你必须显式的给定一个合适的类加载器给toClass()方法
        // 例如：如果bean代表一个session对象，那么下面的代码：
        //CtClass cc = ...;
        //Class clz = cc.toClass(bean.getClass().getClassLoader());
    }

    /**
     * Class loading in Java
     *
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {

    }

    /**
     * 使用 javassist.Loader
     *
     * @throws Exception
     */
    @Test
    public void test03() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        Loader cl = new Loader(pool);

        //CtClass ct = pool.get("test.Rectangle");
        //ct.setSuperclass(pool.get("test.Point"));

        Class c = cl.loadClass("test.Rectangle");
        Object rect = c.newInstance();

        Translator t = new MyTranslator();
        cl.addTranslator(pool, t);
        //cl.run("MyApp", args);
    }

    /**
     * 编写一个类加载器。参考：SampleLoader
     *
     * @throws Exception
     */
    @Test
    public void test04() throws Exception {

    }

    /**
     * 修改一个系统类
     *
     * @throws Exception
     */
    @Test
    public void test05() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("java.lang.String");
        CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
        f.setModifiers(Modifier.PUBLIC);
        cc.addField(f);
        cc.writeFile(".");
    }

    // java -Xbootclasspath/p:. MyApp arg1 arg2...
    public static void main(String[] args) throws Exception {
        // 使用test05创建的类
        System.out.println(String.class.getField("hiddenValue").getName());
    }


    /**
     * Reloading a class at runtime
     * 在运行时重新加载一个类
     *
     *
     * 如果JVM开启了JPDA(Java Platform Debugger Architecture),那么一个class可以被动态的再次加载
     *  在JVM加载一个类之后，这个类的旧版本被卸载，新版本被再次reload，也就是说：这个类可以在运行时被动态的修改
     * 然而这个类一定要和旧版本兼容，JVM不允许两个版本之间发生schema的变化，两个版本应该有相同的方法和属性
     *
     * Javassist提供了一个方便的类在运行时重新加载一个class
     * 更多参考信息，参考javassist.tools.HotSwapper说明文档
     * @throws Exception
     */
    @Test
    public void test06() throws Exception {

    }
}


