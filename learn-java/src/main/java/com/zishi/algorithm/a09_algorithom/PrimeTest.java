package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 普利姆算法，
 */
public class PrimeTest {

    public static void main(String[] args) {

        List<Edge> edges = init();
        List<String> starts = edges.stream().map(x -> x.start).collect(Collectors.toList());
        List<String> ends = edges.stream().map(x -> x.end).collect(Collectors.toList());
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
        return  res;
    }
}


class Edge {

    public String start;
    public String end;
    public int weight;

    public Edge(String start, String end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}