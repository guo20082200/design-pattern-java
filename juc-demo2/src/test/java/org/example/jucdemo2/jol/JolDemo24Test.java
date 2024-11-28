package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;

import static java.lang.System.out;


/*
 * 这个示例展示了数组布局的一些奇特之处。
 *
 * 如果你使用几乎任何垃圾回收器运行，你会注意到数组元素是按索引顺序排列的。
 *
 * 如果你使用并行垃圾回收器运行，你可能会注意到新对象元素在数组后面按正序排列，
 * 但在垃圾回收后它们可能会按逆序重新排列。这是因为垃圾回收器在栈上记录了待提升的对象。
 *
 * 最好使用 `-XX:ParallelGCThreads=1` 参数运行此测试。
 *
 * 参见：
 *   https://bugs.openjdk.java.net/browse/JDK-8024394
 */

public class JolDemo24Test {

    public static void main(String[] args) {
        PrintWriter pw = new PrintWriter(System.out, true);

        Integer[] arr = new Integer[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = i + 256; // boxing outside of Integer cache
        }

        String last = null;
        for (int c = 0; c < 100; c++) {
            String current = GraphLayout.parseInstance((Object) arr).toPrintable();

            if (last == null || !last.equalsIgnoreCase(current)) {
                pw.println(current);
                last = current;
            }

            System.gc();
        }

        pw.close();
    }
}
