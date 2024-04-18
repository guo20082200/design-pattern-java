package com.zishi.scala.a03.okk10

object 适配器设计模式 {

  //1. 定义特质PlayLOL, 添加6个抽象方法, 分别为: top(), mid(), adc(), support(), jungle(), schoolchild()
  trait PlayLOL {
    def top(): Unit   //上单
    def mid(): Unit   //中单
    def adc(): Unit   //下路
    def support(): Unit   //辅助
    def jungle(): Unit    //打野
    def schoolchild(): Unit   //小学生
  }

  //2. 定义抽象类Player, 继承PlayLOL特质, 重写特质中所有的抽象方法, 方法体都为空.
  //Player类充当的角色就是：适配器类
  abstract class Player extends PlayLOL {
    override def top(): Unit = {}
    override def mid(): Unit = {}
    override def adc(): Unit = {}
  }

  //3. 定义普通类GreenHand, 继承Player, 重写抽象方法。
  private class GreenHand extends Player{
    //override def top(): Unit = println("上单盖伦！")
    //override def mid(): Unit = println("中单亚索！")
    override def adc(): Unit = println("adc寒冰！")
    override def support(): Unit = println("辅助布伦！")
    override def jungle(): Unit = println("打野剑圣！")
    override def schoolchild(): Unit = println("我是小学生, 你骂我, 我就挂机!")
  }

  //4. 定义main方法, 在其中创建GreenHand类的对象, 并调用其方法进行测试.
  def main(args: Array[String]): Unit = {
    //创建GreenHand类的对象
    val gh = new GreenHand()
    //调用GreenHand类中的方法
    gh.support()
    gh.schoolchild()
  }

}
