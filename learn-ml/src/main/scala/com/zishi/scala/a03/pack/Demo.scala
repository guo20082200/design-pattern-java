package com.zishi.scala.a03.pack

/**
 * 使用打包技术来解决不同包下Cat类
 */
object Demo {

  import com.zishi._

  val cat = new abc.Cat()
  val cat2 = new deg.Cat()
}


package com.zishi.abc {
  class Cat {}
}

package com.zishi.deg {
  class Cat {}
}

/**
 * 包也可以像嵌套类那样嵌套使用（包中有包）,
 * 这个在前面的第三种打包方式已经讲过了，
 * 在使用第三种方式时的好处是：程序员可以在同一个文件中，将类(class / object)、trait 创建在不同的包中，这样就非常灵活了。
 */

package com.zishi2 {
  // 在 com.zishi2包下面的类
  class User {

  }

  // 类对象就是在Monster$， 也在com.zishi2包下面
  object Monster {

  }

  package order {

    // 在 com.zishi2.order包下面的类
    class User {

    }
  }

}

/**
 * 3)作用域原则：可以直接向上访问。即: Scala中子包中直接访问父包中的内容,
 * 大括号体现作用域。(提示：Java中子包使用父包的类，需要import)。
 * 在子包和父包 类重名时，默认采用就近原则，如果希望指定使用某个类，则带上包名即可。
 */
package com.amcre {
  //这个类就是在com.amcre包下
  class User {
  }

  //这个类对象就是在Monster$ , 也在com.amcre包下
  object Monster {
  }

  class Dog {
  }
  package scala {
    //这个类就是在com.amcre.scala包下
    class User {
    }

    //这个Test 类对象
    object Test {
      def main(args: Array[String]): Unit = {
        //子类可以直接访问父类的内容
        var dog = new Dog()
        println("dog=" + dog)
        //在子包和父包 类重名时，默认采用就近原则.
        var u = new User()
        println("u=" + u)
        //在子包和父包 类重名时，如果希望指定使用某个类，则带上包路径
        var u2 = new com.amcre.User()
        println("u2=" + u2)
      }
    }
  }

}

/**
 * 父包要访问子包的内容时，需要import对应的类等
 */
package com.zishi {
  //引入在com.atguigu 包中希望使用到子包的类Tiger,因此需要引入.

  //这个类就是在com.atguigu包下
  class User {
  }
  package scala {
    //Tiger 在 com.atguigu.scala 包中
    class Tiger {}
  }
  import com.zishi.scala.Tiger
  object Test2 {
    def main(args: Array[String]): Unit = {
      //如果要在父包使用到子包的类，需要import
      val tiger = new Tiger()
      println("tiger=" + tiger)
    }
  }
}

/**
 * 5)可以在同一个.scala文件中，声明多个并列的package(建议嵌套的pakage不要超过3层)
 */
package d {

}

package a {
  package b {
    package c {
      class Person {

      }
    }

  }

}

/**
 * 6)包名可以相对也可以绝对，比如，访问BeanProperty的绝对路径是：_root_.scala.beans.BeanProperty ，
 * 在一般情况下：我们使用相对路径来引入包，只有当包名冲突时，使用绝对路径来处理。
 */
package com.abc.aaa {
  class Manager(var name: String) {
    //第一种形式
    //@BeanProperty var age: Int = _
    //第二种形式, 和第一种一样，都是相对路径引入
    //@scala.beans.BeanProperty var age: Int = _
    //第三种形式, 是绝对路径引入，可以解决包名冲突
    @_root_.scala.beans.BeanProperty var age: Int = _
  }

  object TestBean {
    def main(args: Array[String]): Unit = {
      val m = new Manager("jack")
      println("m=" + m)
    }
  }
}
