package com.zishi.scala.a04.okk11

import org.junit.jupiter.api.{BeforeEach, DisplayName, Test}

import scala.collection.mutable.ListBuffer

@DisplayName("可变列表ListBuffer的示例")
class ListBufferDemoTest {

  var list1: ListBuffer[Int] = null
  var list2: ListBuffer[Int] = null

  @BeforeEach
  def setUp(): Unit = {
    list1 = new ListBuffer[Int]()
    list2 = ListBuffer(12, 53, 75, 100, 1000)
  }

  @Test
  @DisplayName("ListBuffer的创建")
  def testListCreate(): Unit = {
    // 1. 创建一个List
    // 1. 创建可变列表
    val list1: ListBuffer[Int] = new ListBuffer[Int]()
    val list2 = ListBuffer(12, 53, 75)

    println(list1)
    println(list2)
  }

  @Test
  @DisplayName("ListBuffer元素的访问")
  def testListTraversal(): Unit = {
    // 2. 访问和遍历元素

  }

  @Test
  @DisplayName("ListBuffer添加元素")
  def testListAdd(): Unit = {

    // 3. 添加元素
    // list1.append(15, 62) // Symbol append is deprecated. Use appendAll instead
    list1.appendAll(Array(15, 62))

    list2.prepend(20)

    list1.insert(1, 19)

    println(list1)
    println(list2)

    println("==============")

    // +=: 就是 prepend 是 Buffer 的函数
    // +=  就是 addOne, 是 Growable的函数
    31 +=: 96 +=: list1 += 25 += 11
    println(list1)

    println("==============")

  }


  @Test
  @DisplayName("ListBuffer集合的合并")
  def testListBufferMerge(): Unit = {
    val list3 = list1 ++ list2

    val list4 = list1 += 3
    println(list1)
    println(list4)
    println(list2)
    println(list3)

    println("==============")

    // ++=:  就是 prependAll
    list1 ++=: list2
    println(list1)
    println(list2)

  }

  @Test
  @DisplayName("ListBuffer集合元素的修改")
  def testListBufferUpdate(): Unit = {
    // 4. 修改元素
    list2(3) = 30
    list2.update(0, 89)
    println(list2)
  }

  @Test
  @DisplayName("ListBuffer集合元素的删除")
  def testListBufferDelete(): Unit = {
    // 5. 删除元素
    // ListBuffer(89, 53, 75, 30, 1000)
    list2.remove(2)
    println(list2)
    list2 -= 1000
    println(list2)
  }


}
