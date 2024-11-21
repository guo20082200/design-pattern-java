package org.example.jucdemo2.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *
 *
 * 如何解决ABA问题：AtomicStampedReference
 * 其实除了AtomicStampedReference类，还有一个原子类也可以解决，
 * 就是AtomicMarkableReference，它不是维护一个版本号，而是维护一个boolean类型的标记，
 * 用法没有AtomicStampedReference灵活。因此也只是在特定的场景下使用。
 */
public class AtomicTest2 {
    private static final AtomicInteger index = new AtomicInteger(10);
    static AtomicStampedReference<Integer> stampRef
            = new AtomicStampedReference<>(10, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = stampRef.getStamp();
            System.out.println(Thread.currentThread().getName()
                    + " 第1次版本号： " + stamp);
            stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第2次版本号： " + stampRef.getStamp());
            stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第3次版本号： " + stampRef.getStamp());
        }, "张三").start();

        new Thread(() -> {
            try {
                int stamp = stampRef.getStamp();
                System.out.println(Thread.currentThread().getName()
                        + " 第1次版本号： " + stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = stampRef.compareAndSet(10, 12,
                        stampRef.getStamp(), stampRef.getStamp() + 1);
                System.out.println(Thread.currentThread().getName()
                        + " 修改是否成功： " + isSuccess + " 当前版本 ：" + stampRef.getStamp());
                System.out.println(Thread.currentThread().getName()
                        + " 当前实际值： " + stampRef.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "李四").start();
    }
}