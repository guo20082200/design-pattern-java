package com.zishi.scala.a04.okk11

import org.junit.jupiter.api.{BeforeEach, DisplayName, Test}

@DisplayName("不可变列表List的示例")
class ListDemoTest {

  var ls01: List[Int] = null

  @BeforeEach
  def setUp(): Unit = {
    ls01 = List(23, 65, 87)
  }

  @Test
  @DisplayName("Array的创建")
  def testListCreate(): Unit = {
    // 1. 创建一个List
    val ls01 = List(23, 65, 87)
    println(ls01)
  }

  @Test
  @DisplayName("List元素的访问")
  def testListTraversal(): Unit = {
    // 2. 访问和遍历元素
    println(ls01(1))
    ls01.foreach(println)
    val value: List[Int] = ls01.updated(1, 100)
    println(value) // 新数组 List(23, 100, 87)
    println(ls01)
  }

  @Test
  @DisplayName("List添加元素")
  def testListAdd(): Unit = {

    // 3. 添加元素
    val list2 = 10 +: ls01 // 前置追加
    val list3 = ls01 :+ 23 // 后置追加
    println(ls01) // List(23, 65, 87)
    println(list2) // List(10, 23, 65, 87)
    println(list3) // List(23, 65, 87, 23)

    println("==================")

    val list4 = list2.::(51)
    println(list4) // List(51, 10, 23, 65, 87)
    println(list2) // List(10, 23, 65, 87)
    println(list2 == list4) // false

    val list5 = Nil.::(13)
    println(list5) // List(13)

    val list6 = 73 :: 32 :: Nil
    println(list6) // List(73, 32)
    val list7 = 17 :: 28 :: 59 :: 16 :: Nil
    println(list7) // List(17, 28, 59, 16)
  }


  @Test
  @DisplayName("List集合的合并")
  def testListMerge(): Unit = {
    val list6 = List(51, 10, 23, 65, 87)
    val list7 = List(17, 28, 59, 16)
    // 4. 合并列表

    // list6 整体作为 list7 的一个元素加入进去
    val list8 = list6 :: list7
    println(list8) //List(List(51, 10, 23, 65, 87), 17, 28, 59, 16)

    // list6中的元素和list7的元素合并起来
    val list9 = list6 ::: list7
    println(list9) // List(51, 10, 23, 65, 87, 17, 28, 59, 16)

    // list6中的元素和list7的元素合并起来
    val list10 = list6 ++ list7
    println(list10) // List(51, 10, 23, 65, 87, 17, 28, 59, 16)
  }

}
