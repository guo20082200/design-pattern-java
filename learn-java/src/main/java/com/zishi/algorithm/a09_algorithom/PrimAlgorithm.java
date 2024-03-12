package com.zishi.algorithm.a09_algorithom;

import java.util.Arrays;
/**
 * @author zk
 * @version 1.0.0
 * @ClassName PrimAlgorithm.java
 * @Description TODO 普利姆算法求最小生成树(修路问题，求总长最小的方案)
 * @createTime 2021年09月30日 15:29:00
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试看看图是否创建 ok
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertex = data.length;
        //邻接矩阵的关系使用二维数组表示,10000 这个大数，表示两个点不联通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},};
        //创建 MGraph 对象
        MGraph graph = new MGraph(vertex);
        //创建一个 MinTree 对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, vertex, data, weight);
        //输出
        minTree.showGraph(graph);
        //测试普利姆算法
        minTree.prim(graph, 1);//

    }
}

class MinTree {
    //创建图的邻接矩阵

    /**
     * @param graph  图对象
     * @param vertex 图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int vertex, char[] data, int[][] weight) {
        for (int i = 0; i < vertex; i++) {
            graph.data[i] = data[i];
            for (int j = 0; j < vertex; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
        System.out.println();
    }

    //编写 prim 算法，得到最小生成树

    /**
     * @param graph 图
     * @param v     表示从图的第几个顶点开始生成'A'->0 'B'->1...
     */
    public void prim(MGraph graph, int v) {
        //visited[] 默认元素的值都是 0, 表示没有访问过  /visited[] 默认元素的值都是 0, 表示没有访问
        int[] visited = new int[graph.vertex];

        // 把当前节点标记为已访问
        visited[v] = 1;
        // h1和h2 记录两个顶点的下表
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; // 将minWeight 初始成一个大数，后面遍历的时候会被替换
        for (int k = 1; k < graph.vertex; k++) { //因为有 graph.verxs 顶点，普利姆算法结束后，有 graph.vertex-1 边
            for (int i = 0; i < graph.vertex; i++) {
                for (int j = 0; j < graph.vertex; j++) {
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值:" + minWeight);
            visited[h2] = 1;
            minWeight = 10000;
        }


    }
}

class MGraph {
    int vertex; // 表示图的节点个数
    char[] data; // 存放节点数据
    int[][] weight; // 存放边，就是我们的邻接矩阵

    public MGraph(int vertex) {
        this.vertex = vertex;
        this.data = new char[vertex];
        weight = new int[vertex][vertex];
    }
}

