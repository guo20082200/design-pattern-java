/**
 * 和 Java 一样，可以在顶部使用 import 导入，在这个文件中的所有类都可以使用。
 * 局部导入：什么时候使用，什么时候导入。在其作用范围内都可以使用。
 * 通配符导入：import java.util._
 * 给类起名：import java.util.{ArrayList=>JL}
 * 导入相同包的多个类：import java.util.{HashSet, ArrayList}
 * 屏蔽类：import java.util.{ArrayList =>_,_}
 * 导入包的绝对路径：new _root_.java.util.HashMap
 */
package com {

  object TestPackage {
    // 导入包对象

    import com.zishi.scala.a03.MyPackageObject
    import java.util._

    def main(args: Array[String]): Unit = {
      MyPackageObject.sayHello()
      println(MyPackageObject.commonValue)

      new ArrayList[String]()

      import java.util.{ArrayList=>JL, HashMap => HM}
      new JL()
      new HM()

    }
  }

  //

  import com.zishi.scala.Inner



  //在外层包中定义单例对象
  object Outer {
    var out: String = "out"

    def main(args: Array[String]): Unit = {
      println(Inner.in)
    }
  }
  package zishi {
    package scala {
      //内层包中定义单例对象
      object Inner {
        var in: String = "in"

        def main(args: Array[String]): Unit = {
          println(Outer.out)
          Outer.out = "outer"
          println(Outer.out)
        }
      }
    }

  }

}

//在同一文件中定义不同的包
package aaa {
  package bbb {
    object Test01_Package {
      def main(args: Array[String]): Unit = {
        import com.zishi.scala.Inner
        println(Inner.in)
      }
    }
  }

}