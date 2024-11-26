package org.example.jucdemo2.varhandle;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VarHandleExample06Test {


    public static void main(String[] args) throws InterruptedException, IllegalAccessException, NoSuchFieldException {
        VarHandle threadProbe = MethodHandles.privateLookupIn(Thread.class, MethodHandles.lookup())
                .findVarHandle(Thread.class, "threadLocalRandomProbe", int.class);

        VarHandle threadLocalRandomSeedVarHandle = MethodHandles.privateLookupIn(Thread.class, MethodHandles.lookup())
                .findVarHandle(Thread.class, "threadLocalRandomSeed", long.class);


       /* ThreadLocalRandom.current();

        TimeUnit.SECONDS.sleep(3);
        // &#x4E0D;&#x80FD;&#x901A;&#x8FC7; VarHandle &#x8FDB;&#x884C;&#x521D;&#x59CB;&#x5316;
        //threadLocalRandomSeedVarHandle.set(Thread.currentThread(), 23L);
        System.out.println(threadLocalRandomSeedVarHandle.get(Thread.currentThread()));
        int probe = (int) threadProbe.get(Thread.currentThread());
        System.out.println(Thread.currentThread().getName() + "..." + probe);

        System.out.println(probe & 1);
        System.out.println(probe & 3);
        System.out.println(probe & 7);
        System.out.println(probe & 15);*/

        final ExecutorService executorService = Executors.newFixedThreadPool(200);

        for(int i = 1; i <= 1000; i++) {
            executorService.submit(() -> {
                ThreadLocalRandom.current();
                int probe = (int) threadProbe.get(Thread.currentThread());
                System.out.println(Thread.currentThread().getName() + "\t\t" + probe + "\t\t" + (probe & 7));
            });
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {

        }


    }

    @Test
    void testAnd() {
        for(int i = 1; i <= 40; i++) {
            System.out.print((i & 7) + " \t");
        }
    }

}
