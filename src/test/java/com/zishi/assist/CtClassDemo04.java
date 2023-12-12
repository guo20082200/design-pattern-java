package com.zishi.assist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * 1. CtClass 提供内省方法。Javassist 的内省能力与 Java 反射 API 兼容。
 * CtClass 提供了 getName()、getSuperclass()、getMethods() 等。
 * CtClass 还提供了用于修改类定义的方法。它允许添加新的字段、构造函数和方法。也可以检测方法主体。
 *
 * 2. 方法由 CtMethod 对象表示。
 * CtMethod 提供了几种用于修改方法定义的方法。
 * 请注意，如果某个方法是从超类继承的，则表示继承方法的同一 CtMethod 对象表示该超类中声明的方法。CtMethod 对象对应于每个方法声明。
 *
 * 例如，如果类 Point 声明了方法 move()，而 Point 的子类 ColorPoint 不重写 move()，
 * 则在 Point 中声明并在 ColorPoint 中继承的两个 move（） 方法由相同的 CtMethod 对象表示。
 * 如果修改了此 CtMethod 对象表示的方法定义，则修改将反映在这两个方法上。
 * 如果只想修改 ColorPoint 中的 move() 方法，则首先必须向 ColorPoint 添加表示 Point 中 move() 的 CtMethod 对象的副本。
 * 可以通过 CtNewMethod.copy() 获取 CtMethod 对象的副本。
 *
 *
 * 3. Javassist 不允许删除方法或字段，但允许更改名称。
 * 因此，如果不再需要某个方法，则应通过调用 CtMethod 中声明的 setName() 和 setModifiers() 将其重命名并更改为私有方法。
 *
 * 4. Javassist 也不允许向现有方法添加额外的参数。与其这样做，不如将接收额外参数以及其他参数的新方法添加到同一类中。
 * 例如： void move(int newX, int newY) { x = newX; y = newY; } 修改为如下的代码：
 * void move(int newX, int newY, int newZ) {
 *     // do what you want with newZ.
 *     move(newX, newY);
 * }
 *
 * 5. Javassist 还提供了用于直接编辑原始类文件的低级 API。
 * 例如，CtClass 中的 getClassFile() 返回一个表示原始类文件的 ClassFile 对象。
 * CtMethod 中的 getMethodInfo() 返回一个 MethodInfo 对象，该对象表示类文件中包含的method_info结构。
 * 低级 API 使用 Java 虚拟机规范中的词汇表。用户必须了解类文件和字节码。有关更多详细信息，用户应查看 javassist.bytecode 包。
 *
 * 6. 仅当使用某些以 $ 开头的特殊标识符时，Javassist 修改的类文件才需要 javassist.runtime 包来支持运行时。这些特殊标识符如下所述。
 * 在没有这些特殊标识符的情况下修改的类文件在运行时不需要 javassist.runtime 包或任何其他 Javassist 包。
 * 有关详细信息，请参阅 javassist.runtime 包的 API 文档。
 */
public class CtClassDemo04 {


    /**
     * 4.1 在方法主体的开头/结尾插入源文本
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Test
    public void test01() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.writeFile();

        /**
         * 1. 从ClassPool中移除CtClass对象
         * 2. 该方法调用后， 不能再调用CtClass对象的任何方法
         * 3. 如果调用了ClassPool的get()方法，那么ClassPool将读取class文件然后构造另一个代表同一个类的CtClass对象
         */
        cc.detach();
    }


}
