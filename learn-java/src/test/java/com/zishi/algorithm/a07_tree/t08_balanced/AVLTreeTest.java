package com.zishi.algorithm.a07_tree.t08_balanced;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {


    AVLTree<Integer> tree;

    @BeforeEach
    void setup() {
        AVLNode<Integer> node = new AVLNode<>(1);
        tree = new AVLTree<>(node);
        //tree.midOrder();
    }

    @Test
    void name() {
    }

    @Test
    void getNode() {
    }

    @Test
    void insert() {
        tree.insert(10);
        tree.insert(11);
        tree.midOrder();
    }
}