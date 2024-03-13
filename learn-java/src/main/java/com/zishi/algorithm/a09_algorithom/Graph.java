package com.zishi.algorithm.a09_algorithom;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    public List<Edge> edges;

    public Graph() {
        this.edges = init();
    }

    public Graph(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * 从给定的边集合edges里面创建最小的生成树
     *
     * @param edges 给定的集合
     * @return
     */
    public List<Edge> mst(List<Edge> edges) {

        // 获取顶点的个数
        Set<String> starts = edges.stream().map(x -> x.start).collect(Collectors.toSet());
        Set<String> ends = edges.stream().map(x -> x.end).collect(Collectors.toSet());
        starts.addAll(ends);
        int size = starts.size();


        edges.sort(Comparator.comparingInt(o -> o.weight));
        //edges.forEach(System.out::println);

        List<Edge> res = Lists.newArrayList();
        for (Edge edge : edges) {
            res.add(edge);
            if (res.size() == 1) {
                continue;
            }
            Boolean exists = this.existsCycle(res);
            System.out.println(exists);
            if (exists) {
                res.remove(res.size() - 1);
            }

            // 6: 顶点的个数-1
            if (res.size() == (size - 1)) {
                break;
            }
        }

        /*System.out.println(res.size());
        res.forEach(System.out::println);*/

        return null;
    }

    /**
     * 判断集合中的边是否存在环
     *
     * @param edges 边的集合
     *              <p>
     *              拓扑排序法判断一个无向图中是否有环
     *              使用拓扑排序可以判断一个无向图中是否存在环，具体步骤如下：
     *              1. 求出图中所有节点的度。
     *              2. 将所有度 <= 1 的节点入队。
     *              3. 当队列不空时进入循环，弹出队首元素，把与队首元素相邻节点的度减一。如果相邻节点的度变为1，则将相邻节点入队。队列为空则退出循环。
     *              4. 循环结束时判断已经访问过（进入过队列）的节点数是否等于 n。等于 n 说明全部节点都被访问过，无环；反之，则有环。
     */
    public boolean existsCycle(List<Edge> edges) {
        //1. 求出来所有的节点
        List<String> starts = edges.stream().map(x -> x.start).collect(Collectors.toList());
        List<String> ends = edges.stream().map(x -> x.end).collect(Collectors.toList());
        starts.addAll(ends);
        Set<String> set = Sets.newHashSet(starts);
        List<String> vertexes = Lists.newArrayList(set); // 所有的顶点
        long[] degrees = new long[vertexes.size()];
        List<String> visitedList = Lists.newArrayList();
        // 2. 求出来所有顶点的度
        for (int i = 0; i < vertexes.size(); i++) {
            String vertex = vertexes.get(i);
            long count01 = edges.stream().filter(x -> x.start.equals(vertex)).count();
            long count02 = edges.stream().filter(x -> x.end.equals(vertex)).count();
            degrees[i] = count01 + count02;
        }

        //3. 队列
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] <= 1) {
                queue.offer(vertexes.get(i));
                visitedList.add(vertexes.get(i));
            }
        }

        // 当队列不空时进入循环，弹出队首元素，把与队首元素相邻节点的度减一。如果相邻节点的度变为1，则将相邻节点入队。队列为空则退出循环。
        while (!queue.isEmpty()) {
            String head = queue.poll();
            //visitedList.add(head);
            // 找到关联的元素，把度减1，然后判断当前的度是否为1，如果是1，进入队列
            // 1. head为开始点的边
            List<String> startsByHead = edges.stream()
                    .filter(x -> x.start.equals(head))
                    .map(x -> x.end)
                    .collect(Collectors.toList());

            // 1.  head为结束点的边
            List<String> endsByHead = edges.stream()
                    .filter(x -> x.end.equals(head))
                    .map(x -> x.start)
                    .collect(Collectors.toList());

            startsByHead.addAll(endsByHead);
            for (int i = 0; i < vertexes.size(); i++) {
                String vertex = vertexes.get(i);
                if (startsByHead.contains(vertex) && !visitedList.contains(vertex)) {
                    // 度减小1，
                    degrees[i] -= 1;
                    // 将数据加入队列
                    if (degrees[i] <= 1) {
                        queue.offer(vertex);
                        visitedList.add(vertex); // 加入已经访问的集合
                    }

                }
            }
        }

        return visitedList.size() != vertexes.size();
    }

    /**
     * 使用 DFS 可以判断一个无向图和有向中是否存在环。
     * <p>
     * 1. 深度优先遍历图，如果在遍历的过程中，
     * 发现某个结点有一条边指向已访问过的结点，
     * 并且这个已访问过的结点不是上一步访问的结点，则表示存在环。
     * <p>
     * 2. 我们不能仅仅使用一个 bool 数组来表示结点是否访问过。规定每个结点都拥有三种状态，白、灰、黑。
     * 开始时所有结点都是白色，当访问过某个结点后，该结点变为灰色，当该结点的所有邻接点都访问完，该节点变为黑色。
     * <p>
     * 3. 那么我们的算法可以表示为：如果在遍历的过程中，发现某个结点有一条边指向灰色节点，并且这个灰色结点不是上一步访问的结点，那么存在环。
     *
     * @return
     */
    public boolean existsCycleUseDFS(List<Edge> edges) {

        return false;
    }

    private static List<Edge> init() {
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
