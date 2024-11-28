 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

 /*
  * 这是一个示例，展示了虚拟机如何将由不同线程分配的对象进行共置。
  *
  * 在这个示例中，`ConcurrentHashMap` 由多个线程填充。我们可以看到，在几次垃圾回收之后，它仍然被密集地打包在一起，尽管它是由多个线程分配的。
  *
  * 这个示例会在你的当前目录生成 PNG 图像。
  *
  * 为了获得最佳效果，请使用 `-Xmx1g -XX:+UseParallelGC -XX:ParallelGCThreads=1` 参数运行此测试。
  */

 public class JolDemo27Test {
     public static volatile Object sink;

     public static void main(String[] args) throws Exception {

         // allocate some objects to beef up generations
         for (int c = 0; c < 1000000; c++) {
             sink = new Object();
         }
         System.gc();

         final int COUNT = 1000;

         ConcurrentHashMap<Object, Object> chm = new ConcurrentHashMap<>();

         addElements(COUNT, chm);

         GraphLayout.parseInstance(chm).toImage("chm-1-new.png");

         for (int c = 2; c <= 5; c++) {
             GraphLayout.parseInstance(chm).toImage("chm-" + c + "-gc.png");
             System.gc();
         }

         addElements(COUNT, chm);

         for (int c = 6; c <= 10; c++) {
             GraphLayout.parseInstance(chm).toImage("chm-" + c + "-more-gc.png");
             System.gc();
         }

     }

     private static void addElements(final int count, final Map<Object, Object> chm) throws InterruptedException {
         ExecutorService pool = Executors.newCachedThreadPool();

         Runnable task = () -> {
             for (int c = 0; c < count; c++) {
                 Object o = new Object();
                 chm.put(o, o);
             }
         };

         for (int t = 0; t < Runtime.getRuntime().availableProcessors() * 2; t++) {
             pool.submit(task);
         }

         pool.shutdown();
         boolean termination = pool.awaitTermination(1, TimeUnit.DAYS);
     }
}
