package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * 普利姆算法，
 */
public class PrimTest {

    public static void main(String[] args) {

        String[] vertexes = {"A", "B", "C", "D", "E", "F", "G"};
        int[] visitedArr = new int[vertexes.length];
        List<Edge> edges = init();
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


    public static List<Edge> init() {
        List<Edge> res = Lists.newArrayList();
        res.add(new Edge("A", "B", 5));
        res.add(new Edge("A", "C", 7));
        res.add(new Edge("A", "G", 2));
        res.add(new Edge("B", "G", 3));
        res.add(new Edge("B", "D", 9));
        res.add(new Edge("C", "E", 8));
        res.add(new Edge("D", "F", 4));
        res.add(new Edge("E", "G", 4));
        res.add(new Edge("E", "F", 5));
        res.add(new Edge("F", "G", 6));
        return res;
    }
}


