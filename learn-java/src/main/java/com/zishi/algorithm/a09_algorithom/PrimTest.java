package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * 普利姆算法，
 *
 * 一、Prim算法是采用从点方面考虑来构建MST的一种算法，Prim 算法在稠密图中比Kruskal优，通常步骤如下
 *
 * 1. 从源点出发，将所有与源点连接的点加入一个待处理的集合中
 * 2. 从集合中找出与源点的边中权重最小的点，从待处理的集合中移除标记为确定的点
 * 3. 将找到的点按照步骤1的方式处理
 * 4. 重复2，3步直到所有的点都被标记
 *
 * 可以看到，Prim算法基本上和求最短路径的Dijkstra算法一样，只是稍有改动，
 *
 * 二、Prim算法的正确性证明
 * 1、源点单独一个点时，可以作为MST
 * 2、在与源点相连的点中找出权重最小的加入，显然加入MST后仍然成立
 * 3、将所有的点一个一个加入，如果成环，如果新的边权重更小，则替代权重大的，得到的仍然是MST
 * 4、综上Prim算法中，MST总不被破坏，所以Prim算法总是正确的
 *
 */
public class PrimTest {

    public static void main(String[] args) {

        String[] vertexes = {"A", "B", "C", "D", "E", "F", "G"};
        int[] visitedArr = new int[vertexes.length];
        Graph graph = new Graph();
        List<Edge> edges = graph.edges;
        visitedArr[0] = 1;

        List<String> res = Lists.newArrayList();
        while (true) {
            List<String> visitedList = Lists.newArrayList();
            List<String> unvisitedList = Lists.newArrayList();
            for (int i = 0; i < visitedArr.length; i++) {
                if (visitedArr[i] == 1) {
                    visitedList.add(vertexes[i]);
                } else {
                    unvisitedList.add(vertexes[i]);
                }
            }

            List<Edge> temp = Lists.newArrayList();

            for (String vertex : visitedList) {
                Optional<Edge> min01 = edges.stream()
                        .filter(x -> x.start.equals(vertex) && unvisitedList.contains(x.end))
                        .min(Comparator.comparingInt(o -> o.weight));
                min01.ifPresent(temp::add);
                Optional<Edge> min02 = edges.stream()
                        .filter(x -> x.end.equals(vertex) && unvisitedList.contains(x.start))
                        .min(Comparator.comparingInt(o -> o.weight));
                min02.ifPresent(temp::add);
            }

            // 再取temp集合里面的最小值

            Optional<Edge> optionalEdge = temp.stream().min(Comparator.comparingInt(o -> o.weight));
            Edge min = optionalEdge.get();
            System.out.println(min);
            String minVertex = visitedList.contains(min.start) ? min.end : min.start;
            System.out.println("minVertex:" + minVertex);
            for (int i = 0; i < vertexes.length; i++) {
                if (Objects.equals(minVertex, vertexes[i])) {
                    visitedArr[i] = 1;
                    break;
                }
            }

            if (Arrays.stream(visitedArr).min().getAsInt() == 1) {
                res = visitedList;
                break;
            }
        }

        System.out.println(res);

    }

}


