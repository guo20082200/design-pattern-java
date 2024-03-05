package com.zishi.algorithm.a07_tree.t09_23tree;

public class TwoThreeTree<Key extends Comparable<Key>, Value> {

    TwoThreeTreeNode<Key, Value> root;

    public TwoThreeTree(TwoThreeTreeNode<Key, Value> root) {
        this.root = root;
    }
}
