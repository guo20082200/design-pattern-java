package com.zishi.scala.a02.okk05

object Test05_Lambda02 {
  def main(args: Array[String]): Unit = {
    // 实际示例，定义一个”二元运算“函数，只操作1和2两个数，但是具体运算通过参数传入
    def dualFunctionOneAndTwo(fun: (Int, Int) => Int): Int = {
      fun(1, 2)
    }

    val add = (a: Int, b: Int) => a + b
    val minus = (a: Int, b: Int) => a - b

    println(dualFunctionOneAndTwo(add))
    println(dualFunctionOneAndTwo(minus))

    // 匿名函数简化
    println(dualFunctionOneAndTwo((a: Int, b: Int) => a + b))
    println(dualFunctionOneAndTwo((a: Int, b: Int) => a - b))

    println(dualFunctionOneAndTwo((a, b) => a + b))
    println(dualFunctionOneAndTwo(_ + _))
    println(dualFunctionOneAndTwo(_ - _))

    println(dualFunctionOneAndTwo((a, b) => b - a))
    println(dualFunctionOneAndTwo(-_ + _))

    // Java里面存在引用类型和基本类型同时作为方法的参数 void test(int x, User u);
    // 类比java
    def cal(x: Int, func: (Int, Int) => Int, y: Int) = {
      func(x, y)
    }

    def sum(x: Int, y: Int) = {
      x + y
    }

    println("....")
    println(cal(3,
      (x: Int, y: Int) => {
      x + y
    }, 5))

    println(cal(3, (x: Int, y: Int) => x + y, 5))
    println(cal(3, (x, y) => x + y, 5))
    println(cal(3, _ + _, 5))

  }
}