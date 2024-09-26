package com.zishi.kt01


fun main(args: Array<String>) {


    println("Hello Kotlin")


    //立即初始化
    var var_a: Int = 10

    //推导出类型
    var var_b = 5

    //没有初始化的时候，必须声明类型
    var var_c: Float
    var_c = 12.3f
    var_c += 1

    println("var_a => $var_a \t var_b => $var_b \t var_a => $var_c")

    //立即初始化
    val val_a: Int = 100

    //推导出类型
    val val_b = 50

    //没有初始化的时候，必须声明类型
    val val_c: Int
    val_c = 1
    // val_c += 1 因为c是常量，所以这句代码是会报错的

    println("val_a => $val_a \t val_b => $val_b \t val_c => $val_c")


    Test1()

    Test2()
}

/**
 * 类中声明变量
 */
class Test1{

    // 定义属性
    var var_a : Int = 0
    val val_a : Int = 0

    // 初始化
    init {
        var_a = 10
        //val_a = 0 //为val类型不能更改。 Val cannot be reassigned

        println("var_a => $var_a \t val_a => $val_a")
    }
}

/**
 * 声明可空变量
 * 1. 在声明的时候一定用标准的声明格式定义。不能用可推断类型的简写。
 * 2. 变量类型后面的?符号不能省略。不然就和普通的变量没区别了。
 * 3. 其初始化的值可以为null或确定的变量值。
 *
 * 定义格式：var/val 变量名 ： 类型? = null/确定的值
 */
class Test2{

    // 声明可空变量

    var var_a : Int? = 0
    val val_a : Int? = null

    init {
        var_a = 10
        // val_a = 0 为val类型不能更改。

        println("var_a => $var_a \t val_a => $val_a")
    }
}





