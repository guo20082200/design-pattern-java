 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;


 /*
  * 这是一个如何使用 GraphLayout 差异来确定对象图变化的示例。
  *
  * 在这里，我们有一个 `ConcurrentHashMap`，并且进行了三次测量：
  *   1) 初始的 `CHM` 没有后备存储；
  *   2) 添加第一个键值对之后，此时键值对和后备存储都被分配了；
  *   3) 添加第二个键值对之后。
  *
  * 用于减去 GraphLayout 的 API 可以帮助显示快照之间的差异。
  * 请注意，这些差异是基于对象地址的，因此如果垃圾回收器在我们的操作过程中移动了对象，那么这些差异将不可靠。
  * 最好在快照之间尽量减少分配。
  */

 public class JolDemo28Test {

     public static void main(String[] args) {
         Map<String, String> chm = new ConcurrentHashMap<>();

         GraphLayout gl1 = GraphLayout.parseInstance(chm);
         System.out.println(gl1.toPrintable());
         chm.put("Foo", "Bar");
         GraphLayout gl2 = GraphLayout.parseInstance(chm);

         chm.put("Foo2", "Bar2");
         GraphLayout gl3 = GraphLayout.parseInstance(chm);

         System.out.println(gl2.subtract(gl1).toPrintable());
         System.out.println(gl3.subtract(gl2).toPrintable());
     }
}
