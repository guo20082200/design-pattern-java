package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

/**
 * 克鲁斯卡算法
 * <p>
 * ①简介
 * Kruskal算法，相较于Prim算法是基于点的操作，Kruskal算法是基于边的操作，思想也比Prim简单，更容易理解，主要步骤如下
 * <p>
 * 将边按照权重从小到大排列
 * 枚举第一个边，加入MST里，判断是否成环
 * 如果成环则跳过，否则确定这条边为MST里的
 * 继续枚举下一条边，直到所有的边都枚举完
 * <p>
 * ②Kruskal的正确性证明
 * 将边排序完成后，第一条边加入MST时，MST总能成立
 * 加入第二条边时，MST还是成立
 * 加入第三条边时，如果三条边成环，则跳过，因为边是按权重从小到大排序，此时MST仍然成立，不成环时直接加入MST，不破坏MST的结构
 * 综上，直到枚举直到所有的点-1条边都枚举完，MST总能成立
 */
public class KruskalTest {

    public static void main(String[] args) {
        Graph graph = new Graph();
        List<Edge> edges = graph.edges;

    }
}
