package com.zishi.scala.a03.okk08

/**
 * 伴生对象： 为了实现类似 Java 中那种既有实例成员又有静态成员的类的功能。
 *
 * 为什么上面说它是一种 “功能” 呢？因为要想实现像 Java 中那样的类，光靠一个 Scala 类可不行。在 Scala 中，我们必须：
 * 1. 定义一个 class 并在这里面实现所有的实例成员。
 * 2. 添加一个 object ，这个 object 要与上面的 class 同名，然后在这里面实现所有的静态成员。
 * 3. 定义的 class 与 object 必须在同一个文件内。
 */
class CompanionDemo {

  private var title:String = _
  println("new CompanionDemo clz")

  def init(): Unit = {
    // our codes...
    // class 中要访问 object 中的私有属性，直接以 object 名来 “点” 相应的变量或方法即可
    println(CompanionDemo.abc)
  }

}

/**
 * 这个对象称之为：类CompanionDemo的伴生对象
 * 毫无疑问，伴生对象这种存在最大的特点就是可以实现类似 Java 中那样，在同一个类里既有实例成员又有静态成员的功能。
 *
 * 另一个特点就是 class CompanionDemo 和 object CompanionDemo 虽然分开两处定义，但却可以访问对方的 private 变量及方法
 */
object CompanionDemo {

  private var abc:String = _

  def main(args: Array[String]): Unit = {
    val demo = new CompanionDemo()
    // 伴生对象访问类的私有变量
    println(demo.title)
  }

  println("new CompanionDemo object")

  def prt(): Unit = {
    // our codes...
    println()
  }

  /**
   * 伴生对象中要想访问对方的私有属性，需要注意以下 2 点
   *
   * 1. class 中要访问 object 中的私有属性，直接以 object 名来 “点” 相应的变量或方法即可
   *
   * 2. object 中要访问 class 中的私有属性，必须要通过 class 的对象引用来 “点” 相应变量或方法
   */

  /**
   * 伴生对象的意义是什么？
   * 1. Scala 中没有 static 关键字，而 Scala 又运行与 JVM 之上，与 Java 类库完全兼容的编程语言，同时类中拥有静态属性又是如此的有必要，
   * 因此推出这个伴生对象机制就显得很有必要了。所以第 1 个意义就是为了弥补类中不能定义 static 属性的缺陷。
   *
   * 2. 那我们知道，在 Java 中静态属性是属于类的，在整个 JVM 中只开辟一块内存空间，这种设定使得静态属性可以很节省内存资源，
   * 不用担心像实例属性那样有内存溢出的风险。在 Scala 中伴生对象本质上也是生成静态属性，所以这第2个意义就是节省内存资源。
   *
   * 3. 既然静态属性在整个 JVM 中仅开辟一块内存空间，那就说明我们可以在所有实例当中共享这块内存里面的信息，所以第3个意义就是资源共享。
   */
}