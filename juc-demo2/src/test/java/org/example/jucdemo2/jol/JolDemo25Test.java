 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

 /*
  * 这是一个示例，展示了虚拟机如何压缩对象。
  *
  * 这个示例会在你的当前目录生成 PNG 图像。
  *
  * 你可以看到，新分配并填充的列表具有相当稀疏的布局。
  * 这是因为填充列表时分配了许多临时对象。后续的垃圾回收会将列表压缩成一个或几个密集的块。
  *
  * 为了获得最佳效果，请使用 `-Xmx1g -XX:+UseParallelGC -XX:ParallelGCThreads=1` 参数运行此测试。
  */

 public class JolDemo25Test {

     public static volatile Object sink;

     public static void main(String[] args) throws Exception {
         //out.println(VM.current().details());

         // allocate some objects to beef up generations
         for (int c = 0; c < 1000000; c++) {
             sink = new Object();
         }

         System.gc();

         List<String> list = new ArrayList<>();
         for (int c = 0; c < 1000; c++) {
             list.add("Key" + c);
         }

         for (int c = 1; c <= 10; c++) {
             GraphLayout.parseInstance(list).toImage("./list-" + c + ".png");
             System.gc();
         }
     }
}
