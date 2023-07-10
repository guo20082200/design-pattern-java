package com.zishi.creational.prototype;

/**
 * 测试普通new方式创建对象和clone方式创建对象的效率差异！
 * 如果需要短时间创建大量对象，并且new的过程比较耗时。则可以考虑使用原型模式！
 *
 * @author 波波烤鸭
 */
public class Client4 {

    public static void testNew(int size) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            User3 t = new User3();
        }
        long end = System.currentTimeMillis();
        System.out.println("new的方式创建耗时：" + (end - start));
    }

    public static void testClone(int size) throws CloneNotSupportedException {
        long start = System.currentTimeMillis();
        User3 t = new User3();
        for (int i = 0; i < size; i++) {
            User3 temp = (User3) t.clone();
        }
        long end = System.currentTimeMillis();
        System.out.println("clone的方式创建耗时：" + (end - start));
    }


    public static void main(String[] args) throws Exception {
        testNew(1000);
        testClone(1000);
    }
}


class User3 implements Cloneable {  //用户
    public User3() {
        try {
            Thread.sleep(10);  //模拟创建对象耗时的过程!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();  //直接调用object对象的clone()方法！
        return obj;
    }
}

// new的方式创建耗时：15643
// clone的方式创建耗时：19