 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


 /*
  * 这是一个示例，展示了虚拟机如何对堆进行碎片整理。
  *
  * 在这个示例中，我们有一个对象数组，这些对象被密集地分配，并且在多次垃圾回收后仍然保持为密集结构。
  * 然后，我们随机删除一半的元素。此时，内存布局变得稀疏。后续的垃圾回收会处理这些问题。
  *
  * 这个示例会在你的当前目录生成 PNG 图像。
  *
  * 为了获得最佳效果，请使用 `-Xmx1g -XX:+UseParallelGC -XX:ParallelGCThreads=1` 参数运行此测试。
  */
 public class JolDemo26Test {
     public static volatile Object sink;

     public static void main(String[] args) throws Exception {

         // allocate some objects to beef up generations
         for (int t = 0; t < 1000000; t++) {
             sink = new Object();
         }
         System.gc();

         final int COUNT = 10000;

         Object[] array = new Object[COUNT];
         for (int c = 0; c < COUNT; c++) {
             array[c] = new Object();
         }

         Object obj = array;

         GraphLayout.parseInstance(obj).toImage("array-1-new.png");

         for (int c = 2; c <= 5; c++) {
             for (int t = 0; t < 1000000; t++) {
                 sink = new Object();
             }
             System.gc();
             GraphLayout.parseInstance(obj).toImage("array-" + c + "-before.png");
         }

         for (int c = 0; c < COUNT; c++) {
             if (Math.random() < 0.5) {
                 array[c] = null;
             }
         }

         GraphLayout.parseInstance(obj).toImage("array-6-after.png");

         for (int c = 7; c <= 10; c++) {
             for (int t = 0; t < 1000000; t++) {
                 sink = new Object();
             }
             System.gc();
             GraphLayout.parseInstance(obj).toImage("array-" + c + "-after-gc.png");
         }
     }
}
