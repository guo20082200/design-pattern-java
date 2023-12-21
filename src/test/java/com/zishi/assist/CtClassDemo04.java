package com.zishi.assist;

import javassist.*;
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
     * CtMethod 和 CtConstructor 提供了 insertBefore()、insertAfter() 和 addCatch() 方法。
     * 它们用于将代码片段插入到现有方法的主体中。
     * 用户可以使用用 Java 编写的源文本指定这些代码片段。
     * Javassist 包含一个简单的 Java 编译器，用于处理源文本。
     * 它接收用 Java 编写的源文本并将其编译为 Java 字节码，该字节码将被内联到方法体中。
     *
     * 也可以在行号指定的位置插入代码片段（如果行号表包含在类文件中）。
     * CtMethod 和 CtConstructor 中的 insertAt() 在原始类定义的源文件中获取源文本和行号。
     * 它编译源文本并在行号处插入编译后的代码。
     *
     * insertBefore()、insertAfter()、addCatch() 和 insertAt() 方法接收表示语句或块的 String 对象。语句是单个控制结构，
     * 如 if 和 while 或以分号 ; 结尾的表达式。
     * 块是一组用大括号 {} 括起来的语句。因此，以下每一行都是有效语句或块的示例：
     * System.out.println("Hello");
     * { System.out.println("Hello"); }
     * if (i < 0) { i = -i; }
     *
     * 语句和块可以引用字段和方法。如果该方法是使用 -g 选项编译的（以在类文件中包含局部变量属性），则它们还可以引用插入到的方法的参数。否则，它们必须通过特殊变量 $0、$1、$2、...描述如下。尽管允许在块中声明新的局部变量，但不允许访问方法中声明的局部变量。
     * 但是，insertAt() 允许语句和块访问局部变量，前提是这些变量在指定的行号中可用，并且目标方法是使用 -g 选项编译的。
     *
     * 传递给 insertBefore()、insertAfter()、addCatch() 和 insertAt() 方法的 String 对象由 Javsassist 中包含的编译器编译。
     * 由于编译器支持语言扩展，因此以 $ 开头的几个标识符具有特殊含义：
     *
     *      $0, $1, $2, ...    this和其他的实际参数
     *      $args	参数数组， $args 的类型是 Object[].
     *      $$	所有的实际参数 For example, m($$) is equivalent to m($1,$2,...)
     *
     *      $cflow(...)	 cflow变量
     *      $r	结果类型. It is used in a cast expression.
     *      $w	包装类型. It is used in a cast expression.
     *      $_	结果
     *      $sig  java.lang.Class的数组	代表正式的参数类型.
     *      $type 一个java.lang.Class对象，代表正式的结果类型
     *      $class	一个java.lang.Class对象，代表当前被编辑的类
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Test
    public void test01() throws NotFoundException, CannotCompileException, IOException {
    }

    @Test
    public void test02() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.zishi.assist.Point");
        CtMethod m = cc.getDeclaredMethod("move");

        /**
         * 传递给目标方法的参数可通过 $1、$2、...而不是原始参数名称。
         * $1 表示第一个参数，$2 表示第二个参数，依此类推。
         * 这些变量的类型与参数类型相同。
         * $0等同于this。如果该方法是静态的，则 $0 不可用。
         *
         * 这里：$1代表dx，$2代表dy，
         *
         * 执行之后的结果如下：
         * void move(int dx, int dy) {
         *    System.out.println(dx);
         *    System.out.println(dy);
         *    this.x += dx;
         *    this.y += dy;
         * }
         */
        m.insertBefore("{ System.out.println($1); System.out.println($2); }");
        cc.writeFile();
    }
}

class Point {
    int x, y;
    void move(int dx, int dy) { x += dx; y += dy; }
}

