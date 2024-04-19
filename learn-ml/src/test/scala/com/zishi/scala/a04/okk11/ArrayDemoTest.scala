package com.zishi.scala.a04.okk11;


import org.junit.jupiter.api.{BeforeEach, DisplayName, Test};

@DisplayName("不可变数组Array的示例")
class ArrayDemoTest {

  var arr2: Array[Int] = null

  @BeforeEach
  def setUp(): Unit = {
    arr2 = Array(12, 37, 42, 58, 97)
  }

  @Test
  @DisplayName("Array的创建")
  def testArrayCreate(): Unit = {
    // 1. 创建数组
    val arr: Array[Int] = new Array[Int](5)
    // 另一种创建方式
    val arr2 = Array(12, 37, 42, 58, 97) //省略.apply()方法，apply 底层自动调用
    println(arr.getClass) //class [I
    println(arr.getClass.getSuperclass) //class java.lang.Object
    val arr3: Array[Int] = Array.apply(12, 37, 42, 58, 97)
    val array: Array[Int] = Array.emptyIntArray
  }

  @Test
  @DisplayName("Array的所有方法获取")
  def testGetAllMethods(): Unit = {
    // 1. 获取所有的方法
    //val methods = arr2.getClass.getDeclaredMethods
    val methods = arr2.getClass.getMethods
    println(methods.indices)
    methods.foreach( println(_))


  }

  @Test
  @DisplayName("Array的添加方法")
  def testArrayAdd(): Unit = {
    // 1. 添加元素
    val newArr = arr2.:+(73)
    println(arr2.mkString("--"))
    println(newArr.mkString("--"))

    val newArr2 = newArr.+:(30)
    println(newArr2.mkString("--"))

    val newArr3 = newArr2 :+ 15
    val newArr4 = 19 +: 29 +: newArr3 :+ 26 :+ 73
    println(newArr4.mkString(", "))


  }

  @Test
  @DisplayName("Array的Api测试")
  def testArrayApi01(): Unit = {
    // 1. 按照索引获取
    println(arr2(2))
    // 2. 获取数组的长度
    println(arr2.length)
    // 3. 遍历
    //val ar = 1 to 5
    //println(ar.getClass) //class scala.collection.immutable.Range$Inclusive
    for (i <- arr2.indices) {
      println(arr2(i))
    }

    // arr2.indices 就是：Range 0 until 5
    println(arr2.indices) // Range 0 until 5

    // 3. 数组的遍历
    // 1) 普通for循环
    for (i <- 0 until arr2.length) {
      println(arr2(i))
    }

    for (i <- arr2.indices) println(arr2(i))

    println("---------------------")

    // 2) 直接遍历所有元素，增强for循环
    for (elem <- arr2) println(elem)

    println("---------------------")

    // 3) 迭代器
    val iter = arr2.iterator

    while (iter.hasNext)
      println(iter.next())

    println("---------------------")

    // 4) 调用foreach方法
    arr2.foreach((elem: Int) => println(elem))

    arr2.foreach(println)

    println(arr2.mkString("--"))

    // 4.按照索引修改元素
    arr2(2) = 100
    println(arr2(2)) // 100
    arr2.update(2, 300)
    println(arr2(2))

    // 5. 判断数组是否包含某个元素
    def func(elem: Int): Boolean = {
      false
    }

    arr2.exists(_ == 3)
    arr2.contains(3)

    // 6. 获取第一个元素,最后一个元素
    println(arr2.head)
    println(arr2.tail)




  }

  @Test
  @DisplayName("Array的Api02测试")
  def testArrayApi02(): Unit = {
    val value = arr2.##
    println(value)
  }


}