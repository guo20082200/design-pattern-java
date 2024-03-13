package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

public class Edge implements Comparator<Edge> {

    public String start;
    public String end;
    public int weight;

    public Edge(String start, String end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compare(Edge o1, Edge o2) {
        return o1.weight - o2.weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", weight=" + weight +
                '}';
    }




}