package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DijkstraTest {

    public static final Integer MAX = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Graph graph = new Graph();
        List<Edge> edges = graph.edges;

        List<String> vertexes = graph.vertexes;

        /**
         * 键：顶点字母
         * 值：距离参考点的距离
         */
        Map<String, Integer> visitedMap = Maps.newLinkedHashMap();
        visitedMap.put(vertexes.get(0), 0);
        Map<String, Integer> unvisitedMap = Maps.newLinkedHashMap();
        for (int i = 1; i < vertexes.size(); i++) {
            unvisitedMap.put(vertexes.get(i), MAX);
        }
        // 更新距离
        /*List<Edge> from = edges.stream().filter(x -> x.start.equals(vertexes.get(0))).collect(Collectors.toList());
        List<Edge> to = edges.stream().filter(x -> x.end.equals(vertexes.get(0))).collect(Collectors.toList());
        for (Edge edge : from) {
            unvisitedMap.put(edge.end, edge.weight);
        }
        for (Edge edge : to) {
            unvisitedMap.put(edge.start, edge.weight);
        }*/

        // 开始更新距离
        String currentVertex = vertexes.get(0);
        while (visitedMap.size() != vertexes.size()) {

            // 找到 unvisitedMap 里面的最小的距离，不是邻接点集合的最小距离
            String finalCurrentVertex = currentVertex;

            // 找到所有的邻接点，
            List<Edge> collect = edges.stream()
                    .filter(x ->
                            ((x.start.equals(finalCurrentVertex) && unvisitedMap.containsKey(x.end)) ||
                                    (x.end.equals(finalCurrentVertex)) && unvisitedMap.containsKey(x.start)))
                    .collect(Collectors.toList());

            // 计算所有的邻接点距离参考点的距离， 并更新 unvisitedMap
            Integer preDistance = visitedMap.get(finalCurrentVertex);

            for (Edge edge : collect) {
                String v;
                if (edge.start.equals(finalCurrentVertex)) {
                    v = edge.end;
                } else {
                    v = edge.start;
                }
                int newDistance = preDistance + edge.weight;
                int oldDistance = unvisitedMap.get(v);
                // 新计算的距离小于原来的距离才进行更新的操作
                if (newDistance < oldDistance) {
                    unvisitedMap.put(v, newDistance);
                }
            }

            // 从 unvisitedMap 里面取到最小的距离
            Integer minDistance = unvisitedMap.values().stream().min(Comparator.comparingInt(o -> o)).orElse(MAX);

            Set<Map.Entry<String, Integer>> entries = unvisitedMap.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (minDistance.equals(value)) {
                    currentVertex = key;
                    break;
                }
            }
            System.out.println("currentVertex:" + currentVertex);
            // 更新 visitedMap
            visitedMap.put(currentVertex, minDistance);
            // 更新 unvisitedMap
            unvisitedMap.remove(currentVertex);
        }

        visitedMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
