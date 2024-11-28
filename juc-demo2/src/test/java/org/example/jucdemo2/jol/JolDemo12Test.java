 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.samples.JOLSample_12_BiasedLocking;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;


 /**
  * 偏向锁
  * 这是对标记字（mark word）的一次深入探讨。
  *
  * 标记字除了存储其他信息外，还存储锁定信息。我们可以清楚地看到，在我们获取锁并随后释放锁时，标记字的内容是如何变化的。
  *
  * 在这个示例中，我们演示了偏向锁定。每个Java对象都有可能是同步的目标。大多数情况下，对象只被单个线程锁定。在这种情况下，我们可以将对象“偏向”到那个单个线程，从而使对该对象的同步非常廉价。
  *
  * 为了演示这一点，我们在获取锁之前、期间和之后打印对象的内部信息。你可以注意到，标记字从“可偏向”变为“已偏向”。解锁后，标记字保持不变：对象现在偏向于该线程。
  *
  * 在JDK 9之前，默认情况下，偏向锁定只有在VM启动5秒后才会启用。因此，最佳做法是在JDK 8及更低版本上使用-XX:BiasedLockingStartupDelay=0运行测试。从JDK 15开始，默认情况下禁用了偏向锁定，因此需要使用-XX:+UseBiasedLocking来运行此测试。
  */

 public class JolDemo12Test {
    public static void main(String[] args) {

        final A a = new A();

        ClassLayout layout = ClassLayout.parseInstance(a);

        out.println("**** Fresh object");
        out.println(layout.toPrintable());

        synchronized (a) {
            out.println("**** With the lock");
            out.println(layout.toPrintable());
        }

        out.println("**** After the lock");
        out.println(layout.toPrintable());
    }

    public static class A {
    }
}
