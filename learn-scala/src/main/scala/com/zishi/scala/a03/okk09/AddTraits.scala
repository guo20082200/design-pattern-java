package com.zishi.scala.a03.okk09

//看看混入多个特质的特点(叠加特质)

/**
 * 叠加特质注意事项和细节:
 * 1. 特质声明顺序从左到右。
 * 2. Scala在执行叠加对象的方法时，会首先从后面的特质(从右向左)开始执行
 * 3. Scala中特质中如果调用super，并不是表示调用父特质的方法，而是向前面(左边)继续 查找特质，如果找不到，才会去父特质查找
 * 4. 如果想要调用具体特质的方法，可以指定:super[特质].xxx(…).其中的泛型必须是该特质的 直接超类类型
 *
 * 富接口: 该特质中既有抽象方法，又有非抽象方法
 *
 * 特质中的具体字段:
 * 1. 特质中可以定义具体字段，如果初始化了就是具体字段，如果不初始化就是抽象字段。混入该特质
 *    的类就具有了该字段，字段不是继承，而是直接加入类，成为自己的字段。
 *
 * 2.
 */
object AddTraits {
  def main(args: Array[String]): Unit = { //说明
    //1. 创建 MySQL4 实例时，动态的混入 DB4 和 File4
    //研究第一个问题，当我们创建一个动态混入对象时(调用了构造方法)，其顺序是怎样的 //总结一句话
    //Scala 在叠加特质的时候，会首先从后面的特质开始执行(即从左到右) //1.Operate4...
    //2.Data4
    //3.DB4
    //4.File4

    // trait DB4 extends Data4, trait File4 extends Data4, trait Data4 extends Operate4
    // 即父子关系： 先执行父，再执行子，同级关系：从左到右
    val mysql = new MySQL4 with DB4 with File4
    println(mysql)
    //研究第 2 个问题，当我们执行一个动态混入对象的方法，其执行顺序是怎样的
    //顺序是，(1)从右到左开始执行 , (2)当执行到 super 时，是指的左边的特质 (3) 如果左边没有特质 了，则 super 就是父特质
    //1. 向文件"
    //2. 向数据库
    //3. 插入数据 100
    mysql.insert(100)
  }
}

trait Operate4 { //特点
  println("Operate4...")

  def insert(id: Int) //抽象方法 }
  trait Data4 extends Operate4 { //特质，继承了 Operate4
    println("Data4")

    override def insert(id: Int): Unit = { //实现/重写 Operate4 的 insert
      println("插入数据 = " + id)
    }
  }

}

trait Data4 extends Operate4 { //特质，继承了 Operate4
  println("Data4")

  override def insert(id: Int): Unit = { //实现/重写 Operate4 的 insert
    println("插入数据 === " + id)
  }
}

trait DB4 extends Data4 { //特质，继承 Data4
  println("DB4")

  override def insert(id: Int): Unit = { // 重写 Data4 的 insert
    println("向数据库")
    super.insert(id)
  }
}

trait File4 extends Data4 { //特质，继承 Data4
  println("File4")

  override def insert(id: Int): Unit = { // 重写 Data4 的 insert
    println("向文件")
    super.insert(id) //调用了 insert 方法(难点)，这里 super 在动态混入时，不一定是父类
  }
}

class MySQL4 {} //普通类
