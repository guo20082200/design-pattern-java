package org.example.jucdemo2.varhandle;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;

public class VarHandleExample {

    @Test
    void test01() {
        String[] sa = {"1", "2", "3"};
        VarHandle avh = MethodHandles.arrayElementVarHandle(String[].class);
        List<Class<?>> classes = avh.coordinateTypes();
        System.out.println(classes.size());
        System.out.println(classes.get(0));
        System.out.println(classes.get(1));
        //boolean r = avh.compareAndSet(sa, 10, "expected", "new");
    }

    private volatile int counter = 0;

    private static final VarHandle VH;

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            VH = l.findVarHandle(VarHandleExample.class, "counter", int.class);
            System.out.println(VH.getClass());
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void increment() {
        while (true) {
            int current = (int) VH.get(this);
            int next = current + 1;
            if (VH.compareAndSet(this, current, next)) {
                System.out.println("...............");
                break;
            }
        }
    }

    public static void main(String[] args) {
        VarHandleExample example = new VarHandleExample();
        example.increment();
        System.out.println("Counter: " + example.counter); // 输出 Counter: 1
    }
}
