package com.zishi.scala.okk05

/**
 * 将函数对象作为方法的参数来使用，
 * 函数本身就是对象，
 * 对象的使用领域：变量，方法参数，返回值类型
 *
 * 类比Java
 * public void test(User user) {
 * }
 *
 * User user = new User();
 *
 * test(user); 对象作为方法的参数
 * test(new User());
 */
object Test05_Lambda01 {

  def main(args: Array[String]): Unit = {

    // 创建方法对象
    def fun33(age: Int): String = {
      "Hello:" + age
    }

    /**
     *  等同于
     *  def test(f: Function1[Int, String]) : Unit = {}
     */
    def test(f: (Int) => String): Unit = {
      println(f(23))
    }

    val f2 = fun33 _
    test(f2) // Hello:23


    def sum(x:Int, y:Int): Int = {
      x + y
    }

    def sub(x: Int, y: Int): Int = {
      x - y
    }

    def test2(fun: (Int, Int) => Int): Unit = {
      val i = fun(10, 20)
      println(i)
    }

    // 将函数名称作为参数传递为一个函数作为参数，此时不需要下划线
    test2(sum)
    //TODO: 这里函数的名称sub真的很重要吗？
    // 类比Java：test(new User())
    test2(sub)

    // TODO： 将参数传递的名字取消掉
    // 如果函数声明没有def和名称，那么就是匿名函数
    test2((x: Int, y: Int) => {
      x * y
    })

    // 将匿名函数赋值给一个变量
    val a = (x: Int, y: Int) => {
      x / y
    }
    test2(a)

    // 匿名函数的简化原则
    // （1）参数的类型可以省略，会根据形参进行自动的推导
    test2((x, y) => {
      x * y
    })
    // （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
    // （3）匿名函数如果只有一行，则大括号也可以省略
    test2((x, y) => x * y)
    // （4）如果参数只出现一次,并且按照顺序执行一次，则参数省略且后面参数可以用_代替
    println("-------------------")
    test2(_ * _) // 下划线类似占位符，第一个下划线代表第一个参数，依次类推
    // (5) 如果可以推断出，当前传入的println是一个函数体，而不是调用语句，可以直接省略下划线



    // 定义一个函数，以函数作为参数输入
    def f(func: String => Unit): Unit = {
      func("abcde")
    }

    f((name: String) => {
      println(name)
    })

    println("========================")

    // 匿名函数的简化原则
    //    （1）参数的类型可以省略，会根据形参进行自动的推导
    f((name) => {
      println(name)
    })

    //    （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
    f(name => {
      println(name)
    })

    //    （3）匿名函数如果只有一行，则大括号也可以省略
    f(name => println(name))

    //    （4）如果参数只出现一次，则参数省略且后面参数可以用_代替
    f(println(_))

    //     (5) 如果可以推断出，当前传入的println是一个函数体，而不是调用语句，可以直接省略下划线
    f(println _)
    f(println)

  }
}