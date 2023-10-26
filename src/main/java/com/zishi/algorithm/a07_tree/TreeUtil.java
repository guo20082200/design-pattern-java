package com.zishi.algorithm.a07_tree;

public class TreeUtil {

    public static Binarytree init() {
        Binarytree tree = new Binarytree();
        Node root = new Node(1, "one"); //node1
        Node node2 = new Node(2, "two");
        Node node3 = new Node(3, "three");
        Node node4 = new Node(4, "four");
        tree.setRoot(root);
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);


        return tree;
    }
}
