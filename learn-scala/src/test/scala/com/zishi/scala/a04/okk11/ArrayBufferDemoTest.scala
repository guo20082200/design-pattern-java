package com.zishi.scala.a04.okk11

import org.junit.jupiter.api.{BeforeEach, DisplayName, Test}

import scala.collection.mutable.ArrayBuffer;

@DisplayName("可变数组Array的示例")
class ArrayBufferDemoTest {

  var arr2: ArrayBuffer[Int] = null

  @BeforeEach
  def setUp(): Unit = {
    arr2 = ArrayBuffer(23, 57, 92)
  }

  @Test
  @DisplayName("ArrayBuffer的创建")
  def testArrayCreate(): Unit = {
    // 1. 创建可变数组
    val arr1: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val arr2 = ArrayBuffer(23, 57, 92)

    println(arr1) //scala.collection.mutable.ArrayBuffer
    println(arr2)
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

    // 初始： ArrayBuffer(23, 57, 92)
    // 3. 添加元素
    val newArr1 = arr2 :+ 15
    println(arr2) //ArrayBuffer(23, 57, 92, 15)
    println(newArr1)
    println(arr2 == newArr1) // false

    val newArr2 = arr2 += 19
    println(arr2) // ArrayBuffer(23, 57, 92, 19)
    println(newArr2) // ArrayBuffer(23, 57, 92, 19)
    println(arr2 == newArr2) // true
    newArr2 += 13
    println(arr2) // ArrayBuffer(23, 57, 92, 19, 13)
    // newArr2的修改，会影响arr2

    /**
     * :+ 和 += 的区别：
     * :+： 新建了一个 ArrayBuffer
     * +=：还是原来的 ArrayBuffer
     */

    println("-----------------------------------------------")
    // arr2 的修改，会影响newArr2
    77 +=: arr2 // 前置追加元素，是 prepend的别名
    println(arr2) // ArrayBuffer(77, 23, 57, 92, 19, 13)
    println(newArr2) // ArrayBuffer(77, 23, 57, 92, 19, 13)

    arr2.append(36) // 往后追加
    arr2.prepend(11, 76) // 前置追加
   // arr2.insert(1, 13, 59)
    println(arr2)
    println(newArr2)

    println("-----------------------------------------------")
    println(newArr1) // ArrayBuffer(23, 57, 92, 15)

    println(arr2)
    // 从第二个索引位置开始插入整个数组元素
    // arr2第二个索引之后的元素往后移动
    arr2.insertAll(2, newArr1)
    println(arr2)

    // 从arr2索引0的位置插入整个数组newArr2元素
    val arr3 = ArrayBuffer(-1, -3, 0)
    arr2.prependAll(arr3)
    println(arr2)
    println(newArr2) // arr2的修改同时影响了newArr2


  }

  @Test
  @DisplayName("ArrayBuffer访问元素")
  def testArrayBufferApi01(): Unit = {
    // 2. 访问元素
    // println(arr1(0)) // error
    println(arr2(1))
    arr2(1) = 39
    println(arr2(1))
  }

  @Test
  @DisplayName("Array的删除元素测试")
  def testArrayBufferDelete(): Unit = {

    val arr0 = ArrayBuffer(-1, -3, 0, 11, 76, 23, 57, 92, 15, 77, 23, 57, 92, 19, 13, 36)
    // 4. 删除元素
    arr0.remove(3) // 移除索引为3的元素
    println(arr0)

    arr0.remove(0, 10) // 移除索引 0 到 10 的元素
    println(arr0)

    arr0 -= 13 // 移除数组中元素内容为13的元素
    println(arr0) // ArrayBuffer(57, 92, 19, 36)
  }

  @Test
  @DisplayName("ArrayBuffer可变数组转换为不可变数组Array")
  def testArrayBuffer2Array(): Unit = {
    // 5. 可变数组转换为不可变数组
    val arr: ArrayBuffer[Int] = ArrayBuffer(23, 56, 98)
    val newArr: Array[Int] = arr.toArray
    println(newArr.mkString(", "))
    println(arr)

    // 6. 不可变数组转换为可变数组
    val buffer = newArr.toBuffer
    println(buffer) // ArrayBuffer(23, 56, 98)
    println(newArr.mkString("Array(", ", ", ")")) // [I@7d61eb55
  }

}