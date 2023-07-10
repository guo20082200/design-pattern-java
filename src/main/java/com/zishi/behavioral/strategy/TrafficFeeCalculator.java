package com.zishi.behavioral.strategy;

/**
 * 那么这么写有什么弊端呢？
 * 第一：每一种出行方式的交通花费的计算方式都写在了TrafficFeeCalculator 类中，这个类承担的职责较多，违反了单一职责原则。
 * 第二：假如王二狗突然想自己开车去，那就需要修改goToTianJinEye这个方法了。违反了开闭原则。
 */
public class TrafficFeeCalculator {

    public int goToTianJinEye(CalculateStrategy strategy, int distance) {
        return strategy.calculateTrafficFee(distance);
    }

    public static void main(String[] args) {
        TrafficFeeCalculator calculator = new TrafficFeeCalculator();
        System.out.println(String.format("乘坐公交车到天津之眼的花费为：%d块人民币",
                calculator.goToTianJinEye(new ByBus(), 10)));
        System.out.println(String.format("乘坐滴滴快车到天津之眼的花费为：%d块人民币",
                calculator.goToTianJinEye(new ByDiDiExpress(), 10)));
        System.out.println(String.format("骑共享单车到天津之眼的花费为：%d块人民币",
                calculator.goToTianJinEye(new BySharedBicycle(), 10)));
    }

    /*public int goToTianJinEye(String way, int distance) {
        int trafficFee = 0;
        switch (way) {
            case "bus":
                if (distance < 10)
                    trafficFee = 4;
                else
                    trafficFee = 6;
                break;
            case "didi":
                if (distance < 3)
                    trafficFee = 8;
                else
                    trafficFee = 8 + (distance - 3) * 3;
                break;
            case "sharedBicyle":
                trafficFee = 2;
                break;
            default:
                break;
        }
        return trafficFee;
    }*/
}