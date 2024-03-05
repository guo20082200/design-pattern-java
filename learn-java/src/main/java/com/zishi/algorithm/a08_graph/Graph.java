package com.zishi.algorithm.a08_graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    private ArrayList<String> vertexList; // 存放顶点集合
    private int[][] edges; // 矩阵
    private int num; // 边的数目
    public boolean[] isVisited; // 记录被访问了的节点

    public Graph(int n) {
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        num = 0;
        isVisited = new boolean[n];
    }

    // 插入节点
    public void addVer(String vertex) {
        vertexList.add(vertex);
    }

    // 添加边
    public void addNum(int v1, int v2, int weight) {
        // v1和v2指的是点的下标。weight表示权值
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        num++;
    }

    // 返回节点的个数
    public int nodeNum() {
        return vertexList.size();
    }

    // 返回边一共有多少条
    public int edgeNum() {
        return num;
    }

    // 通过索引返回值
    public String getValue(int i) {
        return vertexList.get(i);
    }

    // 返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    // 显示图对应的矩阵
    public void show() {
        for (int[] link : edges) {
            System.err.println(Arrays.toString(link));
        }
    }


    // 得到第一个邻接节点的下标
    public int getFirstNode(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) { // 存在下一个节点
                return j;
            }
        }
        return -1;
    }

    // 根据前一个邻接节点的下标获取下一个邻接节点
    public int getNextNode(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 1. 访问初始结点v。并标记结点v为已访问。
     * 2. 查找结点v的第-一个邻按结点w.
     * 3. 若w存在，则继续执行4，如果w不存在，则回到第1步， 将从v的下一个结点继续。
     * 4. 若w未被访问，对w进行深度优先遍历递归(即把w当做另一个v，然后进行步骤123)。
     * 5. 查找结点v的w邻按结点的下一个邻接结点，转到步骤3
     * @param isVisited:当前节点是否访问过
     * @param i
     */
    private void depthFirstSearch(boolean[] isVisited, int i) {
        // 先进行访问i
        System.out.print(getValue(i) + " -> ");
        // 把这个节点设置为已访问
        isVisited[i] = true;
        // 查找第一个邻接节点
        int w = getFirstNode(i);
        while (w != -1) {
            if (!isVisited[w]) {// 节点还未被访问
                depthFirstSearch(isVisited, w);
            }
            // 已经被访问,查找下一个邻接 节点
            w = getNextNode(i, w);
        }
    }

    // 对DFS进行重载
    public void depthFirstSearch() {
        // 遍历所有节点进行DFS
        for (int i = 0; i < nodeNum(); i++) {
            if (!isVisited[i]) {
                depthFirstSearch(isVisited, i);
            }
        }
    }


    /**
     * 广度优先遍历的步骤：
     * 1. 访问初始结点v并标记结点v为已访问。
     * 2. 结点v入队列
     * 3. 当队列非空时， 继续执行，否则算法结束。
     * 4. 出队列， 取得队头结点u。.
     * 5. 查找结点u的第一 个邻接结点w.
     * 6. 若结点u的邻接结点w不存在，则转到步骤3;否则循环执行以下三个步骤：
     *      6.1 若结点w尚未被访问，则访问结点w并标记为己访问。
     *      6.2 结点w入队列
     *      6.3 查找结点u的继w邻接结点后的下一个邻接结点w,转到步骤6。
     *
     * @param isVisited
     * @param i
     */
    public void breadthFirstSearch(boolean[] isVisited, int i) {
        int u; // 队列的头节点
        int w; // 邻接节点
        LinkedList<Integer> queue = new LinkedList<>(); // 使用队列记录访问顺序
        System.out.print(getValue(i) + " -> ");
        isVisited[i] = true;
        queue.addLast(i);
        while (!queue.isEmpty()) {
            // 取出队列头节点下标
            u = queue.removeFirst();
            w = getFirstNode(u);
            while (w != -1) {
                // 是否被访问过
                if (!isVisited[w]) {
                    System.out.print(getValue(w) + " -> ");
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }
                w= getNextNode(u, w); //继续找下一个节点
            }
        }
    }

    public void breadthFirstSearch() {
        // 遍历所有节点进行DFS
        for (int i = 0; i < nodeNum(); i++) {
            if (!isVisited[i]) {
                breadthFirstSearch(isVisited, i);
            }
        }
    }

    public static Graph buildGraph() {
        int n = 5; // 节点的个数
        String[] vertexString = { "A", "B", "C", "D", "F" };
        // 创建图对象
        Graph gp = new Graph(5);
        // 向图对象添加节点
        for (String value : vertexString) {
            gp.addVer(value);
        }
        // 添加边AB AC AD AE BC BD CE DE
        gp.addNum(0, 1, 1);
        gp.addNum(0, 2, 1);
        gp.addNum(0, 3, 1);
        gp.addNum(0, 4, 1);
        gp.addNum(1, 2, 1);
        gp.addNum(1, 3, 1);
        gp.addNum(2, 4, 1);
        gp.addNum(3, 4, 1);
        return gp;
    }

    public static void main(String[] args) {

        Graph graph = buildGraph();
        // 显示图的矩阵
        graph.show();

        //System.out.println("depthFirstSearch:");
        //graph.depthFirstSearch();

        System.out.println("breadthFirstSearch:");
        graph.breadthFirstSearch();
    }
}
