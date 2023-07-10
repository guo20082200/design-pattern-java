package com.zishi.behavioral.strategy;

//乘坐滴滴快车算法
public class ByDiDiExpress implements CalculateStrategy {
    @Override
    public int calculateTrafficFee(int distance) {
        return distance<3?8:(8+(distance-3)*3);
    }
}