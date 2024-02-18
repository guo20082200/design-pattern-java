package com.zishi.algorithm.a07_tree.threadtree;

public class ThreadTreeTest {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ThreadTree tree = new ThreadTree(data); // 创建普通二叉树

        //System.out.println(tree.getSize());
        //System.out.println(tree.getRoot().getLeft().getRight().getLeft());

        //tree.middleTraverse(tree.getRoot()); // 8 4 9 2 10 5 1 6 3 7
        // 中序遍历线索二叉树
        tree.inThreadOrder(tree.getRoot());

        //System.out.println("中序递归遍历二叉树");
       // threadTree.inList(threadTree.getRoot()); // 中序递归遍历二叉树
       // System.out.println();

        //threadTree.inThread(threadTree.getRoot()); // 采用中序遍历将二叉树线索化
        //System.out.println("中序遍历线索化二叉树");
       // threadTree.inThreadList(threadTree.getRoot()); // 中序遍历线索化二叉树
    }
}