package com.zishi.scala.a04.okk11

object Test08_ImmutableMap {
  def main(args: Array[String]): Unit = {
    // 1. 创建map
    val map1: Map[String, Int] = Map("a" -> 13, "b" -> 25, "hello" -> 3)
    println(map1)
    println(map1.getClass)

    println("==========================")
    // 2. 遍历元素
    map1.foreach(println)
    map1.foreach((kv: (String, Int)) => println(kv))

    println("============================")

    // 3. 取map中所有的key 或者 value
    for (key <- map1.keys) {
      println(s"$key ---> ${map1.get(key)}")
    }

    /**
     * a ---> Some(13)
     * b ---> Some(25)
     * hello ---> Some(3)
     */

    println(map1.keys) // Set(a, b, hello)
    println(map1.values) // Iterable(13, 25, 3)

    // 4. 访问某一个key的value
    println("a: " + map1.get("a").get)
    println("c: " + map1.get("c"))
    println("c: " + map1.getOrElse("c", 0))

    println(map1("a"))
  }
}