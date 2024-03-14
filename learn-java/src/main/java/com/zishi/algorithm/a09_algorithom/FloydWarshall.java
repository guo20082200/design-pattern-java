package com.zishi.algorithm.a09_algorithom;

import java.util.Arrays;

public class FloydWarshall {
    private int V; // 顶点数量
    private int[][] dist; // 存储从一个顶点到另一个顶点的最短路径长度
    private int[][] s; // 存储从一个顶点到另一个顶点的最短路径长度

    // 初始化FloydWarshall对象
    public FloydWarshall(int V) {
        this.V = V;
        dist = new int[V][V];
        s = new int[V][V];
        // 初始化距离矩阵，如果(i, j)之间有一条直接边，则dist[i][j]为其权重，否则为Integer.MAX_VALUE表示无穷大
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = 1000000;
                s[i][j] = j + 1;
            }
            dist[i][i] = 0; // 自己到自己的距离为0
            s[i][i] = 0;
        }
    }

    // 添加边到图中
    public void addEdge(int src, int dest, int weight) {
        dist[src][dest] = weight;
        dist[dest][src] = weight;
    }

    // Floyd-Warshall算法
    public void floydWarshall() {
        for (int k = 0; k < V; k++) {
            System.out.println(".................k :" + k);
            printArr(dist);
            System.out.println("............................");
            printArr(s);
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    // 更新dist[i][j]为dist[i][j]和dist[i][k] + dist[k][j]中较小的一个
                    if (i != j && i != k && j != k) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                        s[i][j] = k;
                    }
                }
            }
        }


    }

    public void printArr(int[][] dist) {
        for (int[] arr : dist) {
            System.out.println(Arrays.toString(arr));
        }
    }

    // 获取从src到dest的最短路径长度
    public int getShortestPathLength(int src, int dest) {
        return dist[src][dest];
    }

    public static void main(String[] args) {
        // 创建一个有向图
        FloydWarshall graph = new FloydWarshall(5);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 10);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 6);
        graph.addEdge(2, 4, 15);
        graph.addEdge(3, 4, 4);

        // 计算所有顶点对的最短路径
        graph.floydWarshall();

        // 输出从顶点0到顶点3的最短路径长度
        System.out.println("Shortest distance between vertex 0 to vertex 3: " + graph.getShortestPathLength(0, 3));
    }
}
