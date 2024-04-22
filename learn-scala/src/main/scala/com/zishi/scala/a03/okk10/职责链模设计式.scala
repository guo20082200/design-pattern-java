package com.zishi.scala.a03.okk10

object 职责链模设计式 {
  //1. 定义一个父特质 Handler, 表示处理数据(具体的支付逻辑)
  trait Handler {
    def handle(data:String): Unit = {
      println("具体处理数据的代码（例如：转账逻辑）")
      println(data)
    }
  }
  //2. 定义一个子特质 DataValidHandler, 表示 校验数据.
  private trait DataValidHandler extends Handler{
    override def handle(data: String): Unit = {
      println("校验数据...")
      super.handle(data)
    }
  }
  //3. 定义一个子特质 SignatureValidHandler, 表示 校验签名.
  private trait SignatureValidHandler extends Handler {
    override def handle(data: String): Unit = {
      println("校验签名...")
      super.handle(data)
    }
  }
  //4. 定义一个类Payment, 表示: 用户发起的支付请求.
  private class Payment extends DataValidHandler with SignatureValidHandler {
    def pay(data:String): Unit = {
      println("用户发起支付请求...")
      super.handle(data)
    }
  }

  def main(args: Array[String]): Unit = {
    //5. 创建Payment类的对象, 模拟: 调用链.
    val pm = new Payment
    pm.pay("shuyv给xin转账10000￥")
  }
  // 程序运行输出如下：
  // 用户发起支付请求...
  // 校验签名...
  // 校验数据...
  // 具体处理数据的代码(例如: 转账逻辑)
  // 苏明玉给苏大强转账10000元

}
