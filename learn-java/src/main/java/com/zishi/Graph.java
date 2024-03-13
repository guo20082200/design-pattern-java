package com.zishi;

import java.util.*;

class Node {
    int id;
    List<Node> neighbors;

    Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class Graph {
    private Map<Integer, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    // 添加节点和边的方法（省略）

    // 判断图中是否存在环
    public boolean hasCycle() {
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes.values()) {
            if (!visited.contains(node) && dfs(node, visited)) {
                return true;
            }
        }

        return false;
    }

    // 深度优先搜索，如果在访问过程中再次遇到已访问节点，则存在环
    private boolean dfs(Node node, Set<Node> visited) {
        if (visited.contains(node)) {
            return false;
        }
        visited.add(node);

        for (Node neighbor : node.neighbors) {
            if (dfs(neighbor, visited)) {
                return true;
            }
        }

        return false;
    }

    // 主函数入口
    public static void main(String[] args) {
        // 构建图（省略）
        Graph graph = new Graph();
        // 添加节点和边（省略）

        if (graph.hasCycle()) {
            System.out.println("图中存在环");
        } else {
            System.out.println("图中不存在环");
        }
    }
}
